package io.github.facuu16.flib.command.acf.manager;

import co.aikar.commands.BukkitCommandManager;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.annotation.Plugin;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;

@Bind
public class CommandManager extends BukkitCommandManager {
    
    @Inject
    private CommandManager(@NonNull @Plugin JavaPlugin plugin) {
        super(plugin);
    }
    
}