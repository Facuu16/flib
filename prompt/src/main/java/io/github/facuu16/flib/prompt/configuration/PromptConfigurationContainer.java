package io.github.facuu16.flib.prompt.configuration;

import io.github.facuu16.flib.FlibApplication;
import io.github.facuu16.flib.annotation.Attached;
import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.common.Updatable;
import io.github.facuu16.flib.configuration.HoconConfigurationContainer;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

@Component
@Attached(target = Updatable.class)
public class PromptConfigurationContainer extends HoconConfigurationContainer<PromptConfiguration> {

    private PromptConfigurationContainer() {
        super(HoconConfigurationLoader.builder().path(FlibApplication.folder().resolve("prompt.conf")));
    }

}