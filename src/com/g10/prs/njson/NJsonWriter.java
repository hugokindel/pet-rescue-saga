package com.g10.prs.njson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * This class is used to write NJson data format.
 * If you need more info on NJson, please look at NJsonReader. */
public class NJsonWriter {
    /** The output file to print to. */
    private final PrintWriter file;

    /** The current prefix (space alignment) to use. */
    private String prefix;

    /** Defines if we currently are writing in an array (only useful for a beautification of two-dimensional arrays). */
    private boolean parentIsArray = false;

    /**
     * Class constructor.
     *
     * @param filepath The file path to use.
     */
    public NJsonWriter(String filepath) throws FileNotFoundException {
        file = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filepath), StandardCharsets.UTF_8)));
        prefix = "";
    }

    /**
     * Writes a map to file in NJSon format.
     *
     * @param filepath The file path to use.
     * @param map The map to write.
     */
    public static void write(String filepath, Map<String, Object> map) throws FileNotFoundException {
        NJsonWriter writer = new NJsonWriter(filepath);
        writer.writeMap(map);
        writer.close();
    }

    /**
     * Writes a list to file in NJSon format.
     *
     * @param filepath The file path to use.
     * @param array The array to write.
     */
    public static void write(String filepath, List<Object> array) throws FileNotFoundException {
        NJsonWriter writer = new NJsonWriter(filepath);
        writer.writeArray(array);
        writer.close();
    }

    /**
     * Writes a map.
     *
     * @param map The map to write.
     */
    public void writeMap(Map<String, Object> map) {
        parentIsArray = false;

        write("{\n", false);

        String oldPrefix = prefix;
        prefix += "\t";

        int i = 1;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            writeMapElement(entry);

            if (i != map.size()) {
                write(",", false);
            }

            write("\n", false);

            i++;
        }

        prefix = oldPrefix;

        write("}");
    }

    /**
     * Writes a map element.
     *
     * @param element The element pair (string, object) to write.
     */
    public void writeMapElement(Map.Entry<String, Object> element) {
        write("\"" + element.getKey() + "\": ");
        writeValue(element.getValue());
    }

    /**
     * Writes an array.
     * Takes care of space alignment with a tweak for two-dimensional arrays as they are used a lot through our data.
     *
     * @param array The array to write.
     */
    public void writeArray(List<Object> array) {
        boolean beforeParentIsArray = parentIsArray;

        if (beforeParentIsArray) {
            write("[", false);
        } else {
            write("[\n", false);
        }

        if (!array.isEmpty() && array.get(0) instanceof List) {
            parentIsArray = true;
        }

        String oldPrefix = prefix;
        prefix += "\t";

        int i = 1;
        for (Object object : array) {
            if (!beforeParentIsArray) {
                write("");
            } else if (i != 1) {
                write(" ", false);
            }

            writeValue(object);

            if (i != array.size()) {
                write(",", false);
            }

            if (!beforeParentIsArray) {
                write("\n", false);
            }

            i++;
        }

        prefix = oldPrefix;

        write("]", !beforeParentIsArray);

        if (!array.isEmpty() && array.get(0) instanceof List) {
            parentIsArray = false;
        }
    }

    /**
     * Writes a value of any supported type (integer, double, string, character, list, map).
     *
     * @param value The value to write.
     */
    public void writeValue(Object value) {
        if (value instanceof Integer || value instanceof Double) {
            writeNumber(value);
        } else if (value instanceof String) {
            writeString(value);
        } else if (value instanceof Character) {
            writeCharacter(value);
        } else if (value instanceof List) {
            writeArray((List<Object>)value);
        } else if (value instanceof Map) {
            writeMap((Map<String, Object>)value);
        }
    }

    /**
     * Writes a number.
     *
     * @param value The number to write.
     */
    public void writeNumber(Object value) {
        write(value.toString(), false);
    }

    /**
     * Writes a string.
     *
     * @param value The string to write.
     */
    public void writeString(Object value) {
        write("\"" + getUnescaped(value.toString()) + "\"", false);
    }

    /**
     * Writes a character.
     *
     * @param value The character to write.
     */
    public void writeCharacter(Object value) {
        write("'" + getUnescaped(value.toString()) + "'", false);
    }

    /** Closes the file. */
    public void close() {
        file.close();
    }

    /**
     * Write a text to file.
     *
     * @param text The text to print.
     */
    private void write(String text) {
        write(text, true);
    }

    /**
     * Write a text to file.
     *
     * @param text The text to print.
     * @param usePrefix Defines if we should write the prefix first.
     */
    private void write(String text, boolean usePrefix) {
        file.write((usePrefix ? prefix : "") + text);
    }

    /**
     * Gets an unescaped string from a string.
     *
     * @param string The string to unescape.
     * @return the unescaped string.
     */
    private String getUnescaped(String string) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);

            if (character == '\b') {
                return "\\b";
            } else if (character == '\t') {
                return "\\t";
            } else if (character == '\n') {
                return "\\n";
            } else if (character == '\f') {
                return "\\f";
            } else if (character == '\r') {
                return "\\r";
            } else if (character == '\"') {
                return "\\\"";
            } else if (character == '\'') {
                return "\\'";
            } else if (character == '\\') {
                return "\\\\";
            }

            result.append(character);
        }

        return result.toString();
    }
}
