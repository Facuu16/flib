package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import team.unnamed.inject.Inject;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Bind
public class LifecycleService implements Service {
    
    @Inject
    private Set<Lifecycle> lifecycles;
    
    @Override
    public CompletableFuture<Void> start() {
        return allOf(lifecycles.stream().map(Lifecycle::start));
    }
    
    @Override
    public CompletableFuture<Void> stop() {
        return allOf(lifecycles.stream().map(Lifecycle::stop));
    }
    
}