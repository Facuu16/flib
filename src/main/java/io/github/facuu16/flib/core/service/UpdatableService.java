package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import io.github.facuu16.flib.core.common.Updatable;
import team.unnamed.inject.Inject;

import java.util.Set;

@Bind(to = Lifecycle.class)
public class UpdatableService implements Service {

    @Inject
    private Set<Updatable> updatables;
    
    @Override
    public void start() {
        updatables.forEach(Updatable::update);
    }
    
    
    @Override
    public void stop() {
        updatables.forEach(Updatable::save);
    }

}