package io.github.facuu16.flib.core.common;

import java.util.concurrent.CompletableFuture;

public interface Lifecycle {
    
    CompletableFuture<Void> start();
    
    default CompletableFuture<Void> stop() {
        return CompletableFuture.runAsync(() -> {});
    }

}