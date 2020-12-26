package com.g10.prs.power;

import com.g10.prs.PetRescueSaga;

/** Firework power to destroy a column */
public class Firework extends Power {
    /** the column to destroy */
    private final int column;


    /** class constructor */
    public Firework(int column) {
        this.column = column;
    }

    /** action of the power */
    @Override
    public void use() {
        PetRescueSaga.level.removeColumn(column);
    }
}
