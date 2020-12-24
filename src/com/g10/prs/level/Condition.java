package com.g10.prs.level;

import com.g10.prs.njson.NJsonSerializable;

import java.util.ArrayList;
import java.util.List;

@NJsonSerializable
public class Condition {
    @NJsonSerializable
    private String name;

    @NJsonSerializable
    private List<String> values;

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

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

    public Condition copy(){
        Condition res = new Condition();
        res.name = name;
        res.values = new ArrayList<>(values);
        return res;
    }
}
