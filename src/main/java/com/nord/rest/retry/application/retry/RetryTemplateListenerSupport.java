package com.nord.rest.retry.application.retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class RetryTemplateListenerSupport extends RetryListenerSupport {

    private static final Logger LOGGER = LogManager.getLogger(RetryTemplateListenerSupport.class);

    @Override
    public <T, E extends Throwable> void close(RetryContext context,
                                               RetryCallback<T, E> callback, Throwable throwable) {
        LOGGER.info("RetryTemplate ===== ===== onClose\n");
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
                                                 RetryCallback<T, E> callback, Throwable throwable) {
        LOGGER.info("RetryTemplate ===== ===== onError\n");
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
                                                 RetryCallback<T, E> callback) {
        LOGGER.info("RetryTemplate ===== ===== onOpen\n");
        return super.open(context, callback);
    }
}
