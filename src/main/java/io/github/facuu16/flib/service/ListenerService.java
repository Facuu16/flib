package io.github.facuu16.flib.service;

import io.github.facuu16.flib.FlibApplication;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Lifecycle;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;

import java.util.Set;

@Component
@Attached(target = Lifecycle.class)
public class ListenerService implements Service {

    @Inject
    private Set<Listener> listeners;

    @Override
    public void start() {
        final JavaPlugin plugin = FlibApplication.plugin();

        listeners.forEach(listener -> plugin.getServer()
                .getPluginManager()
                .registerEvents(listener, plugin));
    }

    @Override
    public void stop() {
        listeners.forEach(HandlerList::unregisterAll);
    }

}