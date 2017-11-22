package org.jone.di;

import java.lang.invoke.MethodHandle;

public class ProviderFactory {

    public Provider from(Object source, MethodHandle handle) {
        return new MethodHandleProvider(source, handle);
    }

    public Provider singleton(Provider provider) {
        return new SingletonProvider(provider);
    }
}
