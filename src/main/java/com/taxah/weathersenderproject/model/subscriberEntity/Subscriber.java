package com.taxah.weathersenderproject.model.subscriberEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "chat_id")
    private Long chatId;

    public Subscriber(String name, Long chatId) {
        this.name = name;
        this.chatId = chatId;
    }
}
