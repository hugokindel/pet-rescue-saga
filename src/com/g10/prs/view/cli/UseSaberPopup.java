package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class UseSaberPopup extends CliPopup {
    public UseSaberPopup() {
        super("Utiliser un sabre");
    }

    @Override
    protected void drawContent() {
        PetRescueSaga.level.print();
        Out.println();
    }

    @Override
    protected void interact() {
        int y = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "y" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getColumns());
        PetRescueSaga.level.removeRow(y);
    }
}
