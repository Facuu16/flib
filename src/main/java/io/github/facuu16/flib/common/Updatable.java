package io.github.facuu16.flib.common;

import java.util.concurrent.CompletableFuture;

public interface Updatable {

    boolean reload();

    boolean save();

    default CompletableFuture<Boolean> reloadAsync() {
        return CompletableFuture.supplyAsync(this::reload);
    }

    default CompletableFuture<Boolean> saveAsync() {
        return CompletableFuture.supplyAsync(this::save);
    }

}