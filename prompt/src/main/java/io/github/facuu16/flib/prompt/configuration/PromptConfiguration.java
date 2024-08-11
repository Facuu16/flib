package io.github.facuu16.flib.prompt.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.time.Duration;

@Data
@ConfigSerializable
@Accessors(fluent = true)
public class PromptConfiguration {

    private Duration expireAfter = Duration.ofSeconds(30);

    private Component invalidMessage = Component.text("Invalid input!", NamedTextColor.RED);
    private Component expireMessage = Component.text("Canceling...", NamedTextColor.RED);

}