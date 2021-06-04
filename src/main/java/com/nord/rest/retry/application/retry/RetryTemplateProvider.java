package com.nord.rest.retry.application.retry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Spring Retry using following wiki:
 * <p>
 * https://www.baeldung.com/spring-retry
 * <p>
 * https://www.programcreek.com/java-api-examples/?api=org.springframework.retry.backoff.ExponentialBackOffPolicy
 */
@Configuration
public class RetryTemplateProvider {

    @Value("${retry.template.initialInterval}")
    // initial period to sleep on the first backoff
    private long initialInterval;

    @Value("${retry.template.multiplier}")
    // multiplier to use to generate the next backoff interval from the last
    private int multiplier;

    @Value("${retry.template.maxInterval}")
    // maximum interval to sleep for
    private long maxInterval;

    @Value("${retry.template.maxAttempts}")
    // maximum retry
    private int maxAttempts;

    @Bean(name = "fixedBackOffRetryTemplate")
    public RetryTemplate fixedBackOffRetryTemplate() {
        final RetryTemplate retryTemplate = new RetryTemplate();

        final FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(initialInterval);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        final SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);

        // Setting RetryListener
        retryTemplate.registerListener(new RetryTemplateListenerSupport());

        return retryTemplate;
    }

    @Bean(name = "exponentialBackOffRetryTemplate")
    public RetryTemplate exponentialBackOffRetryTemplate() {
        final RetryTemplate retryTemplate = new RetryTemplate();

        final ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMultiplier(multiplier);
        backOffPolicy.setMaxInterval(maxInterval);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        final SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);

        retryTemplate.registerListener(new RetryTemplateListenerSupport());

        return retryTemplate;
    }
}
