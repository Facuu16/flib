package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.common.Lifecycle;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface Service extends Lifecycle {
    
    default CompletableFuture<Void> allOf(@NonNull Stream<CompletableFuture<?>> futures) {
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
    
}