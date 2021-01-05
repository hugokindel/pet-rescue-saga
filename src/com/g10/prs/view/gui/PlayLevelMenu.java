package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Resources;
import com.g10.prs.level.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/** Menu of a level */
public class PlayLevelMenu extends GuiMenu {
    /** true if the player is going to destroy a block */
    boolean destroyingBlock = false;
    /** true if the player is using a rocket power */
    boolean usingRocket = false;
    /** true if the player is using a saber power */
    boolean usingSaber = false;

    /** class cosntructor */
    public PlayLevelMenu() {
        super(PetRescueSaga.level.getName(), null, true, PetRescueSaga.level.getBackgroundImagePath(), "ingame.png");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();
        int animalsLeft = PetRescueSaga.level.getAnimalsLeft();

        if (getView().getStyle() == GuiView.Style.Stylized) {
            Panel levelNamePanel = new Panel();
            levelNamePanel.add(new Label(PetRescueSaga.level.getName(), 26));
            panel.add(levelNamePanel);
        }

        Panel topPanel = new Panel();
        topPanel.add(new Label("Vous avez joué " + numberOfPlay + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                " et votre score est de " + PetRescueSaga.level.getScore() + ".", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12));
        topPanel.setBorder(0, 0, -5, 0);
        panel.add(topPanel);

        Panel topPanel2 = new Panel();
        topPanel2.add(new Label("Il reste " +animalsLeft + " " + (animalsLeft > 1 ? "animaux" : "animal") +
                " à sauver...", getView().getStyle() == GuiView.Style.Stylized ? 21 : 12));
        topPanel2.setBorder(0, 0, 10, 0);
        panel.add(topPanel2);

        Level level = PetRescueSaga.level;
        JButton[][] buttons = new JButton[level.getRows()][level.getColumns()];

        for (int i = 0; i < level.getRows(); i++) {
            Panel levelPanel = new Panel();

            for (int j = 0; j < level.getColumns(); j++) {
                Cell cell = level.getBoard()[i][j];

                JButton button = new JButton();
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                button.setFont(Arrays.stream(gEnv.getAllFonts()).filter(k -> k.getName().equals("Curse Casual Regular")).findFirst().get().deriveFont(13f));
                button.setPreferredSize(new Dimension(40, 40));

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
                            if (destroyingBlock && PetRescueSaga.level.countNumberOfBlocksSimilar(finalI, finalJ) >= 2) {
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
                            if (destroyingBlock && PetRescueSaga.level.countNumberOfBlocksSimilar(finalI, finalJ) >= 2) {
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

        Panel contentPanel = new Panel();
        contentPanel.setBorder(5, 0, 15, 0);
        Button destroyBlock = new Button("Détruire un bloc coloré");
        contentPanel.add(destroyBlock.get());
        Button useRocket = new Button("Utiliser une fusée");
        contentPanel.add(useRocket.get());
        Button useSaber = new Button("Utiliser un sabre");
        contentPanel.add(useSaber.get());
        Button botPlay = new Button("Laisser le robot jouer un tour");
        contentPanel.add(botPlay.get());

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

        panel.add(contentPanel);
    }

    /** check if the level is finished */
    private void checkWin() {
        if (PetRescueSaga.level.hasWon()) {
            PetRescueSaga.view.changeMenu(new EndLevelMenu("gagné"), false);
        } else {
            ((GuiView)PetRescueSaga.view).reload();
        }
    }
}
