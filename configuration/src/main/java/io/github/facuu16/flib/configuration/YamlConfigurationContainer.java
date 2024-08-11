package io.github.facuu16.flib.configuration;

import lombok.NonNull;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class YamlConfigurationContainer<C> extends AbstractConfigurationContainer<YamlConfigurationLoader, C> {

    public YamlConfigurationContainer(@NonNull YamlConfigurationLoader.Builder builder) {
        super(builder.defaultOptions(DEFAULTS).build());
    }

}