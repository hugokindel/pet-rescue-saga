package com.g10.prs.power;

import com.g10.prs.PetRescueSaga;

public class Firework extends Power {
    private final int column;

    public Firework(int column) {
        this.column = column;
    }

    @Override
    public void use() {
        PetRescueSaga.level.removeColumn(column);
    }
}
