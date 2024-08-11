package io.github.facuu16.flib.module;

import io.github.facuu16.flib.FlibOptions;
import io.github.facuu16.flib.annotation.Hierarchy;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.common.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class ComponentModule extends AbstractModule {

    private final FlibOptions options;

    private final List<Class<?>> components;

    @Override
    protected void configure() {
        bind(new TypeReference<Set<Updatable>>() {}).toInstance(new HashSet<>());
        bind(new TypeReference<Set<Listener>>() {}).toInstance(new HashSet<>());

        for (final Class<?> component : components) {
            if (options.skips().contains(component))
                continue;

            bind(component).singleton();

            if (component.isAnnotationPresent(Hierarchy.class))
                hierarchy(component, component);

            if (component.isAnnotationPresent(Attached.class))
                asSet(component.getAnnotation(Attached.class).target(), component);
        }
    }

    private void hierarchy(Class<?> component, Class<?> actual) {
        final Class<?> superclass = actual.getSuperclass();

        if (superclass != null) {
            asSet(superclass, component);
            hierarchy(component, superclass);
        }

        for (final Class<?> interfaceclass : actual.getInterfaces()) {
            asSet(interfaceclass, component);
            hierarchy(component, interfaceclass);
        }
    }

    private void asSet(Class<?> type, Class target) {
        multibind(type).asSet().to(target).singleton();
    }

}