package io.github.facuu16.flib.prompt.configuration;

import io.github.facuu16.flib.configuration.YamlConfigurationFile;
import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.annotation.Bind;
import io.github.facuu16.flib.core.common.Updatable;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.time.Duration;

@Bind(to = Updatable.class)
public class PromptConfiguration extends YamlConfigurationFile {
    
    private PromptConfiguration() throws IOException, InvalidConfigurationException {
        super(FlibApplication.folder().resolve("prompt.yml"));
    }
    
    public Duration expireAfter() {
        return Duration.ofSeconds(getInt("expire-after", 30));
    }
    
    public Component message(@NonNull String key) {
        return component("messages", key);
    }
    
    public Component invalidMessage() {
        return message("invalid");
    }
    
    public Component expireMessage() {
        return message("expire");
    }
    
}