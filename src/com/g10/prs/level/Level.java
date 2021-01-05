package com.g10.prs.level;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Resources;
import com.g10.prs.common.print.BackgroundColor;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;
import com.g10.prs.njson.NJSonCannotParseException;
import com.g10.prs.njson.NJson;
import com.g10.prs.njson.NJsonSerializable;
import com.g10.prs.common.PrsException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
     * 0: Border (which is comparable to an obstacle cell).
     * 1: Content (which is any type of cell, including an obstacle).
     */
    @NJsonSerializable
    List<List<Integer>> backgroundGrid;

    /**
     * The initial blocks contained within the board, it can have different values:
     * 0: Empty cell (an empty space, which is not obstacle, which means it can be filled when cells are moving).
     * 1: Animal (it will randomly choose any animal) cell.
     * 2: Specific color block. |
     * 3: Specific color block. |
     * 4: Specific color block. | => The game has 5 colors, every color will be choosen randomly on each game.
     * 5: Specific color block. |
     * 6: Specific color block. |
     * 7: Obstacle cell.
     * Other values: A group ID.
     * */
    @NJsonSerializable
    List<List<Integer>> initialBlocks;

    @NJsonSerializable(necessary = false)
    List<Group> groups;

    @NJsonSerializable(necessary = false)
    String backgroundImagePath;

    /**
     * The board is built with the backgroundGrid and the initialBlocks, it represents the current state of play:
     * Each cell represents the current start at position [row][column], it can have different types of cells:
     * Cell : An empty space that can be filled.
     * Block : Any color block, a movable and removable cell.
     * Animal : Any animal, a movable and removable (but not by the user) cell.
     * Obstacle: Any osbtacle, nor movable nor removable cell.
     */
    Cell[][] board;

    /**
     * The background represents what we can see when showing the level,
     * it can have different values (same as backgroundGrid):
     * 0: Border (which is comparable to an obstacle cell).
     * 1: Content (which is any type of cell, including an obstacle).
     */
    Visibility[][] background;

    /** the colors of the block with random order */
    List<Integer> colors;

    /** the number of move the player does */
    int numberOfPlay = 0;

    /** the number of block the player removed */
    int numberOfBlockRemoved = 0;

    /** the score of the player */
    int score = 0;

    /** The number of animals left before winning. */
    int animalsLeft;

    Level save;

    /**
     * getter of board
     *
     * @return the board
     */
    public Cell[][] getBoard(){
        return board;
    }

    /**
     * getter of animalsLeft
     *
     * @return the number of animalsleft
     */
    public int getAnimalsLeft(){
        return animalsLeft;
    }

    public Level getSave() {
        save.save = save.copy();
        return save;
    }

    /**
     * copy the level
     *
     * @return a copy of the level
     */
    public Level copy() {
        Level level = new Level();

        level.name =  name;
        level.authors = new ArrayList<>(authors);
        level.version = version;
        level.columns = columns;
        level.rows = rows;
        level.backgroundGrid = new ArrayList<>();
        for (List<Integer> value : backgroundGrid) {
            level.backgroundGrid.add(new ArrayList<>(value));
        }
        level.initialBlocks = new ArrayList<>();
        for (List<Integer> value : initialBlocks) {
            level.initialBlocks.add(new ArrayList<>(value));
        }
        if (groups != null) {
            level.groups = new ArrayList<>(groups);
            for (Group value : groups) {
                level.groups.add(value.copy());
            }
        } else {
            level.groups = null;
        }
        level.board = copyBoard(this);
        level.background = new Visibility[background.length][background[0].length];
        for (int i = 0; i < background.length; i++) {
            System.arraycopy(background[i], 0, level.background[i], 0, background[i].length);
        }
        level.colors = new ArrayList<>(colors);
        level.numberOfPlay = numberOfPlay;
        level.numberOfBlockRemoved = numberOfBlockRemoved;
        level.score = score;
        level.animalsLeft = animalsLeft;

        return level;
    }

    private Cell[][] copyBoard(Level level) {
        Cell[][] board = new Cell[level.board.length][level.board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (level.board[i][j] instanceof Animal) {
                    board[i][j] = ((Animal)level.board[i][j]).copy();
                } else if (level.board[i][j] instanceof Block) {
                    board[i][j] = ((Block)level.board[i][j]).copy();
                } else if (level.board[i][j] instanceof Obstacle) {
                    board[i][j] = ((Obstacle)level.board[i][j]).copy();
                } else if (level.board[i][j] != null) {
                    board[i][j] = level.board[i][j].copy();
                } else {
                    board[i][j] = null;
                }
            }
        }

        return board;
    }

    /**
     * Loads the level at path (the path must be inside the levels folder in the game's data).
     *
     * @param filePath The path to use.
     * @return the loaded level.
     */
    public static Level load(String filePath) throws PrsException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException, IOException, ClassNotFoundException {
        Level level = NJson.deserialize(filePath, Level.class);

        level.colors = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            while (true) {
                int color = PetRescueSaga.randomizer.nextInt(5);
                if (!level.colors.contains(color)) {
                    level.colors.add(color);
                    break;
                }
            }
        }

        level.board = new Cell[level.rows][level.columns];
        level.background = new Visibility[level.rows][level.columns];

        for (int r = 0; r < level.rows; r++) {
            for (int c = 0; c < level.columns; c++) {
                int block = level.initialBlocks.get(r).get(c);
                level.board[r][c] = createBlock(level, block, level.colors);
                level.background[r][c] = Visibility.values()[level.backgroundGrid.get(r).get(c)];
            }
        }

        if (level.backgroundImagePath == null) {
            level.backgroundImagePath = "background.png";
        }

        level.save = level.copy();

        return level;
    }

    /**
     * create a block based on the param block
     * 0: Empty cell (an empty space, which is not obstacle, which means it can be filled when cells are moving).
     * 1: Animal (it will randomly choose any animal) cell.
     * 2: Specific color block. |
     * 3: Specific color block. |
     * 4: Specific color block. |
     * 5: Specific color block. |
     * 6: Specific color block. |
     * 7: Obstacle cell.
     *
     * @param level the level
     * @param block type of the block
     * @param colors the list of the colors for the block
     * @return a cell
     */
    private static Cell createBlock(Level level, int block, List<Integer> colors) {
        if (block == 0) {
            return new Cell(CellType.Empty);
        } else if (block == 1) {
            Animal animal = new Animal(AnimalType.values()[PetRescueSaga.randomizer.nextInt(6)]);
            level.animalsLeft++;
            return animal;
        } else if (block <= 6) {
            return new Block(BlockType.values()[colors.get(block - 2)]);
        } else if (block <= 7) {
            return new Obstacle(ObstacleType.Wood);
        } else {
            boolean found = false;

            int i = 0;
            for (; i < level.groups.size(); i++) {
                if (level.groups.get(i).getId() == block) {
                    found = true;
                    break;
                }
            }

            if (found) {
                List<Integer> blocks = level.groups.get(i).getBlocks();

                return createBlock(level, blocks.get(PetRescueSaga.randomizer.nextInt(blocks.size())), colors);
            }
        }

        return null;
    }

    /**
     * Saves the level to a NJson file.
     *
     * @param filepath The file path to use.
     */
    public void save(String filepath) throws IllegalAccessException, FileNotFoundException, PrsException {
        List<List<Integer>> savedBoard = new ArrayList<List<Integer>>(initialBlocks);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Cell cell = board[r][c];

                if (cell instanceof Animal) {
                    initialBlocks.get(r).set(c, 1);
                } else if (cell instanceof Block) {
                    initialBlocks.get(r).set(c, ((Block)cell).getBlockType().ordinal() + 2);
                } else if (cell instanceof Obstacle) {
                    initialBlocks.get(r).set(c, ((Obstacle)cell).getObstacleType().ordinal() +
                            BlockType.values().length + 2);
                } else {
                    initialBlocks.get(r).set(c, 0);
                }
            }
        }

        NJson.serialize(filepath, this);
        initialBlocks = savedBoard;
    }

    /** Print the level. */
    public void print() {
        Out.print("  ");
        for (int c = 0; c < columns; c++) {
            Out.print(c);
        }
        Out.println();

        for (int r = 0; r < rows + 2; r++) {
            if (r != 0 && r != rows + 1) {
                Out.print(r - 1);
            } else {
                Out.print(" ");
            }

            for (int c = 0; c < columns + 2; c++) {
                int br = r - 1;
                int bc = c - 1;

                if (r == 0) {
                    Out.print(BackgroundColor.White + "▓");
                } else if (r == rows + 1) {
                    Out.print(BackgroundColor.White + "▓");
                } else if (c == 0) {
                    Out.print(BackgroundColor.White + "▓");
                } else if (c == columns + 1) {
                    Out.print(BackgroundColor.White + "▓");
                } else if (background[br][bc] == Visibility.Border) {
                    Out.print(BackgroundColor.White + "▓");
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
                        Out.print(BackgroundColor.White + "▓");
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
            score += 100;
            numberOfBlockRemoved++;

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
        played();
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
        played();
    }

    /**
     * Removes a block with the game rules which means it can also remove what is at
     * its left, right, top or bottom recursively.
     *
     * @param c The column.
     * @param r The row.
     * @param recalculate Indicates if we need to recalculate (apply gravity, ...).
     */
    public void removeGameMode(int c, int r, boolean recalculate, boolean played) {
        if (board[r][c] == null) {
            return;
        }

        Block block = (Block)board[r][c];

        remove(c, r, true, false);

        if (r - 1 >= 0 && board[r - 1][c] instanceof Block &&
                ((Block)board[r - 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r - 1, false, false);
        }

        if (r + 1 < rows && board[r + 1][c] instanceof Block &&
                ((Block)board[r + 1][c]).getBlockType() == block.getBlockType()) {
            removeGameMode(c, r + 1, false, false);
        }

        if (c - 1 >= 0 && board[r][c - 1] instanceof Block &&
                ((Block)board[r][c - 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c - 1, r, false, false);
        }

        if (c + 1 < columns && board[r][c + 1] instanceof Block &&
                ((Block)board[r][c + 1]).getBlockType() == block.getBlockType()) {
            removeGameMode(c + 1, r, false, false);
        }

        if (recalculate) {
            recalculate();
        }

        if (played) {
            played();
        }
    }

    /** @return if the level is won. */
    public boolean hasWon() {
        return animalsLeft == 0;
    }

    /**
     * Recalculate the states of each cell:
     *
     * First, we try to apply gravity and each time gravity is applied on a block, it must be reapplied.
     * Then, we check for animals on the bottom and removes them if found.
     * Finally, we apply the left shift mechanism if possible, if we did, we need to start everything over again.
     */
    private void recalculate() {
        boolean needsToCheck = true;

        int i = 0;

        while (needsToCheck) {
            i++;

            boolean gravityNeedsToBeApplied = true;
            while (gravityNeedsToBeApplied) {
                gravityNeedsToBeApplied = applyGravity();
                gravityNeedsToBeApplied = removeAnimalsAtBottom() || gravityNeedsToBeApplied;
                applyRefill();
            }

            needsToCheck = applyShift();

            if (i >= 500) {
                Out.printlnError("Infinite loop !");
                print();
                return;
            }
        }
    }

    /**
     * Applies gravity on the level.
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
                if (board[r][c] != null && isMovable(c, r)) {
                    if (board[r + 1][c] == null || board[r + 1][c].getType() == CellType.Empty) {
                        board[r + 1][c] = board[r][c];
                        board[r][c] = null;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /** Removes any animal on the bottom of the board. */
    private boolean removeAnimalsAtBottom() {
        boolean removed = false;

        for (int c = 0; c < columns; c++) {
            if (board[rows - 1][c] instanceof Animal) {
                board[rows - 1][c] = null;
                score += 1000;
                animalsLeft--;
                if (animalsLeft == 0) {
                    score += 5000;
                }
                removed = true;
            }
        }

        return removed;
    }

    /**
     * Applies lefty shifting on the leveL;
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
        for (int r = 0; r < rows; r++) {
            for (int c = 1; c < columns; c++) {
                if (board[r][c] != null && isMovable(c, r) && (r + 1 == rows || !isMovable(c, r + 1)) && (board[r][c - 1] == null || board[r][c - 1].getType() == CellType.Empty)) {
                    int nextObstacleRow = 0;

                    for (int r2 = r; r2 >= 0; r2--) {
                        if (board[r2][c] == null || !isMovable(c, r2)) {
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
     * apply a refill on the level if possible
     *
     * @return true if the refill has been made
     */
    private boolean applyRefill() {
        boolean filled = false;

        if (groups != null) {
            for (int c = 0; c < columns; c++) {
                if (!isMovable(c, 0)) {
                    break;
                }

                if (board[0][c] == null || board[0][c].getType() == CellType.Empty) {
                    for (Group group : groups) {
                        if (group.canRefill()) {
                            boolean canRefill = false;

                            if (group.getRefillCondition() == null || group.getRefillCondition().isTrue("amongColumns", String.valueOf(c))) {
                                canRefill = true;
                            }

                            if (canRefill) {
                                for (int r = 0; r < rows; r++) {
                                    if ((!isMovable(c, r))) {
                                        break;
                                    }

                                    board[r][c] = createBlock(this, group.getId(), colors);
                                }
                                filled = true;
                            }
                        }
                    }
                }
            }
        }

        return filled;
    }

    /**
     * Check if the position is a movable cell or not, meaning, if it is neither a border nor an obstacle.
     *
     * @param c The column.
     * @param r The row.
     * @return if the cell at position [row][column] is movable.
     */
    public boolean isMovable(int c, int r) {
        return !(background[r][c] == Visibility.Border || board[r][c] instanceof Obstacle);
    }

    public int countNumberOfBlocksSimilar(int r, int c) {
        return countNumberOfBlocksSimilar(r, c, copyBoard(this));
    }

    public int countNumberOfBlocksSimilar(int r, int c, Cell[][] board) {
        int res = 1;
        BlockType type = ((Block)board[r][c]).getBlockType();
        board[r][c] = null;

        if (r - 1 >= 0 && board[r - 1][c] instanceof Block &&
                ((Block)board[r - 1][c]).getBlockType() == type) {
            res += countNumberOfBlocksSimilar(r - 1, c, board);
        }

        if (r + 1 < board.length && board[r + 1][c] instanceof Block &&
                ((Block)board[r + 1][c]).getBlockType() == type) {
            res += countNumberOfBlocksSimilar(r + 1, c, board);
        }

        if (c - 1 >= 0 && board[r][c - 1] instanceof Block &&
                ((Block)board[r][c - 1]).getBlockType() == type) {
            res += countNumberOfBlocksSimilar(r, c - 1, board);
        }

        if (c + 1 < board[r].length && board[r][c + 1] instanceof Block &&
                ((Block)board[r][c + 1]).getBlockType() == type) {
            res += countNumberOfBlocksSimilar(r, c + 1, board);
        }

        return res;
    }

    /** @return the name of the level*/
    public String getName() {
        return name;
    }

    /** @return the number of columns of the level */
    public int getColumns() {
        return columns;
    }

    /** @return the number of the rows of the level */
    public int getRows() {
        return rows;
    }

    /** increment the number of moved done by the player */
    public void played() {
        numberOfPlay++;
    }

    /** @return the number of moved done by the player */
    public int getNumberOfPlay() {
        return numberOfPlay;
    }

    /** @return the score of the player */
    public int getScore() {
        return score;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }
}
