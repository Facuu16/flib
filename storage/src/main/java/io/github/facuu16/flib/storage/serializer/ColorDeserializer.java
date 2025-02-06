package io.github.facuu16.flib.storage.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.awt.Color;
import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {

    @Override
    public Color deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        final JsonNode node = parser.getCodec().readTree(parser);

        return new Color(
                node.get("red").asInt(),
                node.get("green").asInt(),
                node.get("blue").asInt(),
                node.get("alpha").asInt()
        );
    }

}