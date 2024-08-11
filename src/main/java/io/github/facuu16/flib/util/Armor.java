package io.github.facuu16.flib.util;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public enum Armor {

    HELMET {

        @Override
        public void set(@NonNull Player player, @NonNull ItemStack item) {
            player.getInventory().setHelmet(item);
            player.updateInventory();
        }

    },

    CHESTPLATE {

        @Override
        public void set(@NonNull Player player, @NonNull ItemStack item) {
            player.getInventory().setChestplate(item);
            player.updateInventory();
        }

    },

    LEGGINGS {

        @Override
        public void set(@NonNull Player player, @NonNull ItemStack item) {
            player.getInventory().setLeggings(item);
            player.updateInventory();
        }

    },

    BOOTS {

        @Override
        public void set(@NonNull Player player, @NonNull ItemStack item) {
            player.getInventory().setBoots(item);
            player.updateInventory();
        }

    },

    NONE {
        @Override
        public void set(@NonNull Player player, @NonNull ItemStack item) {}
    };

    public abstract void set(@NonNull Player player, @NonNull ItemStack item);

    public static Armor determine(@NonNull Player player, @NonNull ItemStack item) {
        final PlayerInventory inventory = player.getInventory();

        return item.equals(inventory.getHelmet()) ? HELMET :
               item.equals(inventory.getChestplate()) ? CHESTPLATE :
               item.equals(inventory.getLeggings()) ? LEGGINGS :
               item.equals(inventory.getBoots()) ? BOOTS : NONE;
    }

}