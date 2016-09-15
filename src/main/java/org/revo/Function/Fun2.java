package org.revo.Function;

/**
 * Created by ashraf on 15/09/16.
 */
@FunctionalInterface
public interface Fun2<T, U, R> extends Fun {
    R apply(T t, U u);
}


