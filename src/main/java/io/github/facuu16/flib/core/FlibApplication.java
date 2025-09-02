package io.github.facuu16.flib.core;

import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.annotation.Plugin;
import io.github.facuu16.flib.core.module.ComponentModule;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.github.facuu16.flib.core.service.LifecycleService;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.Injector;

import java.nio.file.Path;
import java.util.Random;
import java.util.logging.Logger;

@UtilityClass
@Accessors(fluent = true)
public class FlibApplication {

    @Getter
    private final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(FlibApplication.class);
    
    @Inject
    private LifecycleService lifecycle;
    
    public void run() {
        run(FlibOptions.builder());
    }

    public <V extends JavaPlugin> void run(@NonNull FlibOptions.FlibOptionsBuilder builder) {
        final Class<V> main = (Class<V>) plugin.getClass();

        builder.target("io.github.facuu16.flib.*");
        builder.target(main.getPackage().getName());

        final FlibOptions options = builder.build();

        try (
            final ScanResult result = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(options.targets().toArray(new String[0]))
                .scan()
        ) {
            final Injector injector = Injector.create(
                binder -> {
                    binder.bind(main).toInstance((V) plugin);
                    binder.bind(JavaPlugin.class).markedWith(Plugin.class).toInstance(plugin);
                    binder.bind(Logger.class).markedWith(Plugin.class).toInstance(plugin.getLogger());
                    binder.bind(Random.class).markedWith(Plugin.class).toInstance(new Random());

                    binder.bind(BukkitAudiences.class).toInstance(BukkitAudiences.create(plugin));

                    options.binder().accept(binder);
                },
            
                new ComponentModule(options, result.getClassesWithAnnotation(Bind.class).loadClasses())
            );
            
            result.getClassesWithFieldAnnotation(Inject.class)
                    .union(result.getClassesWithAnnotation(InjectAll.class))
                    .loadClasses()
                    .forEach(injector::injectStaticMembers);
            
            injector.injectMembers(injector.getInstance(main));
            lifecycle.start();
        }
    }

    public void shutdown() {
        lifecycle.stop();
    }
    
    public Logger logger() {
        return plugin.getLogger();
    }

    public Path folder() {
        return plugin.getDataFolder().toPath();
    }

}