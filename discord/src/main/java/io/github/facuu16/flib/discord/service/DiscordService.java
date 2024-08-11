package io.github.facuu16.flib.discord.service;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import io.github.facuu16.flib.FlibApplication;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import io.github.facuu16.flib.discord.DiscordCommand;
import io.github.facuu16.flib.service.Service;
import team.unnamed.inject.Inject;

import java.util.HashSet;
import java.util.Set;

@Component
@Attached(target = Lifecycle.class)
public class DiscordService implements Service {

    @Inject
    private Set<DiscordCommand> commands;

    @Override
    public void start() {
        final Set<PluginSlashCommand> commands = new HashSet<>();

        this.commands.forEach(command -> {
            commands.add(new PluginSlashCommand(FlibApplication.plugin(), command.data(), command.guilds()));
            FlibApplication.logger().info("Registered discord command '" + command.data().getName() + "'");
        });

        DiscordSRV.api.addSlashCommandProvider(() -> commands);
    }

    @Override
    public void stop() {}

}