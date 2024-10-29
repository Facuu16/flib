package io.github.facuu16.flib.core.util;

import io.github.facuu16.flib.core.FlibApplication;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class Paths {
    
    public Path ensure(@NonNull Type type, @NonNull String... path) {
        return ensure(type, java.nio.file.Paths.get(FlibApplication.folder().toString(), path));
    }
    
    @SneakyThrows
    public Path ensure(@NonNull Type type, @NonNull Path path) {
        if (Files.exists(path))
            return path;
        
        if (type == Type.DIRECTORY) {
            Files.createDirectories(path);
            return path;
        }
        
        if (!Files.exists(path.getParent()))
            Files.createDirectories(path.getParent());
        
        Files.createFile(path);
        
        return path;
    }
    
    public enum Type {
        FILE,
        DIRECTORY
    }
    
}