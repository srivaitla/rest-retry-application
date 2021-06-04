package com.nord.rest.retry.application.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RestClientConfig {

    @Value("${producer.url}")
    private String url;

}
