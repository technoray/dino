package org.jone.di;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationContext implements Context {

    private final Map<Class<?>, Provider> providers;

    public ApplicationContext() {
        this.providers = new HashMap<>();
    }

    public void register(Class<?> targetClass, Provider provider) {
        Objects.requireNonNull(targetClass);
        Objects.requireNonNull(provider);

        if (providers.containsKey(targetClass)) {
            throw new RuntimeException("A provider for is already registered for the given type: " +
                    targetClass);
        }

        providers.put(targetClass, provider);
    }

    public void register(ProviderTypeLookup scanner) {
        scanner.lookup().forEach(this::register);
    }

    private void register(ProviderType providerType) {
        register(providerType.getTargetClass(), providerType.getProvider());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> targetClass) {
        Provider provider = providers.get(targetClass);
        Objects.requireNonNull(provider, "Could not find a provider for the given type: " +
                targetClass);
        return (T) provider.provide(this);
    }
}
