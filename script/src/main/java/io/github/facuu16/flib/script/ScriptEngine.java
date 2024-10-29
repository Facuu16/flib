package io.github.facuu16.flib.script;

import io.github.facuu16.flib.core.FlibApplication;
import lombok.NonNull;
import org.graalvm.polyglot.Context;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public interface ScriptEngine {
    
    static CompletableFuture<Context> eval(@NonNull String language, @NonNull CharSequence source, @NonNull ContextOptions options) {
        return CompletableFuture.supplyAsync(() -> {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final Context context = options.context();
            
            try {
                Thread.currentThread().setContextClassLoader(ScriptEngine.class.getClassLoader());
                context.eval(language, source);
            } catch (Exception e) {
                FlibApplication.logger().log(Level.SEVERE, "Error executing script", e);
            } finally {
                Thread.currentThread().setContextClassLoader(loader);
                
                if (options.strategy() == ContextStrategy.DEFAULT)
                    context.close();
            }
            
            return context;
        }, FlibApplication.executor());
    }

}