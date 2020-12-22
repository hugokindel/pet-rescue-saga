package com.g10.prs.view.ui;

import com.g10.prs.AI.AI;
import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.print.In;
import com.g10.prs.player.Player;

public class LevelMenu extends Menu {
    public LevelMenu() {
        super("Niveau", new String[] {"Détruire un bloc", "Utiliser la fusée","Utiliser le sabre","laisser le bot jouer"});
    }

    @Override
    public void handleAnswer() {
        int answer = getAnswer();

        if (answer == 1) {
            Popup x = new LevelPopup('x');
            PetRescueSaga.view.showPopup(x);
            int resultX = Integer.parseInt(x.getAnswer());
            Popup y = new LevelPopup('y');
            PetRescueSaga.view.showPopup(y);
            int resultY = PetRescueSaga.view.nextInt();
            PetRescueSaga.level.removeGameMode(resultX, resultY, true);
            if (PetRescueSaga.level.hasWin() /** TODO : || PetRescueSaga.Level.hasLose() */) {
                /** TODO : peut etre trouver une maniere de dire qu'il a gagner ou perdu puis lui proposer de rejouer le niveau ou revenir en arriere
                 * et reset la sauvegarde du niveau */
                PetRescueSaga.view.currentMenu = PetRescueSaga.view.menuBacklog.pop();
            }
            // TODO : sauvegarder la progression du joueur
        } else if ( answer == 2) {
            // TODO : fusee
        } else if ( answer == 3) {
            // TODO : sabre
        } else if ( answer == 4) {
            Pair<Integer,Integer> pair = PetRescueSaga.bot.play(PetRescueSaga.level.getBoard());
            PetRescueSaga.level.removeGameMode(pair.getObject2(), pair.getObject1(), true); //use of Object2 because the first int is the column
            if (PetRescueSaga.level.hasWin() /** TODO : || PetRescueSaga.Level.hasLose() */) {
                /** TODO : peut etre trouver une maniere de dire qu'il a gagner ou perdu puis lui proposer de rejouer le niveau ou revenir en arriere
                 * et reset la sauvegarde du niveau */
                PetRescueSaga.view.currentMenu = PetRescueSaga.view.menuBacklog.pop();
            }
        }
    }

}
