package util;

public class SimplePair<L, R> {
    private L left;
    private R right;

    public SimplePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
