package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Popup to use a saber power */
public class UseSaberPopup extends CliPopup {

    /** class constructor */
    public UseSaberPopup() {
        super("Utiliser un sabre");
    }

    /** draw the content (level) */
    @Override
    protected void drawContent() {
        PetRescueSaga.level.print();
        Out.println();
    }

    /** interact with the player and remove a row */
    @Override
    protected void interact() {
        int y = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "y" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getColumns());
        PetRescueSaga.level.removeRow(y);
    }
}
