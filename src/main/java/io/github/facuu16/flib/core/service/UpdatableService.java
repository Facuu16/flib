package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import io.github.facuu16.flib.core.common.Updatable;
import team.unnamed.inject.Inject;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Bind(to = Lifecycle.class)
public class UpdatableService implements Service {

    @Inject
    private Set<Updatable> updatables;
    
    @Override
    public CompletableFuture<Void> start() {
        return allOf(updatables.stream().map(Updatable::update));
    }
    
    
    @Override
    public CompletableFuture<Void> stop() {
        return allOf(updatables.stream().map(Updatable::save));
    }

}