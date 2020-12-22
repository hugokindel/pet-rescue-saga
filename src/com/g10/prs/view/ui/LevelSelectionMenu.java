package com.g10.prs.view.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.PrsException;
import com.g10.prs.common.Resources;
import com.g10.prs.level.Level;

public class LevelSelectionMenu extends Menu {
    public LevelSelectionMenu() {
        super("Choisir un niveau", new String[] {"Introduction"});
    }

    public void handleAnswer() {
        int answer = getAnswer();

        try {
            if (answer == 1) {
                // TODO : prendre en compte la progression du joueur pour qu'il continue le niveau plus tard
                PetRescueSaga.level = Level.load(Resources.getCampaignLevelsDirectory() + "/level_01.njson");
                PetRescueSaga.view.showMenu(new LevelMenu());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}