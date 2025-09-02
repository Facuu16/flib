package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import team.unnamed.inject.Inject;

import java.util.Set;

@Bind
public class LifecycleService implements Service {
    
    @Inject
    private Set<Lifecycle> lifecycles;
    
    @Override
    public void start() {
        lifecycles.forEach(Lifecycle::start);
    }
    
    @Override
    public void stop() {
        lifecycles.forEach(Lifecycle::stop);
    }
    
}