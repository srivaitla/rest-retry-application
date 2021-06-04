package com.nord.rest.retry.application.controller;

import com.nord.rest.retry.application.model.ConsumerRequest;
import com.nord.rest.retry.application.model.ConsumerResponse;
import com.nord.rest.retry.application.service.RetryTemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryTemplateController {

    private static final Logger LOGGER = LogManager.getLogger(RetryTemplateController.class);

    @Autowired
    private RetryTemplateService service;

    @PostMapping(value = "/consume/retryTemplateFixedBackOff", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsumerResponse retryTemplateFixedBackOff(@RequestBody ConsumerRequest request) {
        LOGGER.info("Retry-Template-FixedBackOff-Controller ===== ===== Started\n");
        LOGGER.info(request + "\n");

        final ConsumerResponse response = service.retryTemplateFixedBackOff(request);

        LOGGER.info(response + "\n");
        LOGGER.info("Retry-Template-FixedBackOff-Controller ===== ===== Completed\n\n\n");
        return response;
    }

    @PostMapping(value = "/consume/retryTemplateExponentialBackOff", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsumerResponse retryTemplateExponentialBackOff(@RequestBody ConsumerRequest request) {
        LOGGER.info("Retry-Template-ExponentialBackOff-Controller ===== ===== Started\n");
        LOGGER.info(request + "\n");

        final ConsumerResponse response = service.retryTemplateExponentialBackOff(request);

        LOGGER.info(response + "\n");
        LOGGER.info("Retry-Template-ExponentialBackOff-Controller ===== ===== Completed\n\n\n");
        return response;
    }
}
