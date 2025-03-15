package io.github.facuu16.flib.storage.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.nio.file.Path;
import java.util.function.Consumer;

@Value
@Builder
@Accessors(fluent = true)
public class JsonRepositoryOptions {

    @Getter
    static ObjectMapper defaultMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);
    
    @NonNull
    Path folder;
    
    @NonNull
    @Builder.Default
    ObjectMapper mapper = defaultMapper;
    
    @NonNull
    @Builder.Default
    Consumer<Caffeine<String, ?>> cache = builder -> {};
    
}