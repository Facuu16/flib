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
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.Injector;

import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Accessors(fluent = true)
public class FlibApplication {
    
    @Getter
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    
    @Getter
    private static final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(FlibApplication.class);
    
    @Inject
    private static LifecycleService lifecycle;
    
    public static void run() {
        run(FlibOptions.builder().build());
    }

    public static <V extends JavaPlugin> void run(@NonNull FlibOptions options) {
        final Class<V> main = (Class<V>) plugin.getClass();
        
        try (
            final ScanResult result = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(main.getPackage().getName(), "io.github.facuu16.flib.*")
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
            lifecycle.start().join();
        }
    }

    public static void shutdown() {
        lifecycle.stop().join();
    }
    
    public static Logger logger() {
        return plugin.getLogger();
    }

    public static Path folder() {
        return plugin.getDataFolder().toPath();
    }

}