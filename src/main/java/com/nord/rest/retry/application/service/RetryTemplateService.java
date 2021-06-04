package com.nord.rest.retry.application.service;

import com.nord.rest.retry.application.client.RestClientConfig;
import com.nord.rest.retry.application.model.ConsumerRequest;
import com.nord.rest.retry.application.model.ConsumerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class RetryTemplateService {

    private static final Logger LOGGER = LogManager.getLogger(RetryTemplateService.class);

    @Autowired
    private RetryTemplate fixedBackOffRetryTemplate;

    @Autowired
    private RetryTemplate exponentialBackOffRetryTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestClientConfig clientConfig;

    public ConsumerResponse retryTemplateFixedBackOff(ConsumerRequest request) {
        LOGGER.info("Retry-Template-FixedBackOff-Service ===== ===== Started\n");

        final AtomicReference<ConsumerResponse> response = new AtomicReference<>();
        fixedBackOffRetryTemplate.execute(
                context -> {
                    LOGGER.info("Retry-Template-FixedBackOff-Execute ===== ===== Started\n");
                    response.set(restTemplate.postForObject(clientConfig.getUrl(), request, ConsumerResponse.class));
                    LOGGER.info(request + "\n");
                    LOGGER.info(context + "\n");
                    LOGGER.info("Retry-Template-FixedBackOff-Execute ===== ===== Completed\n");
                    return null;
                });

        LOGGER.info("Retry-Template-FixedBackOff-Service ===== ===== Completed\n\n\n");
        return response.get();
    }

    public ConsumerResponse retryTemplateExponentialBackOff(ConsumerRequest request) {
        LOGGER.info("Retry-Template-ExponentialBackOff-Service ===== ===== Started\n");

        final AtomicReference<ConsumerResponse> response = new AtomicReference<>();
        exponentialBackOffRetryTemplate.execute(
                context -> {
                    LOGGER.info("Retry-Template-ExponentialBackOff-Execute ===== ===== Started\n");
                    response.set(restTemplate.postForObject(clientConfig.getUrl(), request, ConsumerResponse.class));
                    LOGGER.info(request + "\n");
                    LOGGER.info(context + "\n");
                    LOGGER.info("Retry-Template-ExponentialBackOff-Execute ===== ===== Completed\n");
                    return null;
                });

        LOGGER.info("Retry-Template-ExponentialBackOff-Service ===== ===== Completed\n\n\n");
        return response.get();
    }
}
