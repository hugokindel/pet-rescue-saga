package com.g10.prs.resource;

import com.g10.prs.level.Level;
import com.g10.prs.util.PrsException;

import java.io.File;

public class Manager {
    public static Level loadLevel(String fileName) throws Exception {
        return new Level(getLevelsDirectory() + "/" + fileName);
    }

    public static String getDataDirectory() throws Exception {
        String path = "data";
        createDirectoryIfNotExist(path);
        return path;
    }

    public static String getLevelsDirectory() throws Exception {
        String path = getDataDirectory() + "/levels";
        createDirectoryIfNotExist(path);
        return path;
    }

    public static String getSaveDirectory() throws Exception {
        String path = getDataDirectory() + "/saves";
        createDirectoryIfNotExist(path);
        return path;
    }

    private static void createDirectoryIfNotExist(String path) throws Exception {
        File saveDirectory = new File(path);

        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdirs()) {
                throw new PrsException("Cannot create save directory!");
            }
        }
    }
}
