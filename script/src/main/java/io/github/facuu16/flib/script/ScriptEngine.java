package io.github.facuu16.flib.script;

import io.github.facuu16.flib.FlibApplication;
import lombok.NonNull;
import org.graalvm.polyglot.Context;

import java.util.logging.Level;

public interface ScriptEngine {

    static Context eval(@NonNull String language, @NonNull CharSequence source, @NonNull ContextOptions options) {
        final Context context = options.context();

        try {
            context.eval(language, source);
        } catch (Exception e) {
            FlibApplication.logger().log(Level.SEVERE, "Error executing script", e);
        } finally {
            if (options.strategy() == ContextStrategy.DEFAULT)
                context.close();
        }

        return context;
    }

}