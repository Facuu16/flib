package io.github.facuu16.flib.prompt;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface Prompt<I, V> {

    Parser<I, V> parser();

    void send(@NonNull Player player, @NonNull PromptCallback<V> callback);

}