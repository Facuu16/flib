package io.github.facuu16.flib.prompt;

import io.github.facuu16.flib.prompt.manager.PromptManager;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

@Value
@Accessors(fluent = true)
public class TextPrompt<V> implements Prompt<String, V> {

    @NonNull
    Parser<String, V> parser;

    @Override
    public void send(@NonNull Player player, @NonNull PromptCallback<V> callback) {
        PromptManager.instance().prompts().put(player.getUniqueId(), new PendingPrompt<>(this, callback));
    }

}