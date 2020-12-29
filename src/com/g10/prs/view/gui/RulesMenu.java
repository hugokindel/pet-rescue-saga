package com.g10.prs.view.gui;

import javax.swing.*;

/** Menu that show the rules */
public class RulesMenu extends GuiMenu {

    /** class constructor */
    public RulesMenu() {
        super("Règles du jeu", null, true, "background.png", "rules.png");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        Panel contentPanel = new Panel();
        contentPanel.add(new Label("<html>Voici comment jouer à Pet Rescue Saga !<br/>" +
                "Votre but est de sauver tous les animaux (indiqués par des lettres sur la carte du niveau).<br/>" +
                "Pour le faire, vous devez enlever les blocs colorés, ce qui va aussi affecter votre score final.<br/>" +
                "N'oubliez pas de tenir compte de la gravité ! Les blocs tombent vers le bas et se déplace vers la gauche lorsqu'ils sont sur un obstacle et que la voie est libre.<br/>" +
                "Il est aussi possible d'utiliser la fusée pour supprimer tous les blocs d'une colonne ou le sabre pour supprimer tous les blocs d'une ligne.<br/>" +
                "<br/>" +
                "Le fonctionnement du score est le suivant :<br/>" +
                "+ 100 - pour chaque bloc enlevé.<br/>" +
                "+ 1000 - pour chaque animal sauvé.<br/>" +
                "+ 5000 - lorsque la partie est gagné.<br/>" +
                "<br/>" +
                "Bon jeu !</html>", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12));
        contentPanel.setBorder(0, 0, 20, 0);
        panel.add(contentPanel);
    }
}
