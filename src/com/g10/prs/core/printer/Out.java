package com.g10.prs.core.printer;

import com.g10.prs.core.resource.Resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Out {
    public static final String prefix = "[prs]";
    public static final String error = "[error]";
    public static final String warning = "[warning]";
    private static FileWriter writer;
    static BufferedWriter bw;
    static PrintWriter out;

    public static void start() {
        try {
            writer = new FileWriter(Resources.getDataDirectory() + "/all-logs.log", false);
            bw = new BufferedWriter(writer);
            out = new PrintWriter(bw);
        } catch (Exception e) {
            writer = null;
        }
    }

    public static void end() {
        try {
            if (writer != null) {
                out.flush();
                out.close();
            }
        } catch (Exception ignored) {

        }
    }

    public static void print(Object object) {
        printAndResetColor(prefix + " " + object);
    }

    public static void println(Object object) {
        print(object + "\n");
    }

    public static void printError(Object object) {
        printAndResetColor(TextColor.Red + prefix + error + Color.ResetAll + " " + object);
    }

    public static void printlnError(Object object) {
        printError(object + "\n");
    }

    public static void printWarning(Object object) {
        printAndResetColor(TextColor.Yellow + prefix + warning + Color.ResetAll + " " + object);
    }

    public static void printlnWarning(Object object) {
        printWarning(object + "\n");
    }

    public static void println() {
        print("\n");
    }

    private static void printAndResetColor(Object object) {
        String text = object + Color.ResetAll;

        System.out.print(text);

        if (writer != null) {
            try {
                // Regular expression to remove ANSI escape sequences (e.g. colors).
                // Implementation from: https://stackoverflow.com/questions/14693701/how-can-i-remove-the-ansi-escape-sequences-from-a-string-in-python
                out.print(text.replaceAll("(\\x9B|\\x1B\\[)[0-?]*[ -\\/]*[@-~]", ""));
            } catch (Exception ignored) {

            }
        }
    }
}
