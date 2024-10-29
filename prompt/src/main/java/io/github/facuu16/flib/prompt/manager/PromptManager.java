package io.github.facuu16.flib.prompt.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.facuu16.flib.prompt.PendingPrompt;
import io.github.facuu16.flib.prompt.configuration.PromptConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import team.unnamed.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@Accessors(fluent = true)
public class PromptManager {

    @Getter
    private final Cache<UUID, PendingPrompt> prompts;

    @Inject
    @Getter
    private static PromptManager instance;

    @Inject
    private PromptManager(@NonNull PromptConfiguration configuration, @NonNull BukkitAudiences audiences) {
        this.prompts = Caffeine.newBuilder()
            .expireAfterWrite(configuration.expireAfter())
            .removalListener((id, prompt, cause) -> {
                if (!cause.wasEvicted())
                    return;

                final UUID uuid = (UUID) id;

                if (!Bukkit.getOfflinePlayer(uuid).isOnline())
                    return;

                audiences.player(uuid).sendMessage(configuration.expireMessage());
            })
            .build();
    }

    public <I, V> Optional<PendingPrompt<I, V>> get(@NonNull UUID uuid) {
        return Optional.ofNullable(prompts.getIfPresent(uuid));
    }

}