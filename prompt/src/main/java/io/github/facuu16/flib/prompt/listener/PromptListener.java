package io.github.facuu16.flib.prompt.listener;

import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.prompt.Parser;
import io.github.facuu16.flib.prompt.PendingPrompt;
import io.github.facuu16.flib.prompt.configuration.PromptConfigurationContainer;
import io.github.facuu16.flib.prompt.manager.PromptManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import team.unnamed.inject.InjectAll;

import java.util.Optional;

@Component
@InjectAll
@Attached(target = Listener.class)
public class PromptListener implements Listener {

    private PromptManager manager;
    private PromptConfigurationContainer container;

    private BukkitAudiences audiences;

    @EventHandler
    public <I, V> void chat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        final Optional<PendingPrompt<I, V>> optional = manager.get(player.getUniqueId());

        if (!optional.isPresent() || optional.get().inputType() != String.class)
            return;

        final PendingPrompt<I, V> pending = optional.get();
        final Parser<I, V> parser = pending.prompt().parser();

        final I input = (I) event.getMessage();

        if (!parser.canParse(input)) {
            audiences.player(player).sendMessage(container.get().invalidMessage());
            return;
        }

        pending.callback().apply(player, parser.parse(input));
    }

}