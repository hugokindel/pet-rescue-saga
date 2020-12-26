package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Menu that show the credits */
public class CreditsMenu extends CliMenu {

    /** class constructor */
    public CreditsMenu() {
        super("Crédits");
    }

    /** draw the content */
    @Override
    protected void drawContent() {
        Out.println(("Programmeurs").toUpperCase());
        Out.println();
        Out.println(TextColor.Red + "Hugo KINDEL");
        Out.println(TextColor.Red + "Maxime JAUROYON");
        Out.println();
        Out.println("Merci d'avoir joué à " + TextColor.Red + "Pet Rescue Saga" + Color.ResetAll + " !");
    }
}
