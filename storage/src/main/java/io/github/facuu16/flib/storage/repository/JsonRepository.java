package io.github.facuu16.flib.storage.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.github.facuu16.flib.core.FlibApplication;
import io.github.facuu16.flib.core.util.Paths;
import io.github.facuu16.flib.storage.model.Model;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Getter
@Accessors(fluent = true)
public class JsonRepository<M extends Model<String>> implements Repository<M, String> {
    
    private final Class<M> type = type();
    
    private final Path folder;
    
    private final ObjectMapper mapper;
    
    private final LoadingCache<String, M> cache;
    
    public JsonRepository(@NonNull JsonRepositoryOptions options) {
        this.folder = Paths.ensure(Paths.Type.DIRECTORY, options.folder());
        this.mapper = options.mapper();
        
        final Caffeine<String, M> builder = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .removalListener((id, model, cause) -> {
                    if (model == null || !model.getClass().equals(type))
                        return;
                    
                    save(model);
                });
        
        options.cache().accept(builder);
        
        this.cache = builder.build(id -> load(id).orElse(null));
    }
    
    public JsonRepository(@NonNull Path path) {
        this(JsonRepositoryOptions.builder().folder(path).build());
    }
    
    @Override
    public CompletableFuture<Boolean> update() {
        return CompletableFuture.supplyAsync(() -> true);
    }
    
    @Override
    public CompletableFuture<Boolean> save() {
        return CompletableFuture.supplyAsync(() -> {
            FlibApplication.logger().info("Saving " + cache.estimatedSize() + " objects in cache for repository '" + getClass().getSimpleName() + "'...");
            cache.asMap().forEach((id, model) -> save(model));
            return true;
        });
    }
    
    @Override
    public Optional<M> load(@NonNull String id) {
        final Path path = folder.resolve(id + ".json");
        final File file = path.toFile();
        
        if (!file.exists())
            return Optional.empty();
        
        if (file.length() <= 0) {
            markAsInvalid(path);
            return Optional.empty();
        }
        
        try {
            return Optional.ofNullable(mapper.readValue(file, type));
        } catch (IOException e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not deserialize model '" + id + "'", e);
            markAsInvalid(path);
            
            return Optional.empty();
        }
    }
    
    @Override
    public boolean save(@NonNull M model) {
        final Path path = folder.resolve(model.id() + ".json");
        final File file = Paths.ensure(Paths.Type.FILE, path).toFile();
        
        try {
            mapper.writeValue(file, model);
        } catch (IOException e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not serialize model '" + model.id() + "'", e);
            return false;
        }
        
        return true;
    }
    
    @Override
    public void create(@NonNull M model) {
        cache.put(model.id(), model);
    }
    
    @Override
    public void createAll(@NonNull Iterable<M> models) {
        models.forEach(this::create);
    }
    
    @Override
    public void createIfNotExists(@NonNull M model) {
        if (!exists(model))
            create(model);
    }
    
    @Override
    public M getById(@NonNull String id) {
        return cache.getIfPresent(id);
    }
    
    @Override
    public Optional<M> findById(@NonNull String id) {
        return Optional.ofNullable(cache.getIfPresent(id));
    }
    
    @Override
    public Iterable<M> findAllById(@NonNull Iterable<String> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
    
    @Override
    public Iterable<M> findAll() {
        findAllIds().forEach(cache::refresh);
        return cache.asMap().values();
    }
    
    @Override
    public Iterable<String> findAllIds() {
        final Set<String> ids = new HashSet<>();
        
        try (final Stream<Path> stream = Files.walk(folder)) {
            stream.filter(path -> path.endsWith(".json"))
                    .map(path -> path.getFileName().toString().replace(".json", ""))
                    .forEach(ids::add);
        } catch (IOException e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not find all ids", e);
        }
        
        return ids;
    }
    
    @Override
    public void deleteById(@NonNull String id) {
        cache.invalidate(id);
        
        try {
            Files.deleteIfExists(folder.resolve(id + ".json"));
        } catch (IOException e) {
            FlibApplication.logger().log(Level.SEVERE, "Could not delete model '" + id + "'", e);
        }
    }
    
    @Override
    public void delete(@NonNull M model) {
        deleteById(model.id());
    }
    
    @Override
    public void deleteAll(@NonNull Iterable<M> models) {
        models.forEach(this::delete);
    }
    
    @Override
    public void deleteAllById(@NonNull Iterable<String> ids) {
        ids.forEach(this::deleteById);
    }
    
    @Override
    public void deleteAll() {
        findAllIds().forEach(this::deleteById);
    }
    
    @Override
    public void refreshById(@NonNull String id) {
        cache.refresh(id);
    }
    
    @Override
    public void refresh(@NonNull M model) {
        refreshById(model.id());
    }
    
    @Override
    public void refreshAll(@NonNull Iterable<M> models) {
        models.forEach(model -> refreshById(model.id()));
    }
    
    @Override
    public void refreshAllById(@NonNull Iterable<String> ids) {
        ids.forEach(this::refreshById);
    }
    
    @Override
    public void refreshAll() {
        findAllIds().forEach(cache::refresh);
    }
    
    @Override
    public boolean existsById(@NonNull String id) {
        return getById(id) != null;
    }
    
    @Override
    public boolean exists(@NonNull M model) {
        return existsById(model.id());
    }
    
    @Override
    public long count() {
        return ((Set<String>) findAllIds()).size();
    }
    
    private void markAsInvalid(@NonNull Path file) {
        try {
            final Path invalid = Paths.ensure(Paths.Type.DIRECTORY, folder.resolve("invalid"));
            
            Files.move(file, invalid.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            FlibApplication.logger().log(Level.SEVERE, "Failed to move invalid file '" + file.getFileName() + "'", e);
        }
    }
    
}