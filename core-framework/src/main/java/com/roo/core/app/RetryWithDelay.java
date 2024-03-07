package com.roo.core.app;

import com.roo.core.model.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    public static final int TYPE_RETRY_DEFAULT = -1; // 默认方式：第一次失败马上重试（快速重试），第二次隔1s，第三次隔4s，第四次隔10s
    public static final int TYPE_RETRY_ENDLESS = -2; // 无限重试：0s，1s，4s，10s，30s，30s，30s无限
    public static final int TYPE_RETRY_TIME = -3;    // 无限重试：自定义间隔
    private static final int DEFAULT_RETRIES = 3;
    private int maxRetries = DEFAULT_RETRIES;
    private int retryDelayMillis = 500;
    private int retryCount = 0;
    private Consumer consumer;

    public RetryWithDelay() {
    }


    public RetryWithDelay(int maxRetries) {
        this(maxRetries, 0, null);
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this(maxRetries, retryDelayMillis, null);
    }

    public RetryWithDelay(int maxRetries, Consumer consumer) {
        this(maxRetries, 0, consumer);
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis, Consumer consumer) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.consumer = consumer;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> attempts) {
        Observable<?> retryObservable = attempts.flatMap((Function<Throwable, Observable<?>>) throwable -> {
            if (checkRetry() && !(throwable instanceof ApiException)) {
                // When this Observable calls onSafeNext, the original Observable will be retried (i.e. re-subscribed).
                return Observable.timer(getDelay(), TimeUnit.MILLISECONDS);
            }
            // Max retries hit. Just pass the error along.
            return Observable.error(throwable);
        }).onErrorResumeNext((Function<Throwable, ObservableSource<?>>) Observable::error);
        if (consumer == null) {
            return retryObservable;
        } else {
            return retryObservable.doOnNext(consumer);
        }
    }

    /**
     * 判断是否需要重试
     */
    private boolean checkRetry() {
        retryCount++;
        return (maxRetries == TYPE_RETRY_DEFAULT && retryCount <= DEFAULT_RETRIES) // 默认重试DEFAULT_RETRIES次
                || maxRetries == TYPE_RETRY_ENDLESS // 无限重试
                || maxRetries == TYPE_RETRY_TIME    //无限定时重试
                || retryCount <= maxRetries;    // 自定义重试
    }

    /**
     * 获取重试间隔
     */
    private int getDelay() {
        if (maxRetries == TYPE_RETRY_DEFAULT || maxRetries == TYPE_RETRY_ENDLESS) {
            switch (retryCount) {
                case 1:
                    retryDelayMillis = 0;
                    break;
                case 2:
                    retryDelayMillis = 1000;
                    break;
                case 3:
                    retryDelayMillis = 4000;
                    break;
                case 4:
                    retryDelayMillis = 10000;
                    break;
                default:
                    retryDelayMillis = 50000;
                    break;
            }
        }
        return retryDelayMillis;
    }

}