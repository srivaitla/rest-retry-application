package com.nord.rest.retry.application.service;

import com.nord.rest.retry.application.client.RestClientConfig;
import com.nord.rest.retry.application.model.ConsumerRequest;
import com.nord.rest.retry.application.model.ConsumerResponse;
import io.github.resilience4j.retry.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import static java.util.Collections.nCopies;
import static java.util.concurrent.Executors.newFixedThreadPool;

@Service
public class RetryResilienceService {

    private static final Logger LOGGER = LogManager.getLogger(RetryResilienceService.class);

    @Value("${retry.numberOfClients}")
    private int numberOfClients;

    @Value("${retry.numberOfThreads}") 
    private int numberOfThreads;

    @Autowired
    private Retry retryResilience;

    @Autowired
    private Retry retryResilienceJitter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestClientConfig clientConfig;

    public ConsumerResponse retryResilience(ConsumerRequest request) throws InterruptedException {
        LOGGER.info("Retry-Resilience-Service ===== ===== Started\n");

        final Function<ConsumerRequest, ConsumerResponse> function = getResponseUsingRetryResilience(request);
        //final ConsumerResponse response = function.apply(request);
        executeWithThreads(request, function);

        LOGGER.info("Retry-Resilience-Service ===== ===== Completed\n\n\n");
        return buildResponse(request);
    }

    public ConsumerResponse retryResilienceJitter(ConsumerRequest request) throws InterruptedException {
        LOGGER.info("Retry-Resilience-Jitter-Service ===== ===== Started\n");

        final Function<ConsumerRequest, ConsumerResponse> function = getResponseUsingRetryResilienceJitter(request);
        //final ConsumerResponse response = function.apply(request);
        executeWithThreads(request, function);

        LOGGER.info("Retry-Resilience-Jitter-Service ===== ===== Completed\n\n\n");
        return buildResponse(request);
    }

    private Function<ConsumerRequest, ConsumerResponse> getResponseUsingRetryResilience(ConsumerRequest request) {
        return Retry.decorateFunction(retryResilience, consumerResponse -> {
            LOGGER.info("Retry-Resilience-Execute ===== ===== Started\n");
            //LOGGER.info(request + "\n");
            final ConsumerResponse response =
                    restTemplate.postForObject(clientConfig.getUrl(), request, ConsumerResponse.class);
            LOGGER.info("Retry-Resilience-Execute ===== ===== Completed\n");
            return response;
        });
    }

    private Function<ConsumerRequest, ConsumerResponse> getResponseUsingRetryResilienceJitter(ConsumerRequest request) {
        return Retry.decorateFunction(retryResilienceJitter, consumerResponse -> {
            LOGGER.info("Retry-Resilience-Jitter-Execute ===== ===== Started\n");
            //LOGGER.info(request + "\n");
            final ConsumerResponse response =
                    restTemplate.postForObject(clientConfig.getUrl(), request, ConsumerResponse.class);
            LOGGER.info("Retry-Resilience-Jitter-Execute ===== ===== Completed\n");
            return response;
        });
    }

    private void executeWithThreads(ConsumerRequest request, Function<ConsumerRequest, ConsumerResponse> function)
            throws InterruptedException {
        ExecutorService executors = newFixedThreadPool(numberOfThreads);
        List<Callable<ConsumerResponse>> tasks = nCopies(numberOfClients, () -> function.apply(request));
        executors.invokeAll(tasks);
    }

    private ConsumerResponse buildResponse(ConsumerRequest request) {
        final ConsumerResponse response = new ConsumerResponse();
        response.setId(request.getId());
        response.setContent(request.getType());
        return response;
    }
}
