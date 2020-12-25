package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

import javax.swing.*;

public class EndLevelMenu extends GuiMenu {
    public EndLevelMenu(String status) {
        super(PetRescueSaga.level.getName() + " - Vous avez " + status + " !");
    }

    @Override
    protected void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();

        JPanel contentPanel = new JPanel();
        contentPanel.add(new Label("<html>Vous avez joué " + numberOfPlay + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                " et votre score final est de " + PetRescueSaga.level.getScore() + " !" +
                "<br><br>" +
                "Merci d'avoir joué à Pet Rescue Saga !</html>").asJLabel());
        panel.add(contentPanel);
    }
}
