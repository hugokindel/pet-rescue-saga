package com.g10.prs.core.printer;

import com.g10.prs.core.resource.Resources;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Out {
    public static final String prefix = "[prs]";
    public static final String error = "[error]";
    public static final String warning = "[warning]";
    static PrintWriter fileOutput;
    static boolean isStartOfLine;

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

        // Create the output file to keep log, if possible.
        try {
            fileOutput = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    Resources.getDataDirectory() + "/all-logs.log", false), StandardCharsets.UTF_8)));
        } catch (Exception e) {
            fileOutput = null;
        }

        isStartOfLine = true;
    }

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

    public static void print(Object object) {
        printAndResetColor((isStartOfLine ? prefix + " ": "") + object);
        isStartOfLine = false;
    }

    public static void println(Object object) {
        print(object + "\n");
        isStartOfLine = true;
    }

    public static void printError(Object object) {
        printAndResetColor(TextColor.Red + (isStartOfLine ? prefix: "") + error + Color.ResetAll + " " + object);
        isStartOfLine = false;
    }

    public static void printlnError(Object object) {
        printError(object + "\n");
        isStartOfLine = true;
    }

    public static void printWarning(Object object) {
        printAndResetColor(TextColor.Yellow + (isStartOfLine ? prefix: "") + warning + Color.ResetAll + " " + object);
        isStartOfLine = false;
    }

    public static void printlnWarning(Object object) {
        printWarning(object + "\n");
        isStartOfLine = false;
    }

    public static void println() {
        print("\n");
        isStartOfLine = true;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

    public static void simulateNewLine() {
        isStartOfLine = true;
        fileOutput.println();
    }

    public static void printToFile(Object object) {
        fileOutput.print(object);
    }

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
}
