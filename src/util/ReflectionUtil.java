package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {
    public static<T> Map<String, Field> getFieldMap(T object, List<String> fieldNames) {
        Map<String, Field> fieldMap = new HashMap<>();

        for (String fieldName : fieldNames) {
            try {
                if (fieldMap.containsKey(fieldName)) {
                    continue;
                }

                Field field = object.getClass().getDeclaredField(fieldName);
                fieldMap.put(fieldName, field);
            } catch (NoSuchFieldException ex) {
                // do nothing
                ex.printStackTrace();
            }
        }

        return fieldMap;
    }
}
