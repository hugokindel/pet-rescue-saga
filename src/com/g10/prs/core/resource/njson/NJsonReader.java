package com.g10.prs.core.resource.njson;

import com.g10.prs.core.type.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NJsonReader {
    Reader reader;
    int currentColumn;
    int currentLine;
    char currentCharacter;

    public Map<String, Object> parse(String filePath) throws IOException, NJSonCannotParseException {
        return parse(new FileInputStream(new File(filePath)));
    }

    public Map<String, Object> parse(InputStream file) throws NJSonCannotParseException, IOException {
        reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
        currentColumn = 1;
        currentLine = 1;

        next();
        return parseMap();
    }

    private Pair<String, Object> parseElement() throws NJSonCannotParseException, IOException {
        String identifier = parseString();
        check(currentCharacter, ':');
        Object value = parseValue();

        return new Pair<>(identifier, value);
    }

    private Object parseValue() throws IOException, NJSonCannotParseException {
        next();
        if (is(currentCharacter, '"')) {
            return parseString();
        } else if (is(currentCharacter, digits, spaces, '-', '.')) {
            return parseNumber();
        } else if (is(currentCharacter, '{')) {
            return parseMap();
        } else if (is(currentCharacter, 't', 'f')) {
            return parseBoolean();
        } else if (is(currentCharacter, '\'')) {
            return parseCharacter();
        } else if (is(currentCharacter, '[')) {
            return parseArray();
        } else if (is(currentCharacter, 'n')) {
            return parseNull();
        }

        return null;
    }

    private String parseString() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        while (!is(next(true), '"')) {
            contentBuilder.append(get());
        }
        next();

        return contentBuilder.toString();
    }

    private String parseCharacter() throws IOException {
        next();
        String character = get();
        next();
        next();

        return character;
    }

    private Object parseNumber() throws IOException {
        StringBuilder contentBuilder = new StringBuilder(new String(new char[] {currentCharacter}));
        while (is(next(), digits, '-', '.')) {
            contentBuilder.append(currentCharacter);
        }

        String number = contentBuilder.toString();
        if (number.contains(".")) {
            return Double.parseDouble(number);
        }

        return Integer.parseInt(number);
    }

    private boolean parseBoolean() throws IOException, NJSonCannotParseException {
        StringBuilder contentBuilder = new StringBuilder(new String(new char[] {currentCharacter}));
        while (Character.isAlphabetic(next(true))) {
            if (!"false".startsWith(contentBuilder.toString()) && !"true".startsWith(contentBuilder.toString())) {
                throw new NJSonCannotParseException("Not boolean");
            }

            contentBuilder.append(currentCharacter);
        }

        return contentBuilder.toString().equals("true");
    }

    private Object parseNull() throws IOException, NJSonCannotParseException {
        StringBuilder contentBuilder = new StringBuilder(new String(new char[] {currentCharacter}));
        while (Character.isAlphabetic(next(true))) {
            if (!"null".startsWith(contentBuilder.toString())) {
                throw new NJSonCannotParseException("Not null");
            }

            contentBuilder.append(currentCharacter);
        }

        return null;
    }

    private Map<String, Object> parseMap() throws NJSonCannotParseException, IOException {
        Map<String, Object> elements = new HashMap<>();

        do {
            check(next(), '}', '"');
            if (currentCharacter == '}') {
                break;
            }
            Pair<String, Object> pair = parseElement();
            elements.put(pair.getObject1(), pair.getObject2());
            check(currentCharacter, '}', ',');
        } while (!is(currentCharacter, '}'));
        next();

        return elements;
    }

    private List<Object> parseArray() throws NJSonCannotParseException, IOException {
        List<Object> elements = new ArrayList<>();

        do {
            Object element = parseValue();
            if (element == null) {
                break;
            }
            elements.add(element);
            check(currentCharacter, ']', ',');
        } while (!is(currentCharacter, ']'));
        next();

        return elements;
    }

    private char next() throws IOException {
        return next(false);
    }

    private char next(boolean isInStringOrComment) throws IOException {
        currentCharacter = (char)reader.read();
        currentColumn++;

        if (!isInStringOrComment && is(currentCharacter, '/')) {
            char currChar = next(true);
            if (is(currChar, '/')) {
                // Comments
                int currentLine = this.currentLine;

                while (currentLine == this.currentLine) {
                    next();
                }
            } else if (is(currChar, '*')) {
                // Multiline comments
                boolean foundFirst = false;
                do {
                    if (!foundFirst && next(true) == '*') {
                        foundFirst = true;
                    } else if (foundFirst) {
                        if (next(true) == '/') {
                            break;
                        } else {
                            foundFirst = false;
                        }
                    }
                } while (true);
                next();
            }
        } else if (isRF()) {
            currentLine++;
            currentColumn = 0;
        }

        while (isCLRF() || (!isInStringOrComment && isSpace())) {
            next();
        }

        return currentCharacter;
    }

    private void check(char c, Object... objects) throws NJSonCannotParseException {
        if (!is(c, objects)) {
            throw new NJSonCannotParseException("Unexpected character '" + c + "' instead of '" + Arrays.toString(objects) + "'!");
        }
    }

    private boolean is(char c, Object... objects) {
        boolean found = false;

        for (Object expected : objects) {
            if (expected instanceof char[]) {
                char[] values = (char[])expected;
                for (char value : values) {
                    found = is(c, value);

                    if (found) {
                        break;
                    }
                }
            } else {
                char value = (char)expected;

                if (c == value) {
                    found = true;
                }
            }

            if (found) {
                break;
            }
        }

        return found;
    }

    private boolean isSpace() {
        return currentCharacter == ' ' || currentCharacter == '\t';
    }

    private boolean isRF() {
        return currentCharacter == '\n';
    }

    private boolean isCLRF() {
        return currentCharacter == '\r' || isRF();
    }

    private String get() throws IOException {
        if (currentCharacter == '\\') {
            char oldChar = currentCharacter;
            next();
            if (currentCharacter == 'b') {
                return "\b";
            } else if (currentCharacter == 't') {
                return "\t";
            } else if (currentCharacter == 'n') {
                return "\n";
            } else if (currentCharacter == 'f') {
                return "\f";
            } else if (currentCharacter == 'r') {
                return "\r";
            } else if (currentCharacter == '"') {
                return "\"";
            } else if (currentCharacter == '\'') {
                return "'";
            } else if (currentCharacter == '\\') {
                return "\\";
            }

            return new String(new char[] {oldChar, currentCharacter});
        }

        return new String(new char[] {currentCharacter});
    }

    private static final char[] spaces = {' ', '\t'};
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
}
