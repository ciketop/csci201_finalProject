package database.util;

import util.SimplePair;

public class QueryPair<L extends String, R extends QueryData> extends SimplePair<L, R> {

    public QueryPair(L left, R right) {
        super(left, right);
    }

    public String getRightAsString() {
        return super.getRight().getData();
    }

    public boolean isRightVariable() {
        return super.getRight().isVariable();
    }
}
