package com.taxah.weathersenderproject.aspect;

import com.taxah.weathersenderproject.service.weather.SubscribersNotificationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiConnectErrorAspect {
    private final SubscribersNotificationService subscribersNotificationService;
    @Value("${bot.admin-chat-id}")
    private Long adminId;

    @AfterThrowing(
            pointcut = "execution(* com.taxah.weathersenderproject.service..*(..))",
            throwing = "ex"
    )
    public void notifyAdminOnError(ResourceAccessException ex) {
        System.out.println("🔔 Администратору отправлено уведомление: " + ex.getMessage());
        subscribersNotificationService.sendCommonMessage(adminId, ex.getMessage());
    }
}
