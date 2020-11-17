package com.g10.prs.view.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.In;

public class LevelMenu extends Menu {


    public LevelMenu() {
        super("Level", new String[] {"Detruire un bloc", "Utiliser la fusee","Utiliser le sabre"});
    }

    @Override
    public void handleAnswer(){
        int answer = getAnswer();

        if ( answer == 1) {
            Popup x = new LevelPopup('x');
            PetRescueSaga.view.showPopup(x,false);
            int resultX = PetRescueSaga.view.nextInt();
            Popup y = new LevelPopup('y');
            PetRescueSaga.view.showPopup(y,false);
            int resultY = PetRescueSaga.view.nextInt();
            PetRescueSaga.level.removeGameMode(resultX,resultY,true);
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
        }
    }

}
