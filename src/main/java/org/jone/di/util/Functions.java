package org.jone.di.util;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Functions {

    public static <T, U> Predicate<U> partial(BiPredicate<T, U> predicate, T t) {
        return u -> predicate.test(t, u);
    }

    public static <T, U, R> Function<U, R> partial(BiFunction<T, U, R> fn, T t) {
        return u -> fn.apply(t, u);
    }
}
