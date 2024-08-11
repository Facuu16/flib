package io.github.facuu16.flib.expansion.service;

import io.github.facuu16.flib.FlibApplication;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import io.github.facuu16.flib.service.Service;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import team.unnamed.inject.Inject;

import java.util.Set;

@Component
@Attached(target = Lifecycle.class)
public class ExpansionService implements Service {

    @Inject
    private Set<PlaceholderExpansion> expansions;

    @Override
    public void start() {
        expansions.forEach(expansion -> {
            if (expansion.canRegister()) {
                expansion.register();
                FlibApplication.logger().info("Registered expansion '" + expansion.getIdentifier() + "' (PlaceholderAPI)");
            }
        });
    }

    @Override
    public void stop() {}

}