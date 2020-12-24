package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class UseRocketPopup extends CliPopup {
    public UseRocketPopup() {
        super("Utiliser une fusée");
    }

    @Override
    protected void drawContent() {
        PetRescueSaga.level.print();
        Out.println();
    }

    @Override
    protected void interact() {
        int x = nextInt("Veuillez choisir un coordonné " + TextColor.Red + "x" + Color.ResetAll, true, 0, true, PetRescueSaga.level.getColumns());
        PetRescueSaga.level.removeColumn(x);
    }
}
