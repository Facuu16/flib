package io.github.facuu16.flib;

import io.github.facuu16.flib.annotation.Component;
import io.github.facuu16.flib.annotation.Plugin;
import mc.obliviate.inventory.InventoryAPI;
import mc.obliviate.inventory.configurable.ConfigurableGuiCache;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Inject;

@Component
public class GuiApi extends InventoryAPI {

    @Inject
    public GuiApi(@Plugin JavaPlugin plugin) {
        super(plugin);
        init();
        ConfigurableGuiCache.resetCaches();
    }

}