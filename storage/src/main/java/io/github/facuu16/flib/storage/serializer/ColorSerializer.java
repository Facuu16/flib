package io.github.facuu16.flib.storage.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.Color;
import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Color> {

    @Override
    public void serialize(Color color, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeNumberField("red", color.getRed());
        generator.writeNumberField("green", color.getGreen());
        generator.writeNumberField("blue", color.getBlue());
        generator.writeNumberField("alpha", color.getAlpha());
        generator.writeEndObject();
    }

}