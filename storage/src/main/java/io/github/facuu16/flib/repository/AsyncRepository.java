package io.github.facuu16.flib.repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AsyncRepository<E, T> extends Repository<E, T> {

    default <S extends E> CompletableFuture<S> saveAsync(S entity) {
        return CompletableFuture.supplyAsync(() -> save(entity));
    }

    default <S extends E> CompletableFuture<Iterable<S>> saveAllAsync(Iterable<S> entities) {
        return CompletableFuture.supplyAsync(() -> saveAll(entities));
    }

    default CompletableFuture<Optional<E>> findByIdAsync(T id) {
        return CompletableFuture.supplyAsync(() -> findById(id));
    }

    default CompletableFuture<Boolean> existsByIdAsync(T id) {
        return CompletableFuture.supplyAsync(() -> existsById(id));
    }

    default CompletableFuture<Iterable<E>> findAllAsync() {
        return CompletableFuture.supplyAsync(this::findAll);
    }

    default CompletableFuture<Iterable<E>> findAllByIdAsync(Iterable<T> ids) {
        return CompletableFuture.supplyAsync(() -> findAllById(ids));
    }

    default CompletableFuture<Long> countAsync() {
        return CompletableFuture.supplyAsync(this::count);
    }

    default CompletableFuture<Void> deleteByIdAsync(T id) {
        return CompletableFuture.runAsync(() -> deleteById(id));
    }

    default CompletableFuture<Void> deleteAsync(E entity) {
        return CompletableFuture.runAsync(() -> delete(entity));
    }

    default CompletableFuture<Void> deleteAllByIdAsync(Iterable<? extends T> ids) {
        return CompletableFuture.runAsync(() -> deleteAllById(ids));
    }

    default CompletableFuture<Void> deleteAllAsync(Iterable<? extends E> entities) {
        return CompletableFuture.runAsync(() -> deleteAll(entities));
    }

    default CompletableFuture<Void> deleteAllAsync() {
        return CompletableFuture.runAsync(this::deleteAll);
    }

}