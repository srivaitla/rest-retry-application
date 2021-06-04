package com.nord.rest.retry.application.retry;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://www.baeldung.com/resilience4j-backoff-jitter
 * <p>
 * wait_interval = base * multiplier^n
 * where, base is the initial interval, ie, wait for the first retry;
 * n is the number of failures that have occurred;
 * multiplier is an arbitrary multiplier that can be replaced with any suitable value
 */
@Configuration
public class RetryResilienceProvider {

    @Value("${retry.resilience.initialInterval}")
    // initial period to sleep on the first backoff
    private long initialInterval;

    @Value("${retry.resilience.multiplier}")
    // multiplier to use to generate the next backoff interval from the last
    private int multiplier;

    @Value("${retry.resilience.maxAttempts}")
    // maximum retry
    private int maxAttempts;

    @Bean(name = "retryResilience")
    public Retry retryResilience() {
        // wait_interval = base * multiplier^n
        final IntervalFunction intervalFn = IntervalFunction.ofExponentialBackoff(initialInterval, multiplier);

        final RetryConfig retryConfig = RetryConfig.custom().maxAttempts(maxAttempts).intervalFunction(intervalFn).build();
        return Retry.of("retryResilience", retryConfig);
    }

    @Bean(name = "retryResilienceJitter")
    public Retry retryResilienceJitter() {
        // wait_interval = (base * 2^n) +/- (random_interval)
        final IntervalFunction intervalFn =
                IntervalFunction.ofExponentialRandomBackoff(initialInterval, multiplier, 0.5D);

        final RetryConfig retryConfig = RetryConfig.custom().maxAttempts(maxAttempts).intervalFunction(intervalFn).build();
        return Retry.of("retryResilienceJitter", retryConfig);
    }
}
