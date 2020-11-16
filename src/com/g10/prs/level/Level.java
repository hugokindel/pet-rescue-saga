package com.g10.prs.level;

import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;
import com.g10.prs.core.Resources;
import com.g10.prs.core.njson.NJSonCannotParseException;
import com.g10.prs.core.njson.NJson;
import com.g10.prs.core.njson.NJsonSerializable;
import com.g10.prs.core.PrsException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/** It represents the structure of a level (which is basically a board of cells to represents the state of the game). */
@NJsonSerializable
public class Level {
    /** The name of the level. */
    @NJsonSerializable
    String name;
    /** A list of authors. */
    @NJsonSerializable
    List<String> authors;
    /** The version of the level (useful to keep track of versioning if it were to exist). */
    @NJsonSerializable
    String version;
    /** The number of columns of the level. It defines the x length of all the grid based attributes. */
    @NJsonSerializable
    int columns;
    /** The number of rows of the level. */
    @NJsonSerializable
    int rows;
    /**
     * The background grid defining the borders of the board, it can have different values:
     * 0 - Border (which is comparable to an obstacle cell).
     * 1 - Content (which is any type of cell, including an obstacle).
     */
    @NJsonSerializable
    List<List<Integer>> backgroundGrid;

    /**
     * The initial blocks contained within the board, it can have different values:
     * 0 - Empty cell (an empty space, which is not obstacle, which means it can be filled when cells are moving).
     * 1 - Animal (it will randomly choose any animal).
     * 2 - Specific color block. |
     * 3 - Specific color block. |
     * 4 - Specific color block. | => The game has 5 colors, every color will be choosen randomly on each game.
     * 5 - Specific color block. |
     * 6 - Specific color block. |
     * */
    @NJsonSerializable
    List<List<Integer>> initialBlocks;

    /**
     * The board is built with the backgroundGrid and the initialBlocks, it represents the current state of play:
     * Each cell represents the current start at position [row][column], it can have different types of cells:
     * Cell - An empty space that can be filled.
     * Block - Any color block, a movable and removable cell.
     * Animal - Any animal, a movable and removable (but not by the user) cell.
     * Obstacle - Any osbtacle, nor movable nor removable cell.
     */
    Cell[][] board;

    /**
     * The background represents what we can see when showing the level,
     * it can have different values (same as backgroundGrid):
     * 0 - Border (which is comparable to an obstacle cell).
     * 1 - Content (which is any type of cell, including an obstacle).
     */
    Visibility[][] background;

    /** The number of animals left before winning. */
    int animalsLeft;

    /**
     * Loads the level at path (the path must be inside the levels folder in the game's data).
     *
     * @param filePath The path to use.
     * @return the loaded level.
     */
    public static Level load(String filePath) throws PrsException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, NJSonCannotParseException, InvocationTargetException, IOException {
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

    /**
     * Print the level.
     * TODO: Move to CLI view
     */
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

    /**
     * Removes an element from the board at [row][column].
     *
     * @param c The column.
     * @param r The row.
     * @param onlyBlocks Indicates if we needs to check if the position is a color block before removing it.
     * @param recalculate Indicates if we need to recalculate (apply gravity, ...).
     */
    public void remove(int c, int r, boolean onlyBlocks, boolean recalculate) {
        if (!onlyBlocks || board[r][c] instanceof Block) {
            board[r][c] = null;

            if (recalculate) {
                recalculate();
            }
        }
    }

    /**
     * Removes a whole row from the board.
     *
     * @param r The row.
     */
    public void removeRow(int r) {
        for (int c = 0; c < columns; c++) {
            remove(c, r, true, false);
        }

        recalculate();
    }

    /**
     * Removes a whole column from the board.
     *
     * @param c The column.
     */
    public void removeColumn(int c) {
        for (int r = 0; r < rows; r++) {
            remove(c, r, true, false);
        }

        recalculate();
    }

    /**
     * Removes a block with the game rules which means it can also remove what is at
     * its left, right, top or bottom recursively.
     *
     * @param c The column.
     * @param r The row.
     * @param recalculate Indicates if we need to recalculate (apply gravity, ...).
     */
    public void removeGameMode(int c, int r, boolean recalculate) {
        Block block = (Block)board[r][c];
        remove(c, r, true, false);

        if (r - 1 >= 0 && board[r - 1][c] instanceof Block &&
                ((Block)board[r - 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r - 1, false);
        }

        if (r + 1 < rows && board[r + 1][c] instanceof Block &&
                ((Block)board[r + 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r + 1, false);
        }

        if (c - 1 >= 0 && board[r][c - 1] instanceof Block &&
                ((Block)board[r][c - 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c - 1, r, false);
        }

        if (c + 1 < columns && board[r][c + 1] instanceof Block &&
                ((Block)board[r][c + 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c + 1, r, false);
        }

        if (recalculate) {
            recalculate();
        }
    }

    /** @return if the level is won. */
    public boolean hasWin() {
        return animalsLeft == 0;
    }

    /**
     * Recalculate the states of each cell:
     *
     * First, we apply apply gravity, each time gravity is applied on a block, it must be reapplied.
     * Then, we check for animals on the bottom and removes them if found.
     * Finally, we apply the left shift mechanics if possible, if we did, we need to start everything over again.
     */
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

    /**
     * Apply gravity on the level.
     *
     * We go through each cells of the board, and look for two possibilities, if true, we apply gravity:
     * If the current cell has an empty space behind it, it falls:
     *
     * Example:
     * █ => E
     * E    █
     *
     * Else if it has an empty space on its bottom left while standing on an unmovable space, it falls on the left:
     *
     * Example:
     * E█ => EE
     * EU    █U
     *
     * E - Empty space.
     * █ - Movable cell.
     * U - Unmovable cell (either obstacle or border).
     *
     * @return if gravity was applied (a state of the board was changed).
     */
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

    /** Removes any animal on the bottom of the board. */
    private void removeAnimalsAtBottom() {
        for (int c = 0; c < columns; c++) {
            if (board[rows - 1][c] instanceof Animal) {
                board[rows - 1][c] = null;
                animalsLeft--;
            }
        }
    }

    /**
     * Apply lefty shifting on the leveL;
     *
     * We go through each cells of the board, and look for one possibility, if true we apply left shifting:
     * If the current cell is sitting on an unmovable cell (border or obstacle) and the column at its left is empty:
     *
     * Example:
     * E█    █E
     * E█ => █E
     * UU    UU
     *
     * Example:
     * U█    U█
     * E█ => █E  (in this case, gravity will do its think when reapplied just after this shifting).
     * UU    UU
     *
     * E - Empty space.
     * █ - Movable cell.
     * U - Unmovable cell (either obstacle or border).
     *
     * @return if a shifting was applied (at least one state of the board was changed).
     */
    private boolean applyShift() {
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = columns - 1; c >= 1; c--) {
                if (board[r][c] != null && isMovable(c, r) && (r + 1 == rows || !isMovable(c, r + 1)) &&
                        board[r][c - 1] == null) {
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

    /**
     * Check if the position is a movable cell or not, meaning, if it is neither a border nor an obstacle.
     *
     * @param c The column.
     * @param r The row.
     * @return if the cell at position [row][column] is movable.
     */
    private boolean isMovable(int c, int r) {
        return !(background[r][c] == Visibility.Border || board[r][c] instanceof Obstacle);
    }
}
