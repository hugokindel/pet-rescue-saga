package com.g10.prs.model.level;

public class Cell {
    private CellType type;

    public Cell(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }
}