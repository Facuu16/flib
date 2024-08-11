package io.github.facuu16.flib.prompt;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface Parser<I, V> {

    V parse(@NonNull I input);

    boolean canParse(@NonNull I input);

    default CompletableFuture<V> parseAsync(@NonNull I input) {
        return CompletableFuture.supplyAsync(() -> parse(input));
    }

}