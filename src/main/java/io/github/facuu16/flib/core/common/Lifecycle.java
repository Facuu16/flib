package io.github.facuu16.flib.core.common;

public interface Lifecycle {
    
    void start();
    
    default void stop() {}

}