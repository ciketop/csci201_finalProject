package database.dao;

import database.util.QueryData;
import database.util.QueryPair;
import database.util.QueryType;
import util.*;
import database.util.WhereClauseConnectType;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static database.util.WhereClauseConnectType.NO_WHERE_CLAUSE;

public class GenericDAO<T> implements GenericDAOTemplate<T> {
    private String dbName;
    private Factory<T> factory;

    private GenericDAO(Factory<T> factory) {
        this.factory = factory;

        this.dbName = "LiveClass";
    }

    public GenericDAO(Factory<T> factory, String dbName) {
        this(factory);

        this.dbName = dbName;
    }

    @Override
    public List<T> queryAll(String tableName, List<String> columnLabels) {
        return selectQuery(tableName, columnLabels, NO_WHERE_CLAUSE, null);
    }

    @Override
    public List<T> selectQuery(
            String tableName,
            List<String> columnLabels,
            WhereClauseConnectType connectType,
            List<QueryPair<String, QueryData>> whereClausePairs
    ) {
        List<T> result = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection(dbName);
//            System.out.println(connection == null);

            String queryString = StringParse.generateSelectQueryString(
                    selectQueryTemplate,
                    tableName,
                    columnLabels,
                    StringParse.generateWhereClause(
                            connectType,
                            whereClausePairs)
            );

//            System.out.println("got here");
            System.out.println(queryString);
//            System.out.println("gothere 2");
            
            ps = connection.prepareStatement(queryString);
            if (connectType != NO_WHERE_CLAUSE) {
                int psParamIdx = 0;
                for (QueryPair<String, QueryData> pair : whereClausePairs) {
                    // assign values to prepared statement
                    if (pair.isRightVariable()) {
                        ps.setString(++psParamIdx, pair.getRight().getData());
                    }
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                // get new instance of T
                T dataObject = factory.factory();
                Map<String, Field> fieldMap = ReflectionUtil.getFieldMap(dataObject, columnLabels);

                for (String columnLabel : columnLabels) {
//                    System.out.println(columnLabel + " -> " + fieldMap.get(columnLabel));
                    Field field = fieldMap.get(columnLabel);
                    field.setAccessible(true);

                    System.out.println(columnLabel + rs.getObject(columnLabel));

                    try {
                        field.set(dataObject, rs.getObject(columnLabel));
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }

                result.add(dataObject);
            }

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public int insertObjects(String tableName, List<String> columnLabels, List<T> objects) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection(dbName);

            String queryString = StringParse.generateInsertQueryString(
                    insertQueryTemplate,
                    tableName,
                    columnLabels,
                    objects.size()
            );

            System.out.println(queryString);

            ps = connection.prepareStatement(queryString);

            int psParamIdx = 0;
            for (T dataObject : objects) {
                Map<String, Field> fieldMap = ReflectionUtil.getFieldMap(dataObject, columnLabels);
                for (String columnLabel : columnLabels) {
                    Field field = fieldMap.get(columnLabel);
                    field.setAccessible(true);

                    try {
                    		System.out.println("GenericDAO: " + field.get(dataObject).toString());
                        ps.setString(++psParamIdx, field.get(dataObject).toString());
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(null, ps, null, connection);
        }
    }

    @Override
    public int updateObject(
            String tableName,
            List<String> oldLabels,
            List<String> newLabels,
            T oldObject,
            T newObject,
            WhereClauseConnectType connectType
    ) {
        Connection connection = null;
        PreparedStatement ps = null;

        List<QueryPair<String, QueryData>> updateClausePairs = DBUtils.getQueryPairList(newLabels, newObject);
        List<QueryPair<String, QueryData>> whereClausePairs = null;

        if (oldLabels == null) {
            oldLabels = newLabels;
        }

        if (connectType != NO_WHERE_CLAUSE) {
            whereClausePairs = DBUtils.getQueryPairList(oldLabels, oldObject);
        }

        try {
            connection = ConnectionFactory.getConnection(dbName);

            String queryString = StringParse.generateUpdateQueryString(
                    updateQueryTemplate,
                    tableName,
                    newLabels,
                    StringParse.generateUpdateClause(updateClausePairs),
                    StringParse.generateWhereClause(
                            connectType,
                            whereClausePairs)
            );

            System.out.println(queryString);

            ps = connection.prepareStatement(queryString);
            int psParamIdx = 0;
            for (QueryPair<String, QueryData> pair : updateClausePairs) {
                ps.setString(++psParamIdx, pair.getRightAsString());
            }

            if (whereClausePairs != null) {
                for (QueryPair<String, QueryData> pair : whereClausePairs) {
                    ps.setString(++psParamIdx, pair.getRightAsString());
                }
            }

            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(null, ps, null, connection);
        }
    }

    @Override
    public int updateObjects(
            String tableName,
            List<String> oldLabels,
            List<String> newLabels,
            List<T> oldObjects,
            List<T> newObjects,
            WhereClauseConnectType connectType
    ) {
        int result = 0;

        for (int i = 0; i < newObjects.size(); i++) {
            if (oldObjects == null)
                result += updateObject(tableName, oldLabels, newLabels, null, newObjects.get(i), connectType);
            else
                result += updateObject(tableName, oldLabels, newLabels, oldObjects.get(i), newObjects.get(i), connectType);
        }

        return result;
    }

    @Override
    public int deleteObjects(String tableName, List<String> columnLabels, List<T> objects) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<QueryPair<String, QueryData>> whereClausePairs = new ArrayList<>();

        StringBuilder whereClauseBuilder = new StringBuilder();
        List<String> whereClauses = new ArrayList<>();

        int count = 0;
        for (T dataObject : objects) {
            Map<String, Field> fieldMap = ReflectionUtil.getFieldMap(dataObject, columnLabels);
            // pair for this object
            List<QueryPair<String, QueryData>> dataObjectPairs = new ArrayList<>();

            for (String columnLabel : columnLabels) {
                Field field = fieldMap.get(columnLabel);
                field.setAccessible(true);

                QueryPair<String, QueryData> pair = null;
                try {
                    pair = new QueryPair<>(columnLabel, new QueryData(field.get(dataObject).toString()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }

                if (pair != null) {
                    dataObjectPairs.add(pair);
                    whereClausePairs.add(pair);
                }
            }

            if (!dataObjectPairs.isEmpty()) {
                whereClauses.add(StringParse.generateWhereClause(WhereClauseConnectType.AND, dataObjectPairs));
            }
        }

        try {
            connection = ConnectionFactory.getConnection(dbName);

            String queryString = StringParse.generateDeleteQueryString(
                    deleteQueryTemplate,
                    tableName,
                    columnLabels,
                    StringParse.combineWhereClauses(
                            WhereClauseConnectType.OR,
                            whereClauses
                    )
            );

            System.out.println(queryString);

            ps = connection.prepareStatement(queryString);
            int psParamIdx = 0;
            for (QueryPair pair : whereClausePairs) {
                ps.setString(++psParamIdx, pair.getRightAsString());
            }

            return ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(null, ps, null, connection);
        }
    }
}
