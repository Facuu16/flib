package io.github.facuu16.flib.storage.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.facuu16.flib.storage.serializer.ColorDeserializer;
import io.github.facuu16.flib.storage.serializer.ColorSerializer;
import io.github.facuu16.flib.storage.serializer.ItemStackDeserializer;
import io.github.facuu16.flib.storage.serializer.ItemStackSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.bukkit.inventory.ItemStack;

import java.awt.Color;
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
            .setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE)
            .registerModule(new SimpleModule()
                    .addSerializer(Color.class,new ColorSerializer())
                    .addDeserializer(Color.class, new ColorDeserializer())
                    .addSerializer(ItemStack.class, new ItemStackSerializer())
                    .addDeserializer(ItemStack.class, new ItemStackDeserializer()));
    
    @NonNull
    Path folder;
    
    @NonNull
    @Builder.Default
    ObjectMapper mapper = defaultMapper;
    
    @NonNull
    @Builder.Default
    Consumer<Caffeine<String, ?>> cache = builder -> {};
    
}