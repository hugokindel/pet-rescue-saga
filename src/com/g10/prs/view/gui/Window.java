package com.g10.prs.view.gui;

import javax.swing.*;

public class Window  extends JFrame {
    private static final int largeur = 300;
    private static final int hauteur = 200;

    public Window() {
        JButton boutonJ = new JButton("Jaune");
        JButton boutonB = new JButton("Bleu");
        JButton boutonR = new JButton("Rouge");
        JButton boutonV = new JButton("Violet");

        JPanel panel = new JPanel();

        panel.add(boutonJ);
        panel.add(boutonB);
        panel.add(boutonR);
        panel.add(boutonV);

        add(panel);

        setSize(largeur, hauteur);
        setTitle("Exemple de fenêtre avec boutons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
