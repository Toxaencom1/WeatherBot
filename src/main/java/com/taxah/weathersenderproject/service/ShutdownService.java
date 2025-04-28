package com.taxah.weathersenderproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ShutdownService {
    private final ApplicationContext context;

    @Value("${connect.ssh.username}")
    private String username;
    @Value("${connect.ssh.password}")
    private String password;

    public void shutdownSpringBootApplication() {
        System.out.println("🛑 Завершаем работу приложения...");
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }

    public void shutdownRaspberryPiOSByTimer(String hostIp,int minutesDelay) {
        if (hostIp != null && !hostIp.isBlank()) {
            String command = String.format(
                    "sshpass -p '%s' ssh -o StrictHostKeyChecking=no %s@%s 'sudo shutdown -h +%d'",
                    password, username, hostIp, minutesDelay
            );

            try {
                Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
                System.out.println("Команда на выключение отправлена через SSH");
            } catch (IOException e) {
                System.out.println("Ошибка при отправке команды через SSH");
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("Host IP is null or blank, skipping SSH command...");
        }
    }
}
