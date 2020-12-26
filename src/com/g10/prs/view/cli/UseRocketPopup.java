package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Popup to use a rocket power */
public class UseRocketPopup extends CliPopup {

    /** class constructor */
    public UseRocketPopup() {
        super("Utiliser une fusée");
    }

    /** draw the content (level) */
    @Override
    protected void drawContent() {
        PetRescueSaga.level.print();
        Out.println();
    }

    /** interact with the player and remove a column */
    @Override
    protected void interact() {
        int x = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "x" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getColumns());
        PetRescueSaga.level.removeColumn(x);
    }
}
