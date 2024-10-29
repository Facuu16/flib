package io.github.facuu16.flib.command.acf;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.annotation.Values;
import io.github.facuu16.flib.command.acf.manager.CommandManager;
import io.github.facuu16.flib.core.annotation.Bind;
import lombok.NonNull;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;

import java.util.Locale;
import java.util.stream.Collectors;

@Bind(to = AbstractCommand.class)
public class LangCommand extends AbstractCommand {

    @Inject
    private CommandManager manager;

    @Override
    public void setup(@NonNull BukkitCommandManager manager) {
        manager.getCommandCompletions().registerCompletion("flib-locales", context -> manager.getSupportedLanguages().stream()
                .map(Locale::toLanguageTag)
                .collect(Collectors.toList()));
    }
    
    @Syntax("<locale>")
    @CommandAlias("lang")
    @CommandPermission("flib.commands.lang")
    public void lang(@NonNull Player player, @Values("@flib-locales") @NonNull String locale) {
        manager.setPlayerLocale(player, Locale.forLanguageTag(locale));
    }

}