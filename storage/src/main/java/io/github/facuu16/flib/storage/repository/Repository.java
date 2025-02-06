package io.github.facuu16.flib.storage.repository;

import io.github.facuu16.flib.core.common.TypeHelper;
import io.github.facuu16.flib.core.common.Updatable;
import io.github.facuu16.flib.storage.model.Model;
import lombok.NonNull;

import java.util.Optional;

public interface Repository<M extends Model<T>, T> extends Updatable {
    
    Optional<M> load(@NonNull T id);
    boolean save(@NonNull M model);
    
    void create(@NonNull M model);
    void createAll(@NonNull Iterable<M> models);
    void createIfNotExists(@NonNull M model);
    
    M getById(@NonNull T id);
    Optional<M> findById(@NonNull T id);
    Iterable<M> findAllById(@NonNull Iterable<T> ids);
    Iterable<M> findAll();
    Iterable<T> findAllIds();
    
    void deleteById(@NonNull T id);
    void delete(@NonNull M model);
    void deleteAll(@NonNull Iterable<M> models);
    void deleteAllById(@NonNull Iterable<T> ids);
    void deleteAll();
    
    void refreshById(@NonNull T id);
    void refresh(@NonNull M model);
    void refreshAll(@NonNull Iterable<M> models);
    void refreshAllById(@NonNull Iterable<T> ids);
    void refreshAll();
    
    boolean existsById(@NonNull T id);
    boolean exists(@NonNull M model);
    
    long count();
    
}
