package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class DestroyBlockPopup extends CliPopup {
    public DestroyBlockPopup() {
        super("Détruire un bloc coloré", "Veuillez choisir la position d'un bloc à détruire.");
    }

    @Override
    protected void drawContent() {
        PetRescueSaga.level.print();
        Out.println();
    }

    @Override
    protected void interact() {
        while (true) {
            int x = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "x" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getColumns());
            int y = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "y" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getRows());

            if (PetRescueSaga.level.isMovable(x, y)) {
                PetRescueSaga.level.removeGameMode(x, y, true, true);
                break;
            } else {
                Out.println("Cette position est invalide !");
            }
        }
    }
}
