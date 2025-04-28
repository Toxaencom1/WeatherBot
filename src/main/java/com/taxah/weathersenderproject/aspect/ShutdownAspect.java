package com.taxah.weathersenderproject.aspect;

import com.taxah.weathersenderproject.service.ShutdownService;
import com.taxah.weathersenderproject.service.WirelessHttpRelayApiService;
import com.taxah.weathersenderproject.service.weather.SubscribersNotificationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class ShutdownAspect {
    public static final String SEND_DAILY_MESSAGES_FAILED = "Send daily messages failed!!!";
    public static final int THRESHOLD_IN_SECONDS = 120;
    public static final int MINUTE_DELIMITER_VALUE = 60;
    private final WirelessHttpRelayApiService relayApiService;
    private final SubscribersNotificationService subscribersNotificationService;
    @Value("${bot.admin-chat-id}")
    private Long adminId;
    @Value("${relay.delay-in-seconds}")
    private int delayInSeconds;
    private final ShutdownService shutdownService;
    @Value("${spring.application.ip}")
    private String hostIp;
    @Value("${spring.datasource.db-ip}")
    private String dbIp;

    @AfterReturning(
            pointcut = "execution(boolean com.taxah.weathersenderproject.service.weather.SubscribersNotificationService.sendDailyMessages(..))",
            returning = "result"
    )
    public void afterSuccessfulSendDailyMessages(boolean result) {
        if (result) {
            if (delayInSeconds > THRESHOLD_IN_SECONDS) {
                System.out.println("Turning off relay by timer!!!");
                int minutesDelay = (delayInSeconds / MINUTE_DELIMITER_VALUE) - (delayInSeconds / MINUTE_DELIMITER_VALUE / 3);
                shutdownService.shutdownRaspberryPiOSByTimer(hostIp, minutesDelay);
                shutdownService.shutdownRaspberryPiOSByTimer(dbIp, minutesDelay);
                Executors.newSingleThreadScheduledExecutor().schedule(
                        shutdownService::shutdownSpringBootApplication,
                        delayInSeconds - (delayInSeconds / 2),
                        TimeUnit.SECONDS
                );
                relayApiService.relayOffByTimer(delayInSeconds);
            } else {
                subscribersNotificationService.sendCommonMessage(adminId,
                        "Delay " + delayInSeconds + " seconds, wrong value");
            }
        } else {
            System.out.println(SEND_DAILY_MESSAGES_FAILED);
            subscribersNotificationService.sendCommonMessage(adminId, SEND_DAILY_MESSAGES_FAILED);
        }
    }
}
