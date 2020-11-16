package com.g10.prs.core;

import java.io.File;

/** Contains various functions for files handling. */
public class FileUtil {
    /**
     * Creates directories recursively if needed.
     *
     * @param path The path to use.
     */
    public static void createDirectoryIfNotExist(String path) throws PrsException {
        File saveDirectory = new File(path);

        if (!saveDirectory.exists()) {
            if (!saveDirectory.mkdirs()) {
                throw new PrsException("Cannot create save directory!");
            }
        }
    }
}
