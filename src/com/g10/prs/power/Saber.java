package com.g10.prs.power;

import com.g10.prs.PetRescueSaga;

/** Saber power to destroy a row */
public class Saber extends Power {
    /** the row to destroy */
    private final int row;

    /** class constructor */
    public Saber(int row) {
        this.row = row;
    }

    /** action of the power */
    @Override
    public void use() {
        PetRescueSaga.level.removeRow(row);
    }
}
