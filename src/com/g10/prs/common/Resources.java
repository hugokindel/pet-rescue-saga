package com.g10.prs.common;

import com.g10.prs.common.PrsException;
import com.g10.prs.level.Level;
import com.g10.prs.njson.NJsonReader;
import com.g10.prs.util.FileUtil;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.List;

/** Contains useful function to find various paths for the project's data. */
public class Resources {
    public static Map<String, BufferedImage> images;
    public static Map<String, Clip> sounds;

    private static boolean imagesLoaded = false;
    private static boolean fontsLoaded = false;
    private static boolean soundsLoaded = false;

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

    public static String getAssetsDirectory() throws PrsException {
        return createPathIfNeeded(getDataDirectory() + "/assets");
    }

    public static String getImagesDirectory() throws PrsException {
        return createPathIfNeeded(getAssetsDirectory() + "/images");
    }

    public static String getFontsDirectory() throws PrsException {
        return createPathIfNeeded(getAssetsDirectory() + "/fonts");
    }

    public static String getSoundsDirectory() throws PrsException {
        return createPathIfNeeded(getAssetsDirectory() + "/sounds");
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

    public static void loadContent() {
        if (!imagesLoaded) {
            try {
                images = new HashMap<>();
                for (final File fileEntry : new File(getImagesDirectory()).listFiles()) {
                    if (!fileEntry.isDirectory()) {
                        String[] segments;
                        if (fileEntry.getAbsolutePath().contains("\\")) {
                            segments = fileEntry.getAbsolutePath().split("\\\\");
                        } else {
                            segments = fileEntry.getAbsolutePath().split("/");
                        }
                        images.put(segments[segments.length - 1], ImageIO.read(new File(Resources.getImagesDirectory() + "/" + segments[segments.length - 1])));
                    }
                }
                imagesLoaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!fontsLoaded) {
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();

            try {
                for (final File fileEntry : new File(getFontsDirectory()).listFiles()) {
                    if (!fileEntry.isDirectory()) {
                        String[] segments;
                        if (fileEntry.getAbsolutePath().contains("\\")) {
                            segments = fileEntry.getAbsolutePath().split("\\\\");
                        } else {
                            segments = fileEntry.getAbsolutePath().split("/");
                        }
                        gEnv.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(Resources.getFontsDirectory() + "/" + segments[segments.length - 1])));
                    }
                }
                fontsLoaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!soundsLoaded) {
            try {
                sounds = new HashMap<>();
                for (final File fileEntry : new File(getSoundsDirectory()).listFiles()) {
                    if (!fileEntry.isDirectory()) {
                        String[] segments;
                        if (fileEntry.getAbsolutePath().contains("\\")) {
                            segments = fileEntry.getAbsolutePath().split("\\\\");
                        } else {
                            segments = fileEntry.getAbsolutePath().split("/");
                        }
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(new File( Resources.getSoundsDirectory() + "/" + segments[segments.length - 1])));
                        sounds.put(segments[segments.length - 1], clip);
                    }
                }
                imagesLoaded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static Clip getSound(String name) {
        return sounds.get(name);
    }
}
