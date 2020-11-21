package com.g10.prs.power;

import com.g10.prs.PetRescueSaga;

public class Saber extends Power {
    private final int row;

    public Saber(int row) {
        this.row = row;
    }

    @Override
    public void use() {
        PetRescueSaga.level.removeRow(row);
    }
}
