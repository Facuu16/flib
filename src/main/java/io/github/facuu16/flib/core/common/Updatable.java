package io.github.facuu16.flib.core.common;

import java.util.concurrent.CompletableFuture;

public interface Updatable {

    CompletableFuture<Boolean> update();

    CompletableFuture<Boolean> save();

}