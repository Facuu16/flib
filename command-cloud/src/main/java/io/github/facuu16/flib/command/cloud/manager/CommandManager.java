package io.github.facuu16.flib.command.cloud.manager;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.annotation.Plugin;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import team.unnamed.inject.Inject;

@Bind
@Getter
@Accessors(fluent = true)
public class CommandManager extends LegacyPaperCommandManager<CommandSender> {
    
    private final AnnotationParser<CommandSender> annotationParser = new AnnotationParser<>(this, CommandSender.class);

    @Inject
    public CommandManager(@Plugin @NonNull JavaPlugin plugin, @NonNull BukkitAudiences audiences) {
        super(plugin, ExecutionCoordinator.simpleCoordinator(), SenderMapper.identity());

        if (hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            registerBrigadier();
        } else if (hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            registerAsynchronousCompletions();
        }

        MinecraftExceptionHandler.create(audiences::sender)
                .defaultHandlers()
                .registerTo(this);

        captionRegistry().registerProvider(MinecraftHelp.defaultCaptionsProvider());
    }

}
