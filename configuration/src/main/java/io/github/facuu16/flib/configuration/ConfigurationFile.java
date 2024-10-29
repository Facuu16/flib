package io.github.facuu16.flib.configuration;

import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.common.Updatable;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ConfigurationFile extends Updatable {
    
    File file();
    
    default void defaults(boolean apply) throws IOException {
        if (apply && file().length() <= 0)
            copyDefaults();
    }
    
    default void copyDefaults() throws IOException {
        copyDefaults(FlibApplication.plugin().getResource(file().getName()));
    }
    
    void copyDefaults(@NonNull InputStream input) throws IOException;
    
}