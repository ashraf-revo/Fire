package org.revo.Function;

/**
 * Created by ashraf on 15/09/16.
 */
@FunctionalInterface
public interface Fun1<T, R> extends Fun{
    R apply(T t);
}
