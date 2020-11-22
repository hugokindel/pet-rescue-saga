package com.g10.prs.option;

/** This is a parser for options with value passed through the cli. */
public class Parser {
    /**
     * Parse the value of an option.
     *
     * @param value The value (what comes after the '=' character in an option).
     * @param classType The class type of the option.
     * @return the value converted to the class type of the option.
     */
    public static Object parse(String value, Class<?> classType) {
        if (classType == String[].class) {
            return value.split(",");
        } else if (classType == String.class) {
            return value;
        } else if (classType == char.class) {
            return value.charAt(0);
        } else if (classType == boolean.class) {
            return !value.equals("0");
        } else if (classType == int.class) {
            return Integer.parseInt(value);
        } else if (classType == byte.class) {
            return Byte.parseByte(value);
        } else if (classType == short.class) {
            return Short.parseShort(value);
        } else if (classType == long.class) {
            return Long.parseLong(value);
        } else if (classType == float.class) {
            return Float.parseFloat(value);
        } else if (classType == double.class) {
            return Double.parseDouble(value);
        }

        return null;
    }
}
