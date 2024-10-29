package io.github.facuu16.flib.expansion.service;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import io.github.facuu16.flib.core.service.Service;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import team.unnamed.inject.Inject;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Bind(to = Lifecycle.class)
public class ExpansionService implements Service {

    @Inject
    private Set<PlaceholderExpansion> expansions;

    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> expansions.forEach(expansion -> {
            if (expansion.canRegister()) {
                expansion.register();
                FlibApplication.logger().info("Registered expansion '" + expansion.getIdentifier() + "' (PlaceholderAPI)");
            }
        }));
    }
    
}