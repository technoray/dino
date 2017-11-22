package org.jone.di.util;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.jone.di.util.Functions.partial;

public class ReflectionUtils {

    public static boolean hasAnnotation(Class<? extends Annotation> annotation, Method method) {
        return method.getDeclaredAnnotation(annotation) != null;
    }

    public static <T extends Annotation> Optional<T> getDeclaredAnnotation(
            Method method, Class<T> annotation) {
        return Optional.ofNullable(method.getDeclaredAnnotation(annotation));
    }

    public static Collection<Method> findAnnotatedMethods(
            Class<?> sourceClass, Class<? extends Annotation> annotation) {
        Predicate<Method> withAnnotation = partial(ReflectionUtils::hasAnnotation, annotation);
        return Arrays.stream(sourceClass.getDeclaredMethods())
                .filter(withAnnotation)
                .collect(Collectors.toList());
    }

    public static MethodHandle unreflect(MethodHandles.Lookup lookup, Method method) {
        try {
            return lookup.unreflect(method);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
