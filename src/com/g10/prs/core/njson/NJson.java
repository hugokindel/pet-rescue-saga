package com.g10.prs.core.njson;

import com.g10.prs.core.options.Runnable;
import com.g10.prs.core.PrsException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.Class;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/** Contains utility functions to deserialize and serialize NJson data format files. */
public class NJson {
    /**
     * Deserialize a NJson file.
     *
     * @param filePath The file path to use.
     * @param classType The Class to create.
     * @param <T> The type to return.
     *
     * @return the class created of type T.
     */
    public static <T> T deserialize(String filePath, Class<T> classType) throws PrsException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, IOException, NJSonCannotParseException {
        checkSerializable(classType);

        Map<String, Object> njson = new NJsonReader(filePath).readMap();
        T object = classType.getConstructor().newInstance();

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(Runnable.class.getDeclaredFields()));
        fields.addAll(Arrays.asList(classType.getDeclaredFields()));
        fields.removeIf(field -> !field.isAnnotationPresent(NJsonSerializable.class));

        for (Field field : fields) {
            NJsonSerializable serializable = field.getAnnotation(NJsonSerializable.class);
            String key = serializable.path().isEmpty() ? field.getName() : serializable.path();
            String[] path = key.split("\\.");
            Map<String, Object> mapToSearch = njson;

            if (path.length > 1) {
                for (int i = 0; i < path.length - 1; i++) {
                    if (mapToSearch.get(path[i]) instanceof Map) {
                        mapToSearch = (Map<String, Object>)mapToSearch.get(path[i]);
                    } else {
                        throw new PrsException("Wrong path!");
                    }
                }
            }

            if (!mapToSearch.containsKey(path[path.length - 1])) {
                throw new PrsException("Missing key!");
            }

            field.setAccessible(true);
            field.set(object, mapToSearch.get(path[path.length - 1]));
        }

        return object;
    }

    /**
     * Serialize a NJson file.
     *
     * @param filePath The file path to use.
     * @param object The object to serialize.
     * @param <T> The type of the object.
     */
    public static <T> void serialize(String filePath, T object) throws PrsException, IllegalAccessException, FileNotFoundException {
        Class<T> classType = (Class<T>) object.getClass();
        checkSerializable(classType);

        Map<String, Object> njson = new LinkedHashMap<>();

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(Runnable.class.getDeclaredFields()));
        fields.addAll(Arrays.asList(classType.getDeclaredFields()));
        fields.removeIf(field -> !field.isAnnotationPresent(NJsonSerializable.class));

        for (Field field : fields) {
            NJsonSerializable serializable = field.getAnnotation(NJsonSerializable.class);
            String key = serializable.path().isEmpty() ? field.getName() : serializable.path();
            String[] path = key.split("\\.");
            field.setAccessible(true);
            addToMap(njson, path, field.get(object));
        }

        NJsonWriter.write(filePath, njson);
    }

    /**
     * Add an object to a map.
     *
     * @param map The map to insert in.
     * @param path The path to search in.
     * @param value The value to add.
     */
    private static void addToMap(Map<String, Object> map, String[] path, Object value) {
        if (path.length == 1) {
            map.put(path[0], value);
            return;
        } else if (!map.containsKey(path[0])) {
            addToMap(map, new String[] {path[0]}, new LinkedHashMap<String, Object>());
        }

        String[] newPath = new String[path.length - 1];
        System.arraycopy(path, 1, newPath, 0, path.length - 1);
        addToMap((Map<String, Object>) map.get(path[0]), newPath, value);
    }

    /**
     * Check if a class is serializable.
     *
     * @param classType The Java Class to use.
     * @param <T> The class's type.
     */
    public static <T> void checkSerializable(Class<T> classType) throws PrsException {
        if (!classType.isAnnotationPresent(NJsonSerializable.class)) {
            throw new PrsException("Not a serializable class!");
        }
    }
}
