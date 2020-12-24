package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;
import com.g10.prs.view.gui.GuiMenu;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends GuiMenu {
    public MainMenu() {
        super("Menu principal", false);
    }

    @Override
    public void drawContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Pet Rescue Saga");
        titleLabel.setFont(new Font(new JLabel().getFont().getName(), Font.BOLD, 40));
        topPanel.add(titleLabel);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(10, 0, 10, 0);
        constr.anchor = GridBagConstraints.CENTER;

        JButton playButton = new JButton("Jouer");
        JButton changeNameButton = new JButton("Changer de nom");
        JButton seeRulesButton = new JButton("Voir les règles");
        JButton seeCreditButton = new JButton("Voir les crédits");
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> {
            getWindow().setVisible(false);
            getWindow().dispose();
        });

        constr.gridy = 1;
        contentPanel.add(playButton, constr);
        constr.gridy = 2;
        contentPanel.add(changeNameButton, constr);
        constr.gridy = 3;
        contentPanel.add(seeRulesButton, constr);
        constr.gridy = 4;
        contentPanel.add(seeCreditButton, constr);
        constr.gridy = 5;
        contentPanel.add(quitButton, constr);

        panel.add(topPanel);
        panel.add(contentPanel);

        getWindow().add(panel);
    }
}
