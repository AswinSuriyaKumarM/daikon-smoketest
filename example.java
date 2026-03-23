public class example {
    private int count;

    public example() {
        count = 0;
    }

    public void inc() {
        count = count + 1;
    }

    public void add(int x) {
        count = count + x;
    }

    public int getCount() {
        return count;
    }

    public boolean isNonNegative() {
        return count >= 0;
    }
}