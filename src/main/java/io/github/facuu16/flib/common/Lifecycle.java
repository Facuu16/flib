package io.github.facuu16.flib.common;

import java.util.concurrent.CompletableFuture;

public interface Lifecycle {

    void start();

    void stop();

    default CompletableFuture<Void> startAsync() {
        return CompletableFuture.runAsync(this::start);
    }

    default CompletableFuture<Void> stopAsync() {
        return CompletableFuture.runAsync(this::stop);
    }

}