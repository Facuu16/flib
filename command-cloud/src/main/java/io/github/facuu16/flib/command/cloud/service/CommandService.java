package io.github.facuu16.flib.command.cloud.service;

import io.github.facuu16.flib.command.cloud.CommandFeature;
import io.github.facuu16.flib.command.cloud.manager.CommandManager;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import io.github.facuu16.flib.core.service.Service;
import team.unnamed.inject.InjectAll;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@InjectAll
@Bind(to = Lifecycle.class)
public class CommandService implements Service {
    
    private Set<CommandFeature> commands;
    
    private CommandManager manager;
    
    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> manager.annotationParser().parse(commands));
    }
    
}