package com.g10.prs.common;

import com.g10.prs.common.PrsException;
import com.g10.prs.level.Level;
import com.g10.prs.njson.NJsonReader;
import com.g10.prs.util.FileUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/** Contains useful function to find various paths for the project's data. */
public class Resources {
    /**
     * Gets the data directory's path.
     *
     * @return the path.
     */
    public static String getDataDirectory() throws PrsException {
        return createPathIfNeeded("data");
    }

    /**
     * Gets the levels directory's path.
     *
     * @return the path.
     */
    public static String getLevelsDirectory() throws PrsException {
        return createPathIfNeeded(getDataDirectory() + "/levels");
    }

    /**
     * Gets the campaign levels directory's path.
     *
     * @return the path.
     */
    public static String getCampaignLevelsDirectory() throws PrsException {
        return createPathIfNeeded(getLevelsDirectory() + "/campaign");
    }

    /**
     * Gets the custom levels directory's path.
     *
     * @return the path.
     */
    public static String getCustomLevelsDirectory() throws PrsException {
        return createPathIfNeeded(getLevelsDirectory() + "/custom");
    }

    /**
     * Gets the save directory's path.
     *
     * @return the path.
     */
    public static String getSaveDirectory() throws PrsException {
        return createPathIfNeeded(getDataDirectory() + "/saves");
    }

    /**
     * Gets the logs directory's path.
     *
     * @return the path.
     */
    public static String getLogsDirectory() throws PrsException {
        return createPathIfNeeded(getDataDirectory() + "/logs");
    }

    public static List<String> getCampaignLevelsList() {
        List<String> levels = new ArrayList<>();

        try {
            for (Object object : new NJsonReader(getLevelsDirectory() + "/campaign.njson").readArray()) {
                levels.add((String)object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return levels;
    }

    /**
     * Creates a path if needed.
     *
     * @param filepath The path to use.
     * @return the path.
     */
    private static String createPathIfNeeded(String filepath) throws PrsException {
        FileUtil.createDirectoryIfNotExist(filepath);
        return filepath;
    }
}
