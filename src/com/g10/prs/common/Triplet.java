package com.g10.prs.common;

/** A container for a triplet of objects. */
public class Triplet<T, U, V> {
    /** The first object of the triplet. */
    private final T object1;

    /** The second object of the triplet. */
    private final U object2;

    /** The third object of the triplet. */
    private final V object3;

    /**
     * Class constructor.
     *
     * @param object1 The first object.
     * @param object2 The second object.
     * @param object3 The third object.
     */
    public Triplet(T object1, U object2, V object3) {
        this.object1 = object1;
        this.object2 = object2;
        this.object3 = object3;
    }

    /** @return the first object of the triplet. */
    public T getObject1() { return object1; }

    /** @return the second object of the triplet. */
    public U getObject2() {
        return object2;
    }

    /** @return the third object of the triplet. */
    public V getObject3() {
        return object3;
    }


}
