package io.github.facuu16.flib.prompt;

import lombok.NonNull;

public class DefaultTextParser<V> implements Parser<String, V> {

    private final Class<V> type = (Class<V>) type();

    @Override
    public V parse(@NonNull String input) throws Exception {
        return type.getConstructor(String.class).newInstance(input);
    }

    @Override
    public boolean canParse(@NonNull String input) {
        try {
            type.getConstructor(String.class);
            return true;
        } catch (NoSuchMethodException exception) {
            return false;
        }
    }

}
