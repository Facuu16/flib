package io.github.facuu16.flib.discord;

import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;

public interface DiscordCommand {

    default String[] guilds() {
        return null;
    }

    CommandData data();

}