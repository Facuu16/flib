package io.github.facuu16.flib.prompt;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class PendingPrompt<I, V> {
    
    @NonNull
    Prompt<I, V> prompt;

    @NonNull
    PromptCallback<V> callback;

}