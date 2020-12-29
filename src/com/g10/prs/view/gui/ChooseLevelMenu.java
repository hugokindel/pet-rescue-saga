package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Resources;
import com.g10.prs.level.Level;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Menu to choose the level */
public class ChooseLevelMenu extends GuiMenu {
    /** list of all the levels */
    List<String> levels;

    /** class constructor */
    public ChooseLevelMenu() {
        super("Choisir un niveau", null, true, "background.png", "levels.png");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        Panel topPanel = new Panel();
        topPanel.add(new Label("Veuillez choisir un niveau à jouer :", 26, 0, 0, 20, 0));
        panel.add(topPanel);

        Panel contentPanel = new Panel();
        levels = new ArrayList<>();
        levels.addAll(Resources.getCampaignLevelsList());

        String[] levelNames = new String[levels.size()];

        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(0, 0, 10, 10);
        constr.anchor = GridBagConstraints.CENTER;
        constr.gridx = -1;

        for (int i = 0; i < levelNames.length; i++) {
            try {
                levelNames[i] = Level.load(Resources.getLevelsDirectory() + "/" + levels.get(i)).getName();
                Button levelButton = new Button(levelNames[i]);
                int finalI = i;
                levelButton.addActionListener(ev -> {
                    try {
                        PetRescueSaga.level = Level.load(Resources.getLevelsDirectory() + "/" + levels.get(finalI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    PetRescueSaga.view.changeMenu(new PlayLevelMenu());
                });
                constr.gridx++;
                contentPanel.add(levelButton.get(), constr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        contentPanel.setBorder(0, 0, 20, 0);
        panel.add(contentPanel);
    }
}
