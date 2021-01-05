package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Menu of the end of a level */
public class EndLevelMenu extends CliMenu {

    /** class constructor */
    public EndLevelMenu(String status) {
        super(PetRescueSaga.level.getName() + " - Vous avez " + status + " !", new String[] {"Recommencer", "Retourner au menu principal"});
    }

    /** draw the content */
    @Override
    public void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();

        Out.println("Vous avez joué " + TextColor.Red + numberOfPlay + Color.ResetAll + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                " et votre score final est de " + TextColor.Red + PetRescueSaga.level.getScore() + Color.ResetAll + " !");
        Out.println("Merci d'avoir joué, " + TextColor.Red + PetRescueSaga.player.getName() + Color.ResetAll + " !");
        Out.println();
        PetRescueSaga.level.print();
        Out.println();
    }

    /** handle the answer of the player */
    @Override
    public void handleChoice(int choice) {
        if (choice == 1) {
        	PetRescueSaga.level = PetRescueSaga.level.getSave();
        	PetRescueSaga.view.changeMenu(new PlayLevelMenu(), false);
        } else if (choice == 2) {
            PetRescueSaga.view.changeMenu(new MainMenu());
        }
    }
}
