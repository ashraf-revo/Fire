package org.revo;

import org.revo.Function.Fun;

/**
 * Created by ashraf on 15/09/16.
 */
public class RequestHolder {
    Fun fun;
    Class type;

    public RequestHolder() {
    }

    public RequestHolder(Fun fun, Class type) {
        this.fun = fun;
        this.type = type;
    }

    public Fun getFun() {
        return fun;
    }

    public RequestHolder setFun(Fun fun) {
        this.fun = fun;
        return this;
    }

    public Class getType() {
        return type;
    }

    public RequestHolder setType(Class type) {
        this.type = type;
        return this;
    }
}
