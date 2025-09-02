package io.github.facuu16.flib.core.service;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Lifecycle;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;

import java.util.Set;

@Bind(to = Lifecycle.class)
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