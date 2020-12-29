package com.g10.prs.level;

import com.g10.prs.njson.NJsonSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * A group is a list of block IDs which can be used within a level implementation
 * to permit to use random choice of blocks when generating, or the refill mechanism.
 */
@NJsonSerializable
public class Group {
    /** The ID of the group. */
    @NJsonSerializable
    private int id;

    /** The list of block IDs. */
    @NJsonSerializable
    private List<Integer> blocks;

    /** If it supports refill (new blocks falling from the top of the level when possible). */
    @NJsonSerializable(necessary = false)
    private boolean canRefill;

    /** Condition to verify when using refill, to check if we can refill at a specific position. */
    @NJsonSerializable(necessary = false)
    Condition refillCondition;

    /** @return the ID of the group. */
    public int getId() {
        return id;
    }

    /** @return the list of block IDs. */
    public List<Integer> getBlocks() {
        return blocks;
    }

    /** @return if it can refill. */
    public boolean canRefill() {
        return canRefill;
    }

    /** @return the refill condition. */
    public Condition getRefillCondition() {
        return refillCondition;
    }

    /**
     * Creates a deep copy the Group.
     *
     * @return the copy.
     */
    public Group copy(){
        Group res = new Group();
        res.id = id;
        res.blocks = new ArrayList<>(blocks);
        res.canRefill = canRefill;
        if (refillCondition != null) {
            res.refillCondition = refillCondition.copy();
        } else {
            res.refillCondition = null;
        }
        return res;
    }
}
