package com.taxah.weathersenderproject.service.onReceiveStrategy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Data
@Component
@RequiredArgsConstructor
public class OnReceiveStrategyResolver {
    private final List<OnReceiveStrategy> strategyList;

    public OnReceiveStrategy resolveStrategy(Update update) {
        return strategyList.stream()
                .filter(strategy -> strategy.provision(update))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No strategy found for update"));
    }
}
