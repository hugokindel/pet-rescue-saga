package com.g10.prs.common.print;

import com.g10.prs.common.PrsException;
import com.g10.prs.core.Resources;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains every functions to send output to the user.
 * For it to work properly, the terminal used for running the program should support ANSI escape sequences,
 * most UNIX terminals should support it, and modern Windows Powershell and cmd.exe should too. */
public class Out {
    /** The prefix to use for every messages. */
    private static final String prefix = "[prs]";

    /** The prefix to use for every warnings. */
    private static final String warningPrefix = "[warning]";

    /** The prefix to use for every errors. */
    private static final String errorPrefix = "[error]";

    /** The output file to print to. */
    private static PrintWriter fileOutput;

    /** Defines if we are at the start of a line (to know if we need to prefix the printing). */
    private static boolean isStartOfLine;

    /**
     * Starts the output system.
     *
     * Needed at the start of the program!
     * It creates the logging file, and initialize various informations.
     */
    public static void start() {
        // Fix to check if we are running inside IntelliJ IDEA,
        // this information is used to avoid calling any clearing process from inside the IDE terminal,
        // because if we do, it displays an unknown "" symbol.
        boolean inIntelliJ = false;
        for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (arg.startsWith("-javaagent") && arg.contains("JetBrains")) {
                inIntelliJ = true;
            }
        }

        // Fix permitting to use ANSI Escape sequences on Windows Powershell and cmd (at least on updated Windows 10).
        // and also clear the terminal (on Windows and UNIX platforms).
        if (!inIntelliJ) {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    Runtime.getRuntime().exec("clear");
                }
            } catch (Exception ignored) {

            }
        }

        try {
            removeOldestLogFile();

            fileOutput = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    Resources.getLogsDirectory() + "/prs-" + new SimpleDateFormat("yyyy_MM_dd-HH-mm-ss")
                            .format(new Date()) + ".log", false), StandardCharsets.UTF_8)));
        } catch (Exception e) {
            fileOutput = null;
        }

        isStartOfLine = true;
    }

    /** Shutdowns the output system.
     *
     * Needed at the end of the program!
     * It saves the logging file.
     */
    public static void end() {
        // Saves and close the output file if it exists.
        try {
            if (fileOutput != null) {
                fileOutput.flush();
                fileOutput.close();
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Prints to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void print(Object object) {
        printAndResetColor((isStartOfLine ? prefix + " ": "") + object);
        isStartOfLine = false;
    }

    /**
     * Prints a new line to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void println(Object object) {
        print(object + "\n");
        isStartOfLine = true;
    }

    /**
     * Prints an error to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printError(Object object) {
        printAndResetColor(TextColor.Red + (isStartOfLine ? prefix: "") +
                errorPrefix + Color.ResetAll + " " + object);
        isStartOfLine = false;
    }

    /**
     * Prints a new line of error to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printlnError(Object object) {
        printError(object + "\n");
        isStartOfLine = true;
    }

    /**
     * Prints a warning to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printWarning(Object object) {
        printAndResetColor(TextColor.Yellow + (isStartOfLine ? prefix: "") +
                warningPrefix + Color.ResetAll + " " + object);
        isStartOfLine = false;
    }

    /**
     * Prints a new line of warning to output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printlnWarning(Object object) {
        printWarning(object + "\n");
        isStartOfLine = false;
    }

    /** Print a new line to output. */
    public static void println() {
        print("\n");
        isStartOfLine = true;
    }

    /** Clear the console. */
    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

    /** Simulates a new line (used only for various scenarios in logging file). */
    public static void simulateNewLine() {
        isStartOfLine = true;
        fileOutput.println();
    }

    /**
     * Prints to file output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printToFile(Object object) {
        fileOutput.print(object);
    }


    /**
     * Prints a new line to file output.
     *
     * @param object The object (with toString()) to print.
     */
    public static void printlnToFile(Object object) {
        fileOutput.println(object);
    }

    /** @return the prefix to use for every messages. */
    public static String getPrefix() {
        return prefix;
    }

    /** @return the prefix to use for every warnings. */
    public static String getErrorPrefix() {
        return errorPrefix;
    }

    /** @return the prefix to use for every errors. */
    public static String getWarningPrefix() {
        return warningPrefix;
    }

    /**
     * Prints text to both system output and logging file, then reset ANSI color.
     *
     * @param object The object (with toString()) to print.
     */
    private static void printAndResetColor(Object object) {
        String text = object + Color.ResetAll;

        System.out.print(text);

        if (fileOutput != null) {
            try {
                // Regular expression to remove ANSI escape sequences (e.g. colors),
                // because we don't want to write ANSI escape sequences on the log file, as it wouldn't be recognized.
                // Implementation from: https://stackoverflow.com/questions/14693701/how-can-i-remove-the-ansi-escape-sequences-from-a-string-in-python
                fileOutput.print(text.replaceAll("(\\x9B|\\x1B\\[)[0-?]*[ -\\/]*[@-~]", ""));
            } catch (Exception ignored) {

            }
        }
    }

    /** Removes the oldest log files in there is more than 8 log files. */
    private static void removeOldestLogFile() throws PrsException {
        File[] logFiles = new File(Resources.getLogsDirectory()).listFiles();

        if (logFiles.length < 8) {
            return;
        }

        long oldestDate = -1;
        File oldestFile = null;

        for(File file : logFiles) {
            if(oldestDate == -1 || file.lastModified() < oldestDate) {
                oldestDate = file.lastModified();
                oldestFile = file;
            }
        }

        oldestFile.delete();
    }
}
