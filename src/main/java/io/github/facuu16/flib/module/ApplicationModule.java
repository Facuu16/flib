package io.github.facuu16.flib.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.AbstractModule;

import java.util.List;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class ApplicationModule extends AbstractModule {

    private final List<Class<?>> applications;

    @Override
    protected void configure() {
        applications.forEach(application -> {
            final JavaPlugin plugin = JavaPlugin.getPlugin((Class<? extends JavaPlugin>) application);
            bind(JavaPlugin.class).named(plugin.getName()).toInstance(plugin);
        });
    }

}