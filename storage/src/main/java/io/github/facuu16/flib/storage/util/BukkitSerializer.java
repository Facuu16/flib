package io.github.facuu16.flib.storage.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@UtilityClass
public class BukkitSerializer {

    public String url(@NonNull String url) throws URISyntaxException {
        new URI(url);
        return java.util.Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes());
    }

    public String itemStack(@NonNull ItemStack item) throws IOException {
        try (
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            final BukkitObjectOutputStream data = new BukkitObjectOutputStream(output)
        ) {
            data.writeObject(item);
            return Base64Coder.encodeLines(output.toByteArray());
        }
    }

    public ItemStack itemStack(@NonNull String base64) throws IOException, ClassNotFoundException {
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(base64)))) {
            return (ItemStack) data.readObject();
        }
    }

    public String itemStackArray(@NonNull ItemStack[] items) throws IOException {
        try (
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            final BukkitObjectOutputStream data = new BukkitObjectOutputStream(output)
        ) {
            data.writeInt(items.length);

            for (final ItemStack item : items)
                data.writeObject(item);

            return Base64Coder.encodeLines(output.toByteArray());
        }
    }

    public ItemStack[] itemStackArray(@NonNull String base64) throws IOException, ClassNotFoundException {
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(base64)))) {
            final int length = data.readInt();
            final ItemStack[] items = new ItemStack[length];

            for (int i = 0; i < length; i++)
                items[i] = (ItemStack) data.readObject();

            return items;
        }
    }

}