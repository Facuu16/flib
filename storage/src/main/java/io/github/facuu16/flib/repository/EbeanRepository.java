package io.github.facuu16.flib.repository;

import io.github.facuu16.flib.database.EbeanDatabase;
import com.google.common.collect.Lists;
import io.ebean.Database;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EbeanRepository<E, T> implements AsyncRepository<E, T> {

    protected final EbeanDatabase container;

    protected final Database database = container.database();

    protected final Class<E> type = type();

    @Override
    public <S extends E> S save(S entity) {
        database.save(entity);
        return entity;
    }

    @Override
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        database.saveAll(entities);
        return entities;
    }

    @Override
    public Optional<E> findById(T id) {
        return Optional.ofNullable(database.find(type).setId(id).findOne());
    }

    @Override
    public boolean existsById(T id) {
        return database.find(type).setId(id).exists();
    }

    @Override
    public Iterable<E> findAll() {
        return database.find(type).findList();
    }

    @Override
    public Iterable<E> findAllById(Iterable<T> ids) {
        return database.find(type).where().idIn(ids).findList();
    }

    @Override
    public long count() {
        return database.find(type).findCount();
    }

    @Override
    public void deleteById(T id) {
        database.find(type).setId(id).delete();
    }

    @Override
    public void delete(E entity) {
        database.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends T> ids) {
        database.find(type).where().idIn(ids).delete();
    }

    @Override
    public void deleteAll(Iterable<? extends E> entities) {
        database.deleteAll(Lists.newArrayList(entities));
    }

    @Override
    public void deleteAll() {
        database.find(type).delete();
    }

}