package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.level.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayLevelMenu extends GuiMenu {
    public PlayLevelMenu() {
        super(PetRescueSaga.level.getName());
    }

    @Override
    protected void drawContent() {
        Level level = PetRescueSaga.level;

        for (int i = 0; i < level.getRows(); i++) {
            JPanel levelPanel = new JPanel();

            for (int j = 0; j < level.getColumns(); j++) {
                Cell cell = level.getBoard()[i][j];

                JButton button = new JButton();
                button.setPreferredSize(new Dimension(45, 45));

                if (cell instanceof Block) {
                    BlockType type = ((Block)cell).getBlockType();

                    if (type == BlockType.Blue) {
                        button.setBackground(Color.BLUE);
                    } else if (type == BlockType.Green) {
                        button.setBackground(Color.GREEN);
                    } else if (type == BlockType.Red) {
                        button.setBackground(Color.RED);
                    } else if (type == BlockType.Purple) {
                        button.setBackground(Color.PINK);
                    } else if (type == BlockType.Yellow) {
                        button.setBackground(Color.YELLOW);
                    }
                } else if (cell instanceof Animal) {
                    AnimalType type = ((Animal)cell).getAnimalType();

                    button.setBackground(Color.WHITE);

                    if (type == AnimalType.Hedgehod) {
                        button.setText("H");
                    } else if (type == AnimalType.Parrot) {
                        button.setText("A");
                    } else if (type == AnimalType.Piglet) {
                        button.setText("I");
                    } else if (type == AnimalType.Puppy) {
                        button.setText("U");
                    } else if (type == AnimalType.Turtle) {
                        button.setText("T");
                    } else if (type == AnimalType.Kitten) {
                        button.setText("K");
                    }
                } else if (cell instanceof Obstacle) {
                    button.setBackground(Color.black);
                }

                button.setEnabled(false);
                levelPanel.add(button);
            }

            panel.add(levelPanel);
        }

        JPanel contentPanel = new JPanel();
        Border border = contentPanel.getBorder();
        Border margin = new EmptyBorder(50, 0, 0, 0);
        contentPanel.setBorder(new CompoundBorder(border, margin));
        JButton destroyBlock = new JButton("Détruire un bloc coloré");
        contentPanel.add(destroyBlock);
        JButton useRocket = new JButton("Utiliser une fusée");
        contentPanel.add(useRocket);
        JButton useSaber = new JButton("Utiliser un sabre");
        contentPanel.add(useSaber);
        JButton botPlay = new JButton("Laisser le robot jouer un tour");
        botPlay.addActionListener(e -> {
            PetRescueSaga.bot.play(PetRescueSaga.level);

            if (PetRescueSaga.level.hasWon()) {
                PetRescueSaga.view.changeMenu(new EndLevelMenu("gagné"), false);
            } else {
                ((GuiView)PetRescueSaga.view).reload();
            }
        });
        contentPanel.add(botPlay);
        panel.add(contentPanel);
    }
}
