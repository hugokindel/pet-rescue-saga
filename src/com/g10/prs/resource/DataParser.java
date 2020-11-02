package com.g10.prs.resource;

import com.g10.prs.util.Pair;
import com.g10.prs.util.PrsException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParser {
    static Reader reader;
    static int column;
    static int line;
    static char currentCharacter;

    public static Map<String, Object> parseData(String expectedFormat, InputStream file) throws Exception {
        Map<String, Object> symbolTable = new HashMap<>();
        reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
        column = 1;
        line = 1;

        if (!checkFormat(expectedFormat)) {
            return null;
        }

        while (!isEOF()) {
            Pair<String, Object> object = parseSymbol();
            symbolTable.put(object.getObject1(), object.getObject2());
        }

        return symbolTable;
    }

    private static boolean checkFormat(String expectedFormat) throws Exception {
        int currentLine = line;
        StringBuilder format = new StringBuilder();

        getNextCharacter();
        while (currentLine == line) {
            format.append(currentCharacter);
            getNextCharacter();
        }

        return format.toString().replaceAll(" ", "").equals("FORMAT" + expectedFormat);
    }

    private static void getNextCharacter() throws Exception {
        int next = reader.read();
        currentCharacter = (next == -1) ? 0 : (char)next;
        column++;

        if (currentCharacter == '\n') {
            line++;
            column = 0;
        } else if (currentCharacter == '#') {
            int currentLine = line;

            while (currentLine == line) {
                getNextCharacter();
            }
        }

        while (currentCharacter == '\n' || currentCharacter == '\r') {
            getNextCharacter();
        }
    }

    private static Pair<String, Object> parseSymbol() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        String identifier = parseIdentifier();
        parseCharacter('=');
        Object object = parseValue();

        return new Pair<>(identifier, object);
    }

    private static void parseCharacter(char symbol) throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        if (currentCharacter != symbol) {
            throw new PrsException("error not character " + symbol);
        }

        getNextCharacter();
    }

    private static String parseIdentifier() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        if (!isAlphabetic()) {
            throw new PrsException("error not identifier");
        }

        StringBuilder identifier = new StringBuilder();

        while (isAlphabetic()) {
            identifier.append(currentCharacter);
            getNextCharacter();
        }

        return identifier.toString();
    }

    private static Object parseValue() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        Object object;

        if (Character.isDigit(currentCharacter)) {
            object = parseLiteralInteger();
        } else if (currentCharacter == '"') {
            object = parseLiteralString();
        } else if (currentCharacter == '{') {
            object = parseLiteralArray();
        } else {
            throw new PrsException("error not value");
        }

        return object;
    }

    private static int parseLiteralInteger() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        StringBuilder identifier = new StringBuilder();

        while (Character.isDigit(currentCharacter)) {
            identifier.append(currentCharacter);
            getNextCharacter();
        }

        return Integer.parseInt(identifier.toString());
    }

    private static String parseLiteralString() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        StringBuilder identifier = new StringBuilder();

        getNextCharacter();
        while (currentCharacter != '"') {
            identifier.append(currentCharacter);
            getNextCharacter();
        }
        getNextCharacter();

        return identifier.toString();
    }

    private static List<Object> parseLiteralArray() throws Exception {
        while (isSpace()) {
            getNextCharacter();
        }

        StringBuilder identifier = new StringBuilder();
        List<Object> objects = new ArrayList<>();

        getNextCharacter();
        while (currentCharacter != '}') {
            objects.add(parseValue());

            if (currentCharacter == ',') {
                getNextCharacter();
            } else if (currentCharacter != '}') {
                throw new PrsException("error not , or }");
            }
        }
        getNextCharacter();

        return objects;
    }

    private static boolean isSpace() {
        return currentCharacter == ' ' || currentCharacter == '\n' || currentCharacter == '\t' || currentCharacter == '\r';
    }

    private static boolean isAlphabetic() {
        return Character.isAlphabetic(currentCharacter) || currentCharacter == '_';
    }

    private static boolean isEOF() {
        return currentCharacter == 0;
    }
}
