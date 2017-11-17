package database.util;

public class QueryData {
    private String data;
    private boolean isVariable;

    public QueryData(String data) {
        this.data = data;
        this.isVariable = true;
    }

    public QueryData(String data, boolean isVariable) {
        this.data = data;
        this.isVariable = isVariable;
    }

    public String getData() {
        return data;
    }

    public boolean isVariable() {
        return isVariable;
    }
}
