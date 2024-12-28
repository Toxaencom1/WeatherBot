package com.taxah.weathersenderproject.controller;

import com.taxah.weathersenderproject.service.SubscribersNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bot")
public class BotController {
    private final SubscribersNotificationService notificationService;

    @PostMapping("/message/ordinary")
    public ResponseEntity<String> send(@RequestBody String message) {
        notificationService.sendOrdinaryMessage(message);
        return ResponseEntity.ok(message);
    }
}
