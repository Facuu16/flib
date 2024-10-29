package io.github.facuu16.flib.command.acf.configuration;

import co.aikar.commands.RegisteredCommand;
import io.github.facuu16.flib.configuration.YamlConfigurationFile;
import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Updatable;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Bind(to = Updatable.class)
public class CommandConfiguration extends YamlConfigurationFile {
    
    private CommandConfiguration() throws IOException, InvalidConfigurationException {
        super(FlibApplication.folder().resolve("command-acf.yml"));
    }
    
    public Component helpSubcommand(@NonNull String command, @NonNull String subcommand, @NonNull String syntax) {
        return component("help-command", "subcommand-template",
                Placeholder.unparsed("command_name", command),
                Placeholder.unparsed("subcommand_name", subcommand),
                Placeholder.unparsed("subcommand_syntax", syntax));
    }
    
    public Component helpMessage(@NonNull String parent, @NonNull Map<String, Collection<RegisteredCommand>> subcommands) {
        final TextComponent.Builder builder = Component.text();

        subcommands.forEach((key, commands)
                -> commands.forEach(command -> builder.append(helpSubcommand(parent, key, command.getSyntaxText()))));
        
        return component("help-command", "message-template", Placeholder.component("subcommands", builder.build()));
    }
    
}