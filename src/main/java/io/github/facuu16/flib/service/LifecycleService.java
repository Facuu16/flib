package io.github.facuu16.flib.service;

import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import team.unnamed.inject.Inject;

import java.util.Set;

@Component
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