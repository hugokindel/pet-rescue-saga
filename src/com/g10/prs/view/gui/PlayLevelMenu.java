package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.level.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayLevelMenu extends GuiMenu {
    boolean destroyingBlock = false;
    boolean usingRocket = false;
    boolean usingSaber = false;

    public PlayLevelMenu() {
        super(PetRescueSaga.level.getName());
    }

    @Override
    protected void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();
        int animalsLeft = PetRescueSaga.level.getAnimalsLeft();

        JPanel topPanel = new JPanel();
        topPanel.add(new Label("<html>Vous avez joué " + numberOfPlay + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                " et votre score est de " + PetRescueSaga.level.getScore() + ".<br> Il reste " +
                animalsLeft + " " + (animalsLeft > 1 ? "animaux" : "animal") + " à sauver...").asJLabel());
        panel.add(topPanel);

        Level level = PetRescueSaga.level;
        JButton[][] buttons = new JButton[level.getRows()][level.getColumns()];

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

                    Border border = button.getBorder();
                    Cursor cursor = getWindow().getCursor();

                    int finalI = i;
                    int finalJ = j;
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (destroyingBlock) {
                                PetRescueSaga.level.removeGameMode(finalJ, finalI, true, true);
                                getWindow().setCursor(cursor);
                                checkWin();
                            } else if (usingRocket) {
                                PetRescueSaga.level.removeColumn(finalJ);
                                getWindow().setCursor(cursor);
                                checkWin();
                            } else if (usingSaber) {
                                PetRescueSaga.level.removeRow(finalI);
                                getWindow().setCursor(cursor);
                                checkWin();
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (destroyingBlock) {
                                button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                                getWindow().setCursor(new Cursor(Cursor.HAND_CURSOR));
                            } else if (usingRocket) {
                                for (int i = 0; i < level.getRows(); i++) {
                                    if (level.getBoard()[i][finalJ] instanceof Block) {
                                        buttons[i][finalJ].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                                    }
                                }
                                getWindow().setCursor(new Cursor(Cursor.HAND_CURSOR));
                            } else if (usingSaber) {
                                for (int i = 0; i < level.getColumns(); i++) {
                                    if (level.getBoard()[finalI][i] instanceof Block) {
                                        buttons[finalI][i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                                    }
                                }
                                getWindow().setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            button.setBorder(border);
                            if (usingRocket) {
                                for (int i = 0; i < level.getRows(); i++) {
                                    if (!(level.getBoard()[i][finalJ] instanceof Obstacle)) {
                                        buttons[i][finalJ].setBorder(border);
                                    }
                                }
                            } else if (usingSaber) {
                                for (int i = 0; i < level.getColumns(); i++) {
                                    if (!(level.getBoard()[finalI][i] instanceof Obstacle)) {
                                        buttons[finalI][i].setBorder(border);
                                    }
                                }
                            }
                            getWindow().setCursor(cursor);
                        }
                    });
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

                buttons[i][j] = button;
                levelPanel.add(buttons[i][j]);
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

        if (destroyingBlock) {
            destroyBlock.setEnabled(false);
        } else if (usingRocket) {
            useRocket.setEnabled(false);
        } else if (usingSaber) {
            useSaber.setEnabled(false);
        }

        destroyBlock.addActionListener(e -> {
            destroyingBlock = true;
            usingRocket = false;
            usingSaber = false;
            destroyBlock.setEnabled(false);
            useRocket.setEnabled(true);
            useSaber.setEnabled(true);
        });

        useRocket.addActionListener(e -> {
            destroyingBlock = false;
            usingRocket = true;
            usingSaber = false;
            destroyBlock.setEnabled(true);
            useRocket.setEnabled(false);
            useSaber.setEnabled(true);
        });

        useSaber.addActionListener(e -> {
            destroyingBlock = false;
            usingRocket = false;
            usingSaber = true;
            destroyBlock.setEnabled(true);
            useRocket.setEnabled(true);
            useSaber.setEnabled(false);
        });

        botPlay.addActionListener(e -> {
            destroyingBlock = false;
            usingRocket = false;
            usingSaber = false;

            destroyBlock.setEnabled(true);
            useRocket.setEnabled(true);
            useSaber.setEnabled(true);

            PetRescueSaga.bot.play(PetRescueSaga.level);

            checkWin();
        });
        contentPanel.add(botPlay);
        panel.add(contentPanel);
    }

    private void checkWin() {
        if (PetRescueSaga.level.hasWon()) {
            PetRescueSaga.view.changeMenu(new EndLevelMenu("gagné"), false);
        } else {
            ((GuiView)PetRescueSaga.view).reload();
        }
    }
}
