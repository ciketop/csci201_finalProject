package util;

import database.util.QueryData;
import database.util.QueryPair;
import database.util.QueryType;
import database.util.WhereClauseConnectType;

import java.util.List;

public class StringParse {
//    public static String generateQueryString(
//            QueryType type,
//            String queryTemplate,
//            String tableName,
//            List<String> columnLabels,
//            String whereClauseString
//    ) {
//        String result = "";
//
//        switch (type) {
//            case SELECT:
//                result = generateSelectQueryString(queryTemplate, tableName, columnLabels, whereClauseString);
//                break;
//            default:
//                break;
//        }
//
//        return result;
//    }

    public static String generateQueryString(
            String queryTemplate,
            String tableName,
            List<String> columnLabels,
            String whereClauseString
    ) {
        String result = queryTemplate.replace("$tableName", tableName);
//                result = queryTemplate.replace("$columnLabel", tableName);

        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        for (String columnLabel : columnLabels) {
            stringBuilder.append(prefix);

            stringBuilder.append(columnLabel);

            prefix = ", ";
        }
        result = result.replace("$columnLabel", stringBuilder.toString());

        if (whereClauseString != null && !whereClauseString.isEmpty()) {
            result += " WHERE " + whereClauseString;
        }

        return result;
    }

    public static String generateSelectQueryString(
            String queryTemplate,
            String tableName,
            List<String> columnLabels,
            String whereClauseString
    ) {
        return generateQueryString(queryTemplate, tableName, columnLabels, whereClauseString);
    }

    public static String generateInsertQueryString(
            String queryTemplate,
            String tableName,
            List<String> columnLabels,
            int numRow
    ) {
        String result = generateQueryString(queryTemplate, tableName, columnLabels, null);

        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < columnLabels.size(); j++) {
            if (j > 0)
                stringBuilder.append(", ");

            stringBuilder.append("?");
        }
        String rowString = stringBuilder.toString();

        // reset string builder
        stringBuilder.setLength(0);

        for (int i = 0; i < numRow; ++i) {
            if (i > 0)
                stringBuilder.append(",\n");

            stringBuilder.append("(");
            stringBuilder.append(rowString);
            stringBuilder.append(")");
        }

        return result.replace("$values", stringBuilder.toString());
    }

    public static String generateUpdateQueryString(
            String queryTemplate,
            String tableName,
            List<String> columnLabels,
            String updateClauseString,
            String whereClauseString
    ) {
        String result = generateQueryString(queryTemplate, tableName, columnLabels, whereClauseString);
        return result.replace("$updateClause", updateClauseString);
    }

    public static String generateDeleteQueryString(
            String queryTemplate,
            String tableName,
            List<String> columnLabels,
            String whereClauseString
    ) {
        String result = generateQueryString(queryTemplate, tableName, columnLabels, null);


        return result.replace("$whereClause", whereClauseString);
    }

    public static String generateWhereClause(
            WhereClauseConnectType connectType,  // are clauses connected by OR or AND
            List<QueryPair<String, QueryData>> whereClausePairs
    ) {
        if (whereClausePairs == null || whereClausePairs.isEmpty()) {
            return null;
        }

        // clear string builder
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";

        switch (connectType) {
            case OR:
                // clause are connected by OR
                prefix += "OR ";
                break;
            case AND:
                // clause are connected by AND
                prefix += "AND ";
                break;
            default:
                return null;
        }

        int count = 0;
        for (QueryPair<String, QueryData> pair : whereClausePairs) {
            if (count > 0)
                stringBuilder.append(prefix);

            stringBuilder.append(pair.getLeft());
            stringBuilder.append(" = ");

            if (!pair.isRightVariable())
                stringBuilder.append(pair.getRightAsString());
            else
                stringBuilder.append("?");

            stringBuilder.append(" ");
            ++count;
        }

//        return whereClauseTemplate.replace("$whereClause", stringBuilder.toString());
        return stringBuilder.toString();
    }


    public static String generateUpdateClause(
            List<QueryPair<String, QueryData>> updateClausePairs
    ) {
        if (updateClausePairs == null || updateClausePairs.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();

        int count = 0;
        for (QueryPair<String, QueryData> pair : updateClausePairs) {
            if (count > 0)
                stringBuilder.append(", ");

            stringBuilder.append(pair.getLeft());
            stringBuilder.append(" = ");

            if (!pair.isRightVariable())
                stringBuilder.append(pair.getRightAsString());
            else
                stringBuilder.append("?");
            ++count;
        }

        return stringBuilder.toString();
    }

    public static String combineWhereClauses(
            WhereClauseConnectType connectType,  // are clauses connected by OR or AND
            List<String> whereClauses
    ) {

        if (whereClauses == null || whereClauses.isEmpty()) {
            return null;
        }

        // clear string builder
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";

        switch (connectType) {
            case OR:
                // clause are connected by OR
                prefix += "OR ";
                break;
            case AND:
                // clause are connected by AND
                prefix += "AND ";
                break;
            default:
                return null;
        }

        int count = 0;
        for (String clause : whereClauses) {
            if (count > 0)
                stringBuilder.append(prefix);

            stringBuilder.append("(");
            stringBuilder.append(clause);
            stringBuilder.append(") ");
            ++count;
        }

        return stringBuilder.toString();
    }
}
