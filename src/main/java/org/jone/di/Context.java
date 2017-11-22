package org.jone.di;

public interface Context {

    <T> T getInstance(Class<T> targetClass);
}
