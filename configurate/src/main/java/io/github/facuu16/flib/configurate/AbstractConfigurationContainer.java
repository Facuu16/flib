package io.github.facuu16.flib.configurate;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.configurate.serializer.ComponentSerializer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractConfigurationContainer<L extends AbstractConfigurationLoader<CommentedConfigurationNode>, C> implements ConfigurationContainer<L, C> {
    
    protected static final ConfigurationOptions DEFAULTS = ConfigurationOptions.defaults()
            .shouldCopyDefaults(true)
            .serializers(builder -> builder.register(new ComponentSerializer()));

    private final Class<C> type = type();

    private final L loader;

    private final AtomicReference<C> configuration = new AtomicReference<>();

    @Override
    public L loader() {
        return loader;
    }

    @Override
    public C get() {
        return configuration.get();
    }

    @Override
    public CompletableFuture<Boolean> update() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final CommentedConfigurationNode node = loader.load();
                final C reloaded = node.get(type);
                
                node.set(type, reloaded);
                loader.save(node);
                configuration.set(reloaded);
                return true;
            } catch (ConfigurateException e) {
                FlibApplication.logger().log(Level.SEVERE, "Could not reload '" + type.getSimpleName() + "' configuration file", e);
                return false;
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> save() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final CommentedConfigurationNode node = loader.load();
                final C configuration = this.configuration.get();
                
                node.set(type, configuration);
                loader.save(node);
                return true;
            } catch (ConfigurateException e) {
                FlibApplication.logger().log(Level.SEVERE, "Could not save '" + type.getSimpleName() + "' configuration file", e);
                return false;
            }
        });
    }

}