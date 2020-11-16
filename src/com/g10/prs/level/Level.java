package com.g10.prs.level;

import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;
import com.g10.prs.core.resource.Resources;
import com.g10.prs.core.resource.njson.NJSonCannotParseException;
import com.g10.prs.core.resource.njson.NJson;
import com.g10.prs.core.resource.njson.NJsonSerializable;
import com.g10.prs.core.type.PrsException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@NJsonSerializable
public class Level {
    @NJsonSerializable
    String name;
    @NJsonSerializable
    List<String> authors;
    @NJsonSerializable
    String version;
    @NJsonSerializable
    int columns;
    @NJsonSerializable
    int rows;
    @NJsonSerializable
    List<List<Integer>> backgroundGrid;
    @NJsonSerializable
    List<List<Integer>> initialBlocks;

    Cell[][] board;
    Visibility[][] background;
    int animalsLeft;

    public static Level load(String filePath) throws PrsException, IllegalAccessException, InstantiationException, NoSuchMethodException, NJSonCannotParseException, InvocationTargetException, IOException {
        Level level = NJson.deserialize(Resources.getLevelsDirectory() + "/" + filePath, Level.class);

        Random rand = new Random();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            while (true) {
                int color = rand.nextInt(5);
                if (!colors.contains(color)) {
                    colors.add(color);
                    break;
                }
            }
        }

        level.board = new Cell[level.rows][level.columns];
        level.background = new Visibility[level.rows][level.columns];

        for (int r = 0; r < level.rows; r++) {
            for (int c = 0; c < level.columns; c++) {
                int block = level.initialBlocks.get(r).get(c);

                if (block == 0) {
                    level.board[r][c] = new Cell(CellType.Empty);
                } else if (block == 1) {
                    level.board[r][c] = new Animal(AnimalType.values()[rand.nextInt(6)]);
                    level.animalsLeft++;
                } else if (block <= 6) {
                    level.board[r][c] = new Block(BlockType.values()[colors.get(block - 2)]);
                } else {
                    level.board[r][c] = new Obstacle(ObstacleType.Wood);
                }

                level.background[r][c] = Visibility.values()[level.backgroundGrid.get(r).get(c)];
            }
        }

        return level;
    }

    public void print() {
        Out.print("   ");
        for (int c = 0; c < columns; c++) {
            Out.print((char)(65 + c));
        }
        Out.println();

        for (int r = 0; r < rows + 2; r++) {
            if (r != 0 && r != rows + 1) {
                Out.print(r - 1 + " ");
            } else {
                Out.print("  ");
            }

            for (int c = 0; c < columns + 2; c++) {
                int br = r - 1;
                int bc = c - 1;

                if (r == 0) {
                    Out.print("*");
                } else if (r == rows + 1) {
                    Out.print("*");
                } else if (c == 0) {
                    Out.print("*");
                } else if (c == columns + 1) {
                    Out.print("*");
                } else if (background[br][bc] == Visibility.Border) {
                    Out.print("*");
                } else {
                    Cell cell = board[br][bc];

                    if (cell instanceof Block) {
                        switch (((Block)cell).getBlockType()) {
                            case Red:
                                Out.print(TextColor.Red + "█");
                                break;
                            case Green:
                                Out.print(TextColor.Green + "█");
                                break;
                            case Blue:
                                Out.print(TextColor.Blue + "█");
                                break;
                            case Purple:
                                Out.print(TextColor.Purple + "█");
                                break;
                            case Yellow:
                                Out.print(TextColor.Yellow + "█");
                                break;
                        }
                    } else if (cell instanceof Animal) {
                        switch (((Animal)cell).getAnimalType()) {
                            case Hedgehod:
                                Out.print("H");
                                break;
                            case Parrot:
                                Out.print("A");
                                break;
                            case Piglet:
                                Out.print("I");
                                break;
                            case Puppy:
                                Out.print("U");
                                break;
                            case Turtle:
                                Out.print("T");
                                break;
                            case Kitten:
                                Out.print("K");
                                break;
                        }
                    } else if (cell instanceof Obstacle) {
                        Out.print("W");
                    } else {
                        Out.print(" ");
                    }
                }
            }

            Out.println();
        }
    }

    public void remove(int c, int r, boolean onlyBlocks, boolean recalculate) {
        if (!onlyBlocks || board[r][c] instanceof Block) {
            board[r][c] = null;

            if (recalculate) {
                recalculate();
            }
        }
    }

    public void removeRow(int r) {
        for (int c = 0; c < columns; c++) {
            remove(c, r, true, false);
        }

        recalculate();
    }

    public void removeColumn(int c) {
        for (int r = 0; r < columns; r++) {
            remove(c, r, true, false);
        }

        recalculate();
    }

    public void removeGameMode(int c, int r, boolean recalculate) {
        Block block = (Block)board[r][c];
        remove(c, r, true, false);

        if (r - 1 >= 0 && board[r - 1][c] instanceof Block && ((Block)board[r - 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r - 1, false);
        }

        if (r + 1 < rows && board[r + 1][c] instanceof Block && ((Block)board[r + 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r + 1, false);
        }

        if (c - 1 >= 0 && board[r][c - 1] instanceof Block && ((Block)board[r][c - 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c - 1, r, false);
        }

        if (c + 1 < columns && board[r][c + 1] instanceof Block && ((Block)board[r][c + 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c + 1, r, false);
        }

        if (recalculate) {
            recalculate();
        }
    }

    public boolean hasWin() {
        return animalsLeft == 0;
    }

    private void recalculate() {
        boolean needsToCheck = true;

        while (needsToCheck) {
            boolean gravityNeedsToBeApplied = true;
            while (gravityNeedsToBeApplied) {
                gravityNeedsToBeApplied = applyGravity();
            }

            removeAnimalsAtBottom();

            needsToCheck = applyShift();
        }
    }

    private boolean applyGravity() {
        for (int r = 0; r < rows - 1; r++) {
            for (int c = 0; c < columns; c++) {
                if (board[r][c] != null && isMovable(c, r) && r + 1 < rows) {
                    if (board[r + 1][c] == null) {
                        board[r + 1][c] = board[r][c];
                        board[r][c] = null;
                        return true;
                    } else if (!isMovable(c, r + 1) && c - 1 >= 0 && board[r + 1][c - 1] == null) {
                        board[r + 1][c - 1] = board[r][c];
                        board[r][c] = null;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void removeAnimalsAtBottom() {
        for (int c = 0; c < columns; c++) {
            if (board[rows - 1][c] instanceof Animal) {
                board[rows - 1][c] = null;
                animalsLeft--;
            }
        }
    }

    private boolean applyShift() {
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = columns - 1; c >= 1; c--) {
                if (board[r][c] != null && isMovable(c, r) && (r + 1 == rows || !isMovable(c, r + 1)) && board[r][c - 1] == null) {
                    int nextObstacleRow = 0;

                    for (int r2 = r; r2 >= 0; r2--) {
                        if (board[r2][c] == null || !isMovable(r2, c)) {
                            nextObstacleRow = r2 + 1;
                            break;
                        }
                    }

                    for (int r2 = r; r2 >= nextObstacleRow; r2--) {
                        if (!isMovable(c - 1, r2)) {
                            break;
                        }

                        board[r2][c - 1] = board[r2][c];
                        board[r2][c] = null;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isMovable(int c, int r) {
        return !(background[r][c] == Visibility.Border || board[r][c] instanceof Obstacle);
    }
}
