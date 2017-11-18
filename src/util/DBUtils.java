package util;

import database.util.QueryData;
import database.util.QueryPair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBUtils {
    public static<T> List<QueryPair<String, QueryData>> getQueryPairList(List<String> columnLabels, T object) {
        if (columnLabels == null || columnLabels.isEmpty() || object == null) {
            return null;
        }

        List<QueryPair<String, QueryData>> queryPairList = new ArrayList<>();

        Map<String, Field> fieldMap = ReflectionUtil.getFieldMap(object, columnLabels);
        for (String columnLabel : columnLabels) {
            Field field = fieldMap.get(columnLabel);
            field.setAccessible(true);

            try {
                queryPairList.add(
                        new QueryPair<>(
                                columnLabel,
                                new QueryData(field.get(object).toString())
                        )
                );
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        return queryPairList;
    }
}
