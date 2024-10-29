package io.github.facuu16.flib.core.common;

import java.lang.reflect.ParameterizedType;

public interface TypeHelper<T> {

    default Class<T> type() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}