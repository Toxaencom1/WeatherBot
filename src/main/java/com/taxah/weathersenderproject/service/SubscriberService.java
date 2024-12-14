package com.taxah.weathersenderproject.service;

import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import com.taxah.weathersenderproject.repository.SubscriberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Data
@Service
@RequiredArgsConstructor
public class SubscriberService implements Iterable<Subscriber> {

    private List<Subscriber> subscribers = new ArrayList<>();
    private final SubscriberRepository subscriberRepository;

    public void addSubscriber(String name, Long chatId) {
        Subscriber subscriber = new Subscriber(name, chatId);
        subscribers.add(subscriber);
        subscriberRepository.save(subscriber);
    }

    public void removeSubscriber(Long chatId) {
        subscribers.removeIf(subscriber -> subscriber.getChatId().equals(chatId));
        subscriberRepository.deleteByChatId(chatId);
    }

    @Override
    public Iterator<Subscriber> iterator() {
        return subscribers.iterator();
    }

    @Override
    public void forEach(Consumer<? super Subscriber> action) {
        Iterable.super.forEach(action);
    }

    public boolean isEmpty() {
        return subscribers.isEmpty();
    }

    public void fetchSubscribers() {
        subscribers = subscriberRepository.findAll();
    }
}
