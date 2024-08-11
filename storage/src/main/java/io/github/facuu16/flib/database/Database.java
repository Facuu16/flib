package io.github.facuu16.flib.database;

import io.github.facuu16.flib.common.Lifecycle;

public interface Database extends Lifecycle {

    boolean isOpen();

}