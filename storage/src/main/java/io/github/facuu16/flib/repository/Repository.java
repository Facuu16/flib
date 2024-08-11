package io.github.facuu16.flib.repository;

import io.github.facuu16.flib.common.TypeHelper;

import java.util.Optional;

public interface Repository<E, T> extends TypeHelper<E> {

    <S extends E> S save(S entity);

    <S extends E> Iterable<S> saveAll(Iterable<S> entities);

    Optional<E> findById(T id);

    boolean existsById(T id);

    Iterable<E> findAll();

    Iterable<E> findAllById(Iterable<T> ids);

    long count();

    void deleteById(T id);

    void delete(E entity);

    void deleteAllById(Iterable<? extends T> ids);

    void deleteAll(Iterable<? extends E> entities);

    void deleteAll();

}