package com.g10.prs.ui;

public class LevelSelectionMenu extends Menu {
    public LevelSelectionMenu() {
        super("Choisir un niveau", true, new String[] {"Introduction"});
    }

    public Integer use() {
        int result = super.use();

        if (result == 1) {
            // TODO: jouer l'introduction
        }

        return result;
    }
}