package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.common.TypeHelper;
import io.github.facuu16.flib.common.Updatable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

public interface ConfigurationContainer<L extends AbstractConfigurationLoader<CommentedConfigurationNode>, C> extends Updatable, TypeHelper<C> {

    L loader();

    C get();

}