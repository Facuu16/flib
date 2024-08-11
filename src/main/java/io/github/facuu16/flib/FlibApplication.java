package io.github.facuu16.flib;

import io.github.facuu16.flib.annotation.Application;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.annotation.Plugin;
import io.github.facuu16.flib.module.ApplicationModule;
import io.github.facuu16.flib.module.ComponentModule;
import io.github.facuu16.flib.service.LifecycleService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@InjectAll
@UtilityClass
@Accessors(fluent = true)
public class FlibApplication {

    private LifecycleService service;

    @Plugin
    @Getter
    private JavaPlugin plugin;

    public void run() {
        run(FlibOptions.builder().build());
    }

    public void run(@NonNull FlibOptions options) {
        try (
            final ScanResult result = new ClassGraph().enableAllInfo().acceptPackages().scan()
        ) {
            final ApplicationModule application = new ApplicationModule(result.getClassesWithAnnotation(Application.class)
                    .filter(info -> info.extendsSuperclass(JavaPlugin.class))
                    .loadClasses());

            final ComponentModule component = new ComponentModule(
                    options, result.getClassesWithAnnotation(Component.class).loadClasses()
            );

            final Injector injector = Injector.create(
                application, component,

                binder -> {
                    final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(FlibApplication.class);

                    binder.bind(JavaPlugin.class).markedWith(Plugin.class).toInstance(plugin);
                    binder.bind(BukkitAudiences.class).toInstance(BukkitAudiences.create(plugin));
                    binder.bind(Random.class).toInstance(new Random());

                    options.binder().accept(binder);
                }
            );

            result.getClassesWithFieldAnnotation(Inject.class)
                    .union(result.getClassesWithAnnotation(InjectAll.class))
                    .loadClasses()
                    .forEach(injector::injectStaticMembers);

            application.applications().forEach(app -> injector.injectMembers(injector.getInstance(app)));

            service.startAsync().exceptionally(e -> {
                logger().log(Level.SEVERE, "Could not start service '" + service.getClass().getSimpleName() + "'", e);
                return null;
            }).join();
        }
    }

    public void shutdown() {
        service.stopAsync().exceptionally(e -> {
            logger().log(Level.SEVERE, "Could not stop service '" + service.getClass().getSimpleName() + "'", e);
            return null;
        }).join();
    }

    public Logger logger() {
        return plugin.getLogger();
    }

    public Path folder() {
        return plugin.getDataFolder().toPath();
    }

}