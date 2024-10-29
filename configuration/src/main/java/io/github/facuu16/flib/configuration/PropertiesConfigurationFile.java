package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.util.Paths;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PropertiesConfigurationFile extends Properties implements ConfigurationFile {
    
    protected final File file;
    
    public PropertiesConfigurationFile(@NonNull Path path, boolean defaults) throws IOException {
        this.file = Paths.ensure(Paths.Type.FILE, path).toFile();
        defaults(defaults);
    }
    
    public PropertiesConfigurationFile(@NonNull Path path) throws IOException {
        this(path, true);
    }
    
    @Override
    public void copyDefaults(@NonNull InputStream input) throws IOException {
        load(input);
        save().join();
    }
    
    @Override
    public CompletableFuture<Boolean> update() {
        return CompletableFuture.supplyAsync(() -> {
            try (final Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)) {
                clear();
                load(reader);
                return true;
            } catch (IOException e) {
                FlibApplication.logger().log(Level.SEVERE, "Could not reload '" + file.getName() + "' properties file", e);
                return false;
            }
        });
    }
    
    @Override
    public CompletableFuture<Boolean> save() {
        return CompletableFuture.supplyAsync(() -> {
            try (final Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
                store(writer, null);
                return true;
            } catch (IOException e) {
                FlibApplication.logger().log(Level.SEVERE, "Could not save '" + file.getName() + "' properties file", e);
                return false;
            }
        });
    }
    
}