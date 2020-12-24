package com.g10.prs.level;

import com.g10.prs.njson.NJsonSerializable;

import java.util.ArrayList;
import java.util.List;

@NJsonSerializable
public class Group {
    @NJsonSerializable
    private int id;

    @NJsonSerializable
    private List<Integer> blocks;

    @NJsonSerializable(necessary = false)
    private boolean canRefill;

    @NJsonSerializable(necessary = false)
    Condition refillCondition;

    public int getId() {
        return id;
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    public boolean canRefill() {
        return canRefill;
    }

    public Condition getRefillCondition() {
        return refillCondition;
    }

    public Group copy(){
        Group res = new Group();
        res.id = id;
        res.blocks = new ArrayList<>(blocks);
        res.canRefill = canRefill;
        res.refillCondition = refillCondition.copy();
        return res;
    }
}
