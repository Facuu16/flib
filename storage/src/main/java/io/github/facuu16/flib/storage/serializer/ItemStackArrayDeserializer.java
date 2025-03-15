package io.github.facuu16.flib.storage.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ItemStackArrayDeserializer extends JsonDeserializer<ItemStack[]> {

    @Override
    public ItemStack[] deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        final String base64 = parser.getText();

        if (base64.isEmpty())
            return null;

        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(base64)))) {
            final int length = data.readInt();
            final ItemStack[] items = new ItemStack[length];

            for (int i = 0; i < length; i++)
                items[i] = (ItemStack) data.readObject();

            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Error deserializing itemstack array", e);
        }
    }

}
