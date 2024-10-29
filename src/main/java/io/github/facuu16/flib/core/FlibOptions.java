package io.github.facuu16.flib.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;
import team.unnamed.inject.Binder;

import java.util.Set;
import java.util.function.Consumer;

@Value
@Builder
@Accessors(fluent = true)
public class FlibOptions {

    @NonNull
    Consumer<Binder> binder;

    @Singular
    Set<Class<?>> skips;

}