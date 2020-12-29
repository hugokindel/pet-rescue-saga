package com.g10.prs.option;

import com.g10.prs.common.print.Out;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class defines a runnable command, which means it can read arguments back from the CLI and set any @Option
 * attributes. Some syntax is inspired from: https://picocli.info */
public abstract class Runnable {
    /** Option to show the help message. */
    @Option(names = {"-h", "--help"}, description = "Montre ce message d'aide.")
    protected boolean showHelp;

    /** Option to show the version message. */
    @Option(names = {"-v", "--version"}, description = "Affiche le numero de version.")
    protected boolean showVersion;

    /** Class constructor. */
    public Runnable() {
        showHelp = false;
        showVersion = false;
    }

    /**
     * Run this command.
     *
     * @param args The arguments.
     * @return the return code.
     */
    public abstract int run(String[] args);

    /**
     * Read every arguments provided and try to see if any option is corresponding to define their values.
     *
     * @param args The arguments.
     * @param classWithArgs The child's class.
     * @param <T> The type of the child class.
     */
    protected <T extends Runnable> void readArguments(String[] args, Class<T> classWithArgs) {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(Runnable.class.getDeclaredFields()));
        fields.addAll(Arrays.asList(classWithArgs.getDeclaredFields()));
        fields.removeIf(field -> !field.isAnnotationPresent(Option.class));

        for (String arg : args) {
            if (arg.startsWith("-")) {
                String[] parts = arg.split("=");
                boolean find = false;

                for (Field field : fields) {
                    for (String name : field.getAnnotation(Option.class).names()) {
                        if (parts[0].equals(name)) {
                            find = true;

                            try {
                                field.setAccessible(true);
                                if (field.getType() == boolean.class) {
                                    field.set(this, true);
                                } else {
                                    field.set(this, Parser.parse(parts[1], field.getType()));
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            break;
                        }
                    }

                    if (find) {
                        break;
                    }
                }

                if (!find) {
                    displayUnknownOption(parts[0], fields);
                }
            }
        }

        if (showHelp) {
            displayHelp(classWithArgs, fields);
        }

        if (showVersion) {
            displayVersion(classWithArgs);
        }
    }

    /**
     * Shows the unknown option along with the closest one (if found).
     *
     * @param unknownOption The unknown option.
     * @param fields The fields to search in.
     */
    private void displayUnknownOption(String unknownOption, ArrayList<Field> fields) {
        int distance = -1;
        String nearest = "";

        for (Field field : fields) {
            Option option = field.getAnnotation(Option.class);

            for (String name : option.names()) {
                int optionDistance = calculateLevenshteinDistance(unknownOption, name);

                if (distance == -1 || optionDistance < distance) {
                    distance = optionDistance;
                    nearest = name;
                }
            }
        }

        Out.println("Option inconnue '" + unknownOption + "' !");

        if (!nearest.isEmpty()) {
            Out.println("Vouliez-vous dire '" + nearest + "' ?");
        }
    }

    /**
     * Shows the program's version.
     *
     * @param classWithArgs The child's class.
     * @param <T> The type of the child's class.
     */
    private <T extends Runnable> void displayVersion(Class<T> classWithArgs) {
        Out.println("Version: " + classWithArgs.getAnnotation(Command.class).version());
    }

    /**
     * Shows the program's help message.
     *
     * @param classWithArgs The child's class.
     * @param fields The list of fields to show.
     * @param <T> The type of the child's class.
     */
    private <T extends Runnable> void displayHelp(Class<T> classWithArgs, ArrayList<Field> fields) {
        Out.println("usage: java -jar " + classWithArgs.getAnnotation(Command.class).name() + ".jar [options...]");

        Out.println();

        for (String line : classWithArgs.getAnnotation(Command.class).description()) {
            Out.println(line);
        }

        Out.println();

        Out.println("Options:");
        for (Field field : fields) {
            Option option = field.getAnnotation(Option.class);
            int numberOfNames = option.names().length;

            Out.print(" \t");

            for (int i = 0; i < numberOfNames; i++) {
                Out.print(option.names()[i] + (i == numberOfNames - 1 ? "" : ", "));
            }

            if (!option.usage().isEmpty()) {
                Out.print("=" + option.usage());
            }

            Out.println();

            for (String line : option.description()) {
                Out.print(" \t\t");
                Out.println(line);
            }
        }
    }

    /**
     * Calculate the levenshtein distance.
     * Implementation from: http://rosettacode.org/wiki/Levenshtein_distance#Java
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return the distance between s1 and s2.
     */
    public static int calculateLevenshteinDistance(String s1, String s2) {
        if(s1.length() == 0) {
            return s2.length();
        } else if(s2.length() == 0) {
            return s1.length();
        }

        if(s1.charAt(0) == s2.charAt(0)) {
            return calculateLevenshteinDistance(s1.substring(1), s2.substring(1));
        }

        int a = calculateLevenshteinDistance(s1.substring(1), s2.substring(1));
        int b = calculateLevenshteinDistance(s1, s2.substring(1));
        int c = calculateLevenshteinDistance(s1.substring(1), s2);

        if(a > b) {
            a = b;
        }

        if(a > c) {
            a = c;
        }

        return a + 1;
    }
}
