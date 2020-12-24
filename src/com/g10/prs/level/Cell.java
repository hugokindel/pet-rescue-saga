package com.g10.prs.level;

import java.util.ArrayList;

/** Base class of every cell. */
public class Cell {
    /** The type of the cell. */
    protected CellType type;

    /**
     * Class constructor.
     *
     * @param type The type of the cell.
     */
    public Cell(CellType type) {
        this.type = type;
    }

    /** @return the type of the cell. */
    public CellType getType() {
        return type;
    }

    public Cell copy(){
        return new Cell(type);
    }
}