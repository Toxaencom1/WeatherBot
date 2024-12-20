package com.taxah.weathersenderproject.repository;

import com.taxah.weathersenderproject.model.subscriberEntity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    @Transactional
    void deleteByChatId(Long chatId);

    List<Subscriber> findByChatId(Long chatId);
}
