package io.github.facuu16.flib.command.acf.service;

import io.github.facuu16.flib.command.acf.AbstractCommand;
import io.github.facuu16.flib.command.acf.manager.CommandManager;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import io.github.facuu16.flib.core.service.Service;
import team.unnamed.inject.InjectAll;

import java.util.Set;

@InjectAll
@Bind(to = Lifecycle.class)
public class CommandService implements Service {

    private CommandManager manager;

    private Set<AbstractCommand> commands;

    @Override
    public void start() {
        commands.forEach(command -> {
            command.setup(manager);
            manager.registerCommand(command);
        });
    }

    @Override
    public void stop() {
        manager.unregisterCommands();
    }

}