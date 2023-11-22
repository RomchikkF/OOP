
public enum Grade {
    Unsatisfactory(2), Satisfactory(3), Good(4), Great(5);

    public final int value;
    Grade(int val) {
        value = val;
    }
}