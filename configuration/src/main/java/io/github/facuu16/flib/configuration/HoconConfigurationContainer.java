package io.github.facuu16.flib.configuration;

import lombok.NonNull;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

public class HoconConfigurationContainer<C> extends AbstractConfigurationContainer<HoconConfigurationLoader, C> {

    public HoconConfigurationContainer(@NonNull HoconConfigurationLoader.Builder builder) {
        super(builder.prettyPrinting(true).defaultOptions(DEFAULTS).build());
    }

}