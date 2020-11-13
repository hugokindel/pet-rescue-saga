package com.g10.prs.core.resource;

import com.g10.prs.level.Level;
import com.g10.prs.core.type.PrsException;

import java.io.File;

public class Resources {
    public static String getDataDirectory() throws PrsException {
        String path = "data";
        createDirectoryIfNotExist(path);
        return path;
    }

    public static String getLevelsDirectory() throws PrsException {
        String path = getDataDirectory() + "/levels";
        createDirectoryIfNotExist(path);
        return path;
    }

    public static String getSaveDirectory() throws PrsException {
        String path = getDataDirectory() + "/saves";
        createDirectoryIfNotExist(path);
        return path;
    }

    private static void createDirectoryIfNotExist(String path) throws PrsException {
        File saveDirectory = new File(path);

        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdirs()) {
                throw new PrsException("Cannot create save directory!");
            }
        }
    }
}
