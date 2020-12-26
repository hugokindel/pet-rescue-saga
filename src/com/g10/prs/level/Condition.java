package com.g10.prs.level;

import com.g10.prs.njson.NJsonSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * A Condition is a specific type which defines a condition to verify.
 * It is used in the Group system, to permit refill with the use of Conditions.
 */
@NJsonSerializable
public class Condition {
    /** The name of the condition. */
    @NJsonSerializable
    private String name;

    /** The values to verify with. */
    @NJsonSerializable
    private List<String> values;

    /** @return the name of the condition. */
    public String getName() {
        return name;
    }

    /** @return the list of values to verify with. */
    public List<String> getValues() {
        return values;
    }

    /**
     * Checks if the condition is true or not.
     *
     * @param nameToCompare Name to verify.
     * @param valueToCompare Value to verify.
     * @return if it is true.
     */
    public boolean isTrue(String nameToCompare, String valueToCompare) {
        if (!name.equals(nameToCompare)) {
            return false;
        }

        for (String value : values) {
            if (value.equals(valueToCompare)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a deep copy the Condition.
     *
     * @return the copy.
     */
    public Condition copy() {
        Condition res = new Condition();
        res.name = name;
        res.values = new ArrayList<>(values);
        return res;
    }
}
