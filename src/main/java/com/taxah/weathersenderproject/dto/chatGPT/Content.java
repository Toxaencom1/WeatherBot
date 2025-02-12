package com.taxah.weathersenderproject.dto.chatGPT;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Content {
    String type;
    String text;
}
