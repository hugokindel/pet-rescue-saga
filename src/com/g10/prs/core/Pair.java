package com.g10.prs.core;

public class Pair<T, U> {
    private final T object1;
    private final U object2;

    public Pair(T object1, U object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public T getObject1() {
        return object1;
    }

    public U getObject2() {
        return object2;
    }
}
