package io.github.facuu16.flib.script;

import com.oracle.truffle.js.runtime.JSContextOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.graalvm.polyglot.Context;

@Value
@Builder
@Accessors(fluent = true)
public class ContextOptions {

    @NonNull
    Context context;

    @NonNull
    ContextStrategy strategy;

    public static class ContextOptionsBuilder {

        public ContextOptionsBuilder defaultJsContext() {
            this.context = Context.newBuilder("js")
                    .allowAllAccess(true)
                    .allowIO(true)
                    .option(JSContextOptions.ECMASCRIPT_VERSION_NAME, "12")
                    .build();

            return this;
        }

    }

}