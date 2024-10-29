package io.github.facuu16.flib.command.acf;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.RegisteredCommand;
import co.aikar.commands.annotation.HelpCommand;
import io.github.facuu16.flib.command.acf.configuration.CommandConfiguration;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import team.unnamed.inject.InjectAll;

import java.util.Collection;
import java.util.Map;

@InjectAll
public abstract class AbstractCommand extends BaseCommand {
    
    private CommandConfiguration configuration;
    private BukkitAudiences audiences;
    
    public void setup(@NonNull BukkitCommandManager manager) {}

    @HelpCommand
    public void onHelp(CommandSender sender) {
        final Map<String, Collection<RegisteredCommand>> subcommands = getSubCommands().asMap();
        
        subcommands.remove("__catchunknown");
        subcommands.remove("__default");
        
        audiences.sender(sender).sendMessage(configuration.helpMessage(getName(), subcommands));
    }

}