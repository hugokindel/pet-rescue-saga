package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

public class PlayLevelMenu extends CliMenu {
    public PlayLevelMenu() {
        super(PetRescueSaga.level.getName(), new String[] {"Détruire un bloc coloré", "Utiliser une fusée", "Utiliser un sabre", "Laisser le robot jouer un tour"});
    }

    @Override
    public void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();
        int animalsLeft = PetRescueSaga.level.getAnimalsLeft();

        Out.println("Vous avez joué " + TextColor.Red + numberOfPlay + Color.ResetAll + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                ", votre score est de " + TextColor.Red + PetRescueSaga.level.getScore() + Color.ResetAll + " et il reste " +
                TextColor.Red + animalsLeft + Color.ResetAll + " " + (animalsLeft > 1 ? "animaux" : "animal") + " à sauver...");
        Out.println();
        PetRescueSaga.level.print();
        Out.println();
    }

    @Override
    public void handleChoice(int choice) {
        if (choice == 1) {
            CliPopup.show(new DestroyBlockPopup());
        } else if (choice == 2) {
            CliPopup.show(new UseRocketPopup());
        } else if (choice == 3) {
            CliPopup.show(new UseSaberPopup());
        } else if (choice == 4) {
            Pair<Integer,Integer> pair = PetRescueSaga.bot.play(PetRescueSaga.level.getBoard());
            PetRescueSaga.level.removeGameMode(pair.getObject2(), pair.getObject1(), true, true); //use of Object2 because the first int is the column
        }

        if (PetRescueSaga.level.hasWon()) {
            PetRescueSaga.view.changeMenu(new EndLevelMenu("gagné"), false);
        } else if (PetRescueSaga.level.hasLost()) {
            PetRescueSaga.view.changeMenu(new EndLevelMenu("perdu"), false);
        }
    }
}
