package com.g10.prs.level;

/** Base class of every cell. */
public class Cell {
    private CellType type;

    public Cell(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }
}