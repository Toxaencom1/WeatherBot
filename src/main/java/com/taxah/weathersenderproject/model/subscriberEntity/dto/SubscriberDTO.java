package com.taxah.weathersenderproject.model.subscriberEntity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberDTO {
    private String firstName;
    private String country;
    private String city;
}
