package io.github.facuu16.flib.prompt;

import io.github.facuu16.flib.common.TypeHelper;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class PendingPrompt<I, V> implements TypeHelper<I> {

    Class<I> inputType = type();

    @NonNull
    Prompt<I, V> prompt;

    @NonNull
    PromptCallback<V> callback;

}