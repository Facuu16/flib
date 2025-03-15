package io.github.facuu16.flib.storage.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackArraySerializer extends JsonSerializer<ItemStack[]> {

    @Override
    public void serialize(ItemStack[] items, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (items == null)
            return;

        try (
                final ByteArrayOutputStream output = new ByteArrayOutputStream();
                final BukkitObjectOutputStream data = new BukkitObjectOutputStream(output)
        ) {
            data.writeInt(items.length);

            for (final ItemStack item : items)
                data.writeObject(item);

            generator.writeString(Base64Coder.encodeLines(output.toByteArray()));
        }
    }

}
