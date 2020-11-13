package com.g10.prs.level;

import com.g10.prs.core.printer.Out;
import com.g10.prs.core.printer.TextColor;
import com.g10.prs.core.resource.Resources;
import com.g10.prs.core.resource.DataParser;
import com.g10.prs.core.type.PrsException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Level {
    private String name;
    private int version;
    private String[] authors;
    private Cell[][] board;
    private int boardWidth;
    private int boardHeight;
    private Visibility[][] visibleZone;
    private int visibleZoneWidth;
    private int visibleZoneHeight;
    private int visibleZoneOriginX;
    private int visibleZoneOriginY;
    private int animalsLeft;
    private int score;

    public Level(String filePath) throws PrsException, FileNotFoundException, IOException {
        readLevelFile(filePath);
    }

    private void readLevelFile(String filePath) throws PrsException, FileNotFoundException, IOException {
        File levelFile = new File(Resources.getLevelsDirectory() + "/" + filePath);

        if (!levelFile.exists()) {
            throw new PrsException("[ERROR] Cannot load error file.");
        }

        Map<String, Object> data = DataParser.parseData("1.0-LEVEL", new FileInputStream(levelFile));

        if (data == null) {
            throw new PrsException("unknown format");
        }

        name = readLevelDataValue(data, "name", "Name of level missing!");
        version = readLevelDataValue(data, "version", "Version of level missing!");
        authors = ((List<Object>)readLevelDataValue(data, "authors", "Authors missing!")).toArray(new String[0]);
        boardWidth = readLevelDataValue(data, "board_width", "Board's width missing!");
        boardHeight = readLevelDataValue(data, "board_height", "Board's height missing!");
        board = readLevelDataBoard(data, "board", "Board missing!");
        visibleZoneWidth = readLevelDataValue(data, "visible_zone_width", "Visible zone's width missing!");
        visibleZoneHeight = readLevelDataValue(data, "visible_zone_height", "Visible zone's height missing!");
        visibleZoneOriginX = readLevelDataValue(data, "visible_zone_x", "Visible zone origin's x missing!");
        visibleZoneOriginY = readLevelDataValue(data, "visible_zone_y", "Visible zone origin's y missing!");
        visibleZone = readLevelDataVisibleZone(data, "visible_zone", "Visible zone missing!");
    }

    private <T> T readLevelDataValue(Map<String, Object> dataMap, String key, String errorMessage) throws PrsException {
        if (!dataMap.containsKey(key)) {
            throw new PrsException("[ERROR] " + errorMessage);
        }

        return (T)dataMap.get(key);
    }

    private Cell[][] readLevelDataBoard(Map<String, Object> dataMap, String key, String errorMessage) throws PrsException {
        List<List<Object>> data = readLevelDataValue(dataMap, key, errorMessage);
        Cell[][] board = new Cell[boardHeight][boardWidth];

        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int value = (int)data.get(y).get(x);

                if (value == 0) {
                    board[y][x] = new Cell(CellType.Empty);
                } else if (value <= 3) {
                    board[y][x] = new Animal(AnimalType.values()[value - 1]);
                    animalsLeft++;
                } else if (value <= 8) {
                    board[y][x] = new Block(BlockType.values()[value - 4]);
                } else if (value <= 9) {
                    board[y][x] = new Obstacle(ObstacleType.values()[value - 9]);
                }
            }
        }

        return board;
    }

    private Visibility[][] readLevelDataVisibleZone(Map<String, Object> dataMap, String key, String errorMessage) throws PrsException {
        List<List<Object>> data = readLevelDataValue(dataMap, key, errorMessage);
        Visibility[][] board = new Visibility[visibleZoneHeight][visibleZoneWidth];

        for (int y = 0; y < visibleZoneHeight; y++) {
            for (int x = 0; x < visibleZoneWidth; x++) {
                board[y][x] = Visibility.values()[(int)data.get(y).get(x)];
            }
        }

        return board;
    }

    // TODO: Obstacles
    public boolean remove(int x, int y) {
        if (board[y][x] instanceof Block) {
            removeSimilarBlocks(x, y);

            boolean needToCheckGravity = true;
            while (needToCheckGravity) {
                needToCheckGravity = applyGravity();
                checkForAnimal();
            }

            /*boolean modifiedV;
            boolean modifiedH;

            do {
                modifiedV = applyGravity();
                checkForAnimal(x);
                //modifiedH = shiftBoardHorizontally(x);
                modifiedH = false;
            } while (modifiedV || modifiedH);*/
            print();

            return checkWin();
        }

        return false;
    }

    private void removeSimilarBlocks(int x, int y) {
        Block block = (Block)board[y][x];
        board[y][x] = null;

        if (y - 1 >= 0 && board[y - 1][x] instanceof Block && ((Block)board[y - 1][x]).getBlockType() == block.getBlockType()) {
            removeSimilarBlocks(x, y - 1);
        }
        if (y + 1 < boardHeight && board[y + 1][x] instanceof Block && ((Block)board[y + 1][x]).getBlockType() == block.getBlockType()) {
            removeSimilarBlocks(x, y + 1);
        }
        if (x - 1 >= 0 && board[y][x - 1] instanceof Block && ((Block)board[y][x - 1]).getBlockType() == block.getBlockType()) {
            removeSimilarBlocks(x - 1, y);
        }
        if (x + 1 < boardWidth && board[y][x + 1] instanceof Block && ((Block)board[y][x + 1]).getBlockType() == block.getBlockType()) {
            removeSimilarBlocks(x + 1, y);
        }
    }

    private boolean applyGravity() {
        boolean modifiedV = false;

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                if (board[y][x] == null) {
                    for (int i = y; i >= findNextObstacleAtColumn(x, y); i--) {
                        if (i > 0 && board[i - 1][x] != null) {
                            modifiedV = true;
                            board[i][x] = board[i - 1][x];
                            board[i - 1][x] = null;
                        } else {
                            board[i][x] = null;
                        }
                    }
                }
            }
        }

        return modifiedV;
    }

    private int findNextObstacleAtColumn(int x, int y) {
        int nextObstacleXPos = 0;

        for (int i = y; i >= 0; i--) {
            if (board[i][x] instanceof Obstacle) {
                nextObstacleXPos = i + 2;
                break;
            }
        }

        return nextObstacleXPos;
    }

    private void checkForAnimal() {
        for (int x = 0; x < boardWidth; x++) {
            if (board[boardHeight - 1][x] instanceof Animal) {
                animalsLeft--;
                board[boardHeight - 1][x] = null;
            }
        }
    }

    private boolean shiftBoardHorizontally(int x) {
        boolean modifiedH = false;

        if (board[boardHeight - 1][x] == null) {
            for (int i = x + 1; i <= boardWidth; i++) {
                for (int y = 0; y < boardHeight; y++) {
                    if (i < boardWidth && board[y][i] != null) {
                        modifiedH = true;
                    }

                    board[y][i - 1] = (i < boardWidth ? board[y][i] : null);
                }
            }
        }

        return modifiedH;
    }

    private boolean checkWin() {
        return animalsLeft == 0;
    }

    public String getName() {
        return name;
    }

    public void print() {
        // First, we print the line with  every column's letter.
        // Print every column's letter.
        Out.print("   ");
        for (int x = 1; x < visibleZoneWidth - 1; x++) {
            Out.print((char)(64 + x));
        }
        Out.println();

        // Then, we print each line
        for (int y = 0; y < visibleZoneHeight; y++) {
            // Print line number on the side.
            if (y != 0 && y != visibleZoneHeight - 1) {
                Out.print(y - 1 + " ");
            } else {
                Out.print("  ");
            }

            // Print current position.
            for (int x = 0; x < visibleZoneWidth ; x++) {
                Visibility visibility = visibleZone[y][x];

                if (visibility == Visibility.Border) {
                    if ((x < visibleZoneWidth - 1 && visibleZone[y][x + 1] == Visibility.Border) &&
                        (y < visibleZoneHeight - 1 && visibleZone[y + 1][x] == Visibility.Border)) {
                        Out.print("┌");
                    } else if ((x - 1 >= 0 && visibleZone[y][x - 1] == Visibility.Border) &&
                               (y < visibleZoneHeight - 1 && visibleZone[y + 1][x] == Visibility.Border)) {
                        Out.print("┐");
                    } else if ((x < visibleZoneWidth - 1 && visibleZone[y][x + 1] == Visibility.Border) &&
                               (y - 1 >= 0 && visibleZone[y - 1][x] == Visibility.Border)) {
                        Out.print("└");
                    } else if ((x - 1 >= 0 && visibleZone[y][x - 1] == Visibility.Border) &&
                               (y - 1 >= 0 && visibleZone[y - 1][x] == Visibility.Border)) {
                        Out.print("┘");
                    } else if ((x < visibleZoneWidth - 1 && visibleZone[y][x + 1] == Visibility.Border) &&
                               (x - 1 >= 0 && visibleZone[y][x - 1] == Visibility.Border)) {
                        Out.print("─");
                    } else if ((y < visibleZoneHeight - 1 && visibleZone[y + 1][x] == Visibility.Border) &&
                               (y - 1 >= 0 && visibleZone[y - 1][x] == Visibility.Border)) {
                        Out.print("│");
                    } else {
                        Out.print("*");
                    }
                } else if (visibility == Visibility.Inside) {
                    Cell cell = board[y + visibleZoneOriginX - 1][x + visibleZoneOriginY - 1];

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
                        Out.print("D");
                    } else if (cell instanceof Obstacle) {
                        Out.print("W");
                    } else {
                        Out.print(" ");
                    }
                } else {
                    Out.print(" ");
                }
            }
            Out.println();
        }
    }
}
