package io.github.facuu16.flib.database;

import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class EbeanDatabase implements Database {

    @NonNull
    private final DatabaseConfig configuration;

    private final AtomicReference<io.ebean.Database> database = new AtomicReference<>();

    public io.ebean.Database database() {
        return database.get();
    }

    @Override
    public void start() {
        final ClassLoader original = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            database.set(DatabaseFactory.create(configuration));
        } finally {
            Thread.currentThread().setContextClassLoader(original);
        }
    }

    @Override
    public void stop() {
        if (!isOpen())
            return;

        database.get().shutdown();
    }

    @Override
    public boolean isOpen() {
        return database.get() != null;
    }

}