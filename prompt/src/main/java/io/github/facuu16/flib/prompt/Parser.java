package io.github.facuu16.flib.prompt;

import io.github.facuu16.flib.core.common.TypeHelper;
import lombok.NonNull;

public interface Parser<I, V> extends TypeHelper<I> {

    V parse(@NonNull I input) throws Exception;
    
    boolean canParse(@NonNull I input);

}