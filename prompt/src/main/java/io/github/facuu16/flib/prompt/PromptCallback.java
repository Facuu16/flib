package io.github.facuu16.flib.prompt;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PromptCallback<V> {

    void apply(Player player, V value);

}