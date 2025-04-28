package com.taxah.weathersenderproject.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * AppConfig Class
 * <p>
 * This class serves as the configuration class for the Spring application.
 * It configures beans and properties needed for the application to function.
 */
@Data
@Configuration
public class AppConfig {
    /**
     * RestTemplate Bean Configuration
     * <p>
     * Configures and returns a RestTemplate bean for making HTTP requests.
     *
     * @return RestTemplate: The configured RestTemplate bean.
     */
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .requestFactory(getClientHttpRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(10000);
        factory.setConnectTimeout(10000);
        return factory;
    }

}
