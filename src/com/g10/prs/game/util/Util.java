package com.g10.prs.game.util;

import java.io.File;

public class Util {
    public static String getApplicationDataDirectoryPath()
    {
        String system = System.getProperty("os.name").toUpperCase();
        String path;

        if (system.contains("WIN")) {
            path = System.getenv("APPDATA") + "/com.g10.prs";
        } else if (system.contains("MAC")) {
            path = System.getProperty("user.home") + "/Library/Application Support/com.g10.prs";
        } else if (system.contains("NUX")) {
            path = System.getProperty("user.home") + "/.com.g10.prs";
        } else {
            path = System.getProperty("user.dir");
        }

        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdirs();
        }

        return path;
    }
}
