package io.github.facuu16.flib.command;

import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.annotation.Plugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import team.unnamed.inject.Inject;

import static net.kyori.adventure.text.Component.text;

@Component
public class CommandManager extends LegacyPaperCommandManager<CommandSender> {

    @Inject
    private BukkitAudiences audiences;

    @Inject
    public CommandManager(@Plugin JavaPlugin plugin) {
        super(plugin, ExecutionCoordinator.simpleCoordinator(), SenderMapper.identity());

        if (hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            registerBrigadier();
        } else if (hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            registerAsynchronousCompletions();
        }

        MinecraftExceptionHandler.create(audiences::sender)
                .defaultInvalidSyntaxHandler()
                .defaultInvalidSenderHandler()
                .defaultNoPermissionHandler()
                .defaultArgumentParsingHandler()
                .defaultCommandExecutionHandler()
                .decorator(
                        component -> text()
                                .append(text("[", NamedTextColor.DARK_GRAY))
                                .append(text("Example", NamedTextColor.GOLD))
                                .append(text("] ", NamedTextColor.DARK_GRAY))
                                .append(component).build()
                )
                .registerTo(this);

        captionRegistry().registerProvider(MinecraftHelp.defaultCaptionsProvider());
    }

}
