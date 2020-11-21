package com.g10.prs.level;

import com.g10.prs.njson.NJsonSerializable;

import java.util.List;

@NJsonSerializable
public class Group {
    @NJsonSerializable
    private int id;

    @NJsonSerializable
    private List<Integer> blocks;

    public int getId() {
        return id;
    }

    public List<Integer> getBlocks() {
        return blocks;
    }
}
