package com.taxah.weathersenderproject.service.openAI;

import com.taxah.weathersenderproject.constants.RequestGPTConstants;
import com.taxah.weathersenderproject.dto.chatGPT.Content;
import com.taxah.weathersenderproject.dto.chatGPT.GptResponseDTO;
import com.taxah.weathersenderproject.dto.chatGPT.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestClientOpenAIService implements OpenAIService {
    @Value("${spring.ai.openai.chat.base-url}")
    private String url;
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final RestClient restClient;

    @Override
    public String getAnswer(String message) {
        String uriString = UriComponentsBuilder.fromUri(URI.create(url)).toUriString();
        System.out.println(uriString);
        GptResponseDTO requestDTO = GptResponseDTO.builder()
                .model("gpt-4o-mini")
                .store(true)
                .messages(new ArrayList<>())
                .build();
        Message developerMessage = Message.builder()
                .role("developer")
                .content(List.of(Content.builder()
                        .type("text")
                        .text(RequestGPTConstants.MAIN_PROMPT)
                        .build()))
                .build();
        Message userMessage = Message.builder()
                .role("user")
                .content(List.of(Content.builder()
                        .type("text")
                        .text(message)
                        .build()))
                .build();
        requestDTO.addMessage(developerMessage);
        requestDTO.addMessage(userMessage);

        GptResponseDTO authorization = restClient.post()
                .uri(uriString)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestDTO)
                .retrieve()
                .body(GptResponseDTO.class);
        assert authorization != null;
        return authorization.getMessages().get(authorization.getMessages().size() - 1).getContent().get(0).getText();
    }
}
