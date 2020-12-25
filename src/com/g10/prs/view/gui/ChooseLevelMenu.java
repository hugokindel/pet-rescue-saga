package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Resources;
import com.g10.prs.level.Level;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChooseLevelMenu extends GuiMenu {
    List<String> levels;

    public ChooseLevelMenu() {
        super("Choisir un niveau");
    }

    @Override
    protected void drawContent() {
        JPanel contentPanel = new JPanel();
        levels = new ArrayList<>();
        levels.addAll(Resources.getCampaignLevelsList());

        String[] levelNames = new String[levels.size()];

        for (int i = 0; i < levelNames.length; i++) {
            try {
                levelNames[i] = Level.load(Resources.getLevelsDirectory() + "/" + levels.get(i)).getName();
                JButton quitButton = new JButton(levelNames[i]);
                int finalI = i;
                quitButton.addActionListener(ev -> {
                    try {
                        PetRescueSaga.level = Level.load(Resources.getLevelsDirectory() + "/" + levels.get(finalI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    PetRescueSaga.view.changeMenu(new PlayLevelMenu());
                });
                contentPanel.add(quitButton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        panel.add(contentPanel);
    }
}
