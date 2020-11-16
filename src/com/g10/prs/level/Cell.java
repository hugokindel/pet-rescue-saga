package com.g10.prs.level;

/** Base class of every cell. */
public class Cell {
    /** The type of the cell. */
    private CellType type;

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
}