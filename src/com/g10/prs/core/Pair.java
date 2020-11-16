package com.g10.prs.core;

/** A container for a pair of objects. */
public class Pair<T, U> {
    /** The first object of the pair. */
    private final T object1;

    /** The second object of the pair. */
    private final U object2;

    /**
     * Class constructor.
     *
     * @param object1 The first object.
     * @param object2 The second object.
     */
    public Pair(T object1, U object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    /** @return the first object of the pair. */
    public T getObject1() {
        return object1;
    }

    /** @return the second object of the pair. */
    public U getObject2() {
        return object2;
    }
}
