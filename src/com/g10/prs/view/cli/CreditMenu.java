package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class CreditMenu extends CliMenu {
    public CreditMenu() {
        super("Crédits");
    }

    @Override
    protected void drawContent() {
        Out.println("Merci d'avoir joué à " + TextColor.Red + "Pet Rescue Saga" + Color.ResetAll + " !");
        Out.println();
        Out.println(("Programmeurs").toUpperCase());
        Out.println();
        Out.println(TextColor.Red + "Hugo KINDEL");
        Out.println(TextColor.Red + "Maxime JAUROYON");
        Out.println();
    }
}
