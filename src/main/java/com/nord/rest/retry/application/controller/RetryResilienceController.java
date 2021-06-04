package com.nord.rest.retry.application.controller;

import com.nord.rest.retry.application.model.ConsumerRequest;
import com.nord.rest.retry.application.model.ConsumerResponse;
import com.nord.rest.retry.application.service.RetryResilienceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryResilienceController {

    private static final Logger LOGGER = LogManager.getLogger(RetryResilienceController.class);

    @Autowired
    private RetryResilienceService service;

    @PostMapping(value = "/consume/retryResilience", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsumerResponse retryResilience(@RequestBody ConsumerRequest request) throws InterruptedException {
        LOGGER.info("Retry-Resilience-Controller ===== ===== Started\n");
        LOGGER.info(request + "\n");

        final ConsumerResponse response = service.retryResilience(request);

        LOGGER.info(response + "\n");
        LOGGER.info("Retry-Resilience-Controller ===== ===== Completed\n\n\n");
        return response;
    }

    @PostMapping(value = "/consume/retryResilienceJitter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsumerResponse retryResilienceJitter(@RequestBody ConsumerRequest request) throws InterruptedException {
        LOGGER.info("Retry-Resilience-Jitter-Controller ===== ===== Started\n");
        LOGGER.info(request + "\n");

        final ConsumerResponse response = service.retryResilienceJitter(request);
        LOGGER.info(response + "\n");
        LOGGER.info("Retry-Resilience-Jitter-Controller ===== ===== Completed\n\n\n");
        return response;
    }
}
