package com.g10.prs.level;

import com.g10.prs.resource.Manager;
import com.g10.prs.resource.DataParser;
import com.g10.prs.util.PrsException;

import java.io.File;
import java.io.FileInputStream;
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

    public Level(String filePath) throws Exception {
        readLevelFile(filePath);
    }

    private void readLevelFile(String filePath) throws Exception {
        File levelFile = new File(Manager.getLevelsDirectory() + "/" + filePath);

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

    private <T> T readLevelDataValue(Map<String, Object> dataMap, String key, String errorMessage) throws Exception {
        if (!dataMap.containsKey(key)) {
            throw new PrsException("[ERROR] " + errorMessage);
        }

        return (T)dataMap.get(key);
    }

    private Cell[][] readLevelDataBoard(Map<String, Object> dataMap, String key, String errorMessage) throws Exception {
        List<List<Object>> data = readLevelDataValue(dataMap, key, errorMessage);
        Cell[][] board = new Cell[boardHeight][boardWidth];

        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int value = (int)data.get(y).get(x);

                if (value == 0) {
                    board[y][x] = new Cell(CellType.Empty);
                } else if (value <= 3) {
                    board[y][x] = new Animal(AnimalType.values()[value - 1]);
                } else if (value <= 8) {
                    board[y][x] = new Block(BlockType.values()[value - 4]);
                } else if (value <= 9) {
                    board[y][x] = new Obstacle(ObstacleType.values()[value - 9]);
                }
            }
        }

        return board;
    }

    private Visibility[][] readLevelDataVisibleZone(Map<String, Object> dataMap, String key, String errorMessage) throws Exception {
        List<List<Object>> data = readLevelDataValue(dataMap, key, errorMessage);
        Visibility[][] board = new Visibility[visibleZoneHeight][visibleZoneWidth];

        for (int y = 0; y < visibleZoneHeight; y++) {
            for (int x = 0; x < visibleZoneWidth; x++) {
                board[y][x] = Visibility.values()[(int)data.get(y).get(x)];
            }
        }

        return board;
    }
}
