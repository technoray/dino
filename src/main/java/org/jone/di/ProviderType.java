package org.jone.di;

public class ProviderType {

    private final Class<?> targetClass;

    private final Provider provider;

    public ProviderType(Class<?> targetClass, Provider provider) {
        this.targetClass = targetClass;
        this.provider = provider;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Provider getProvider() {
        return provider;
    }
}
