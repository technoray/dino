package org.jone.di;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.jone.di.util.ReflectionUtils.unreflect;
import static org.jone.di.util.ReflectionUtils.findAnnotatedMethods;
import static org.jone.di.util.ReflectionUtils.getDeclaredAnnotation;

public class ProviderTypeLookup {

    private final ProviderFactory providerFactory;

    private final Object source;

    private final MethodHandles.Lookup lookup;

    public ProviderTypeLookup(Object source) {
        this(new ProviderFactory(), source);
    }

    public ProviderTypeLookup(ProviderFactory providerFactory, Object source) {
        Objects.requireNonNull(providerFactory);
        Objects.requireNonNull(source);

        this.providerFactory = providerFactory;
        this.source = source;
        this.lookup = MethodHandles.lookup();
    }

    public Collection<ProviderType> lookup() {
        Collection<Method> methods = findAnnotatedMethods(source.getClass(), Provide.class);
        return methods.stream()
                .map(this::asProviderSource)
                .map(this::createProviderType)
                .collect(Collectors.toList());
    }

    private ProviderSource asProviderSource(Method method) {
        return new ProviderSource(method, lookup);
    }

    private ProviderType createProviderType(ProviderSource providerSource) {
        Class<?> targetClass = providerSource.getTargetClass();
        Provider provider = createProvider(providerSource);
        return createProviderType(targetClass, provider);
    }

    private Provider createProvider(ProviderSource providerSource) {
        Provider provider = providerFactory.from(source, providerSource.getMethodHandle());
        if (providerSource.isSingleton()) {
            return providerFactory.singleton(provider);
        }
        return provider;
    }

    private ProviderType createProviderType(Class<?> targetClass, Provider provider) {
        return new ProviderType(targetClass, provider);
    }

    static class ProviderSource {

        private Method method;

        private MethodHandles.Lookup lookup;

        ProviderSource(Method method, MethodHandles.Lookup lookup) {
            this.method = method;
            this.lookup = lookup;
        }

        boolean isSingleton() {
            return getDeclaredAnnotation(method, Provide.class)
                    .map(Provide::singleton)
                    .orElse(false);
        }

        MethodHandle getMethodHandle() {
            return unreflect(lookup, method);
        }

        Class<?> getTargetClass() {
            return method.getReturnType();
        }
    }
}
