package com.g10.prs.core.njson;

import com.g10.prs.core.Resources;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NJsonWriter {
    /** The output file to print to. */
    private final PrintWriter file;
    private String prefix;

    public NJsonWriter(String filepath) throws FileNotFoundException {
        file = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filepath), StandardCharsets.UTF_8)));
        prefix = "";
    }

    public static void write(String filepath, Map<String, Object> map) throws FileNotFoundException {
        NJsonWriter writer = new NJsonWriter(filepath);
        writer.writeMap(map);
        writer.close();
    }

    public void writeMap(Map<String, Object> map) {
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

    public void writeMapElement(Map.Entry<String, Object> element) {
        write("\"" + element.getKey() + "\": ");
        writeValue(element.getValue());
    }

    public void writeArray(List<Object> array) {
        write("[\n", false);

        String oldPrefix = prefix;
        prefix += "\t";

        int i = 1;
        for (Object object : array) {
            write("");
            writeValue(object);

            if (i != array.size()) {
                write(",", false);
            }

            write("\n", false);

            i++;
        }

        prefix = oldPrefix;

        write("]");
    }

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

    public void writeNumber(Object value) {
        write(value.toString(), false);
    }

    public void writeString(Object value) {
        write("\"" + getUnescaped(value.toString()) + "\"", false);
    }

    public void writeCharacter(Object value) {
        write("'" + getUnescaped(value.toString()) + "'", false);
    }

    public void close() {
        file.close();
    }

    private void write(String text) {
        write(text, true);
    }

    private void write(String text, boolean usePrefix) {
        file.write((usePrefix ? prefix : "") + text);
    }

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
