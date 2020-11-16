package com.g10.prs.core.resource.njson;

import com.g10.prs.core.options.type.Runnable;
import com.g10.prs.core.type.PrsException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.Class;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class NJson {
    public static <T> T deserialize(String filePath, Class<T> classType) throws PrsException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, IOException, NJSonCannotParseException {
        if (!classType.isAnnotationPresent(NJsonSerializable.class)) {
            throw new PrsException("Not a serializable class!");
        }

        Map<String, Object> njson = new NJsonReader().parse(filePath);
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
}
