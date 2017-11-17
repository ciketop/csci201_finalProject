package database.dao;

import com.sun.istack.internal.Nullable;
import database.util.QueryData;
import database.util.QueryPair;
import database.util.WhereClauseConnectType;
import util.SimplePair;

import java.util.List;

public interface GenericDAOTemplate<T> {
    String selectQueryTemplate = "SELECT $columnLabel FROM $tableName";
    String insertQueryTemplate = "INSERT INTO $tableName ($columnLabel) VALUES $values;";
    String deleteQueryTemplate = "DELETE FROM $tableName WHERE $whereClause";
    String updateQueryTemplate = "UPDATE $tableName SET $updateClause";

    List<T> queryAll(String tableName, List<String> columnLabels);

    List<T> selectQuery(
            String tableName,
            List<String> columnLabels,
            WhereClauseConnectType connectType,
            @Nullable List<QueryPair<String, QueryData>> whereClausePairs);

    int insertObjects(String tableName, List<String> columnLabels, List<T> objects);

    int updateObject(
            String tableName,
            @Nullable List<String> oldLabels,
            List<String> newLabels,
            @Nullable T oldObject,
            T newObject,
            WhereClauseConnectType connectType);

    int updateObjects(
            String tableName,
            @Nullable List<String> oldLabels,
            List<String> newLabels,
            @Nullable List<T> oldObjects,
            List<T> newObjects,
            WhereClauseConnectType connectType
    );

    int deleteObjects(String tableName, List<String> columnLabels, List<T> objects);
}
