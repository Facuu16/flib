package io.github.facuu16.flib.service;

import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import io.github.facuu16.flib.common.Updatable;
import team.unnamed.inject.Inject;

import java.util.Set;

@Component
@Attached(target = Lifecycle.class)
public class UpdatableService implements Service {

    @Inject
    private Set<Updatable> updatables;

    @Override
    public void start() {
        updatables.forEach(Updatable::reload);
    }

    @Override
    public void stop() {
        updatables.forEach(Updatable::save);
    }

}