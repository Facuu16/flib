package io.github.facuu16.flib.configurate;

import io.github.facuu16.flib.core.common.TypeHelper;
import io.github.facuu16.flib.core.common.Updatable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;

public interface ConfigurationContainer<L extends AbstractConfigurationLoader<CommentedConfigurationNode>, C> extends Updatable, TypeHelper<C> {

    L loader();

    C get();

}