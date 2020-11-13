package com.g10.prs.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.core.printer.Out;

import java.util.Scanner;

public class LevelSelectionMenu extends Menu {
    public LevelSelectionMenu() {
        super("Choisir un niveau", true, new String[] {"Introduction"});
    }

    public Integer use(Scanner sc) {
        int result = super.use(sc);

        if (result == 1) {
            // TODO: jouer l'introduction
        }

        return result;
    }
}