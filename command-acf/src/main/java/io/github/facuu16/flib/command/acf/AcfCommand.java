package io.github.facuu16.flib.command.acf;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import io.github.facuu16.flib.command.acf.configuration.CommandConfiguration;
import io.github.facuu16.flib.core.annotation.Bind;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import team.unnamed.inject.InjectAll;

@InjectAll
@CommandAlias("acf")
@CommandPermission("flib.commands.acf")
@Bind(to = AbstractCommand.class)
public class AcfCommand extends AbstractCommand {
    
    private CommandConfiguration configuration;
    private BukkitAudiences audiences;
    
    @Subcommand("settings")
    public void settings(@NonNull CommandSender sender) {
        audiences.sender(sender).sendMessage(Component.text("Reloading settings...", NamedTextColor.DARK_GRAY));
        configuration.update();
        audiences.sender(sender).sendMessage(Component.text("Settings reloaded!", NamedTextColor.GREEN));
    }

}