package io.github.facuu16.flib.core.module;

import io.github.facuu16.flib.core.FlibOptions;
import io.github.facuu16.flib.core.annotation.Hierarchy;
import io.github.facuu16.flib.core.annotation.Bind;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import team.unnamed.inject.AbstractModule;

import java.util.List;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class ComponentModule extends AbstractModule {

    private final FlibOptions options;

    private final List<Class<?>> components;

    @Override
    protected void configure() {
        for (final Class<?> component : components) {
            if (options.skips().contains(component))
                continue;

            bind(component).singleton();
            
            if (component.isAnnotationPresent(Hierarchy.class))
                hierarchy(component, component);
            
            final Class<?> to = component.getAnnotation(Bind.class).to();
            
            if (to != Bind.class)
                asSet(to, component);
        }
    }

    private void hierarchy(@NonNull Class<?> component, @NonNull Class<?> actual) {
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

    private void asSet(@NonNull Class<?> type, @NonNull Class target) {
        multibind(type).asSet().to(target).singleton();
    }

}