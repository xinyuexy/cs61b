package db;

/**
 * A class store different type.
 */
public class Value {
    public Object value;
    public String type;

    public Value() {
        this.value = null;
    }

    public Value(int value) {
        this.value = value;
        type = "int";
    }

    public Value(String value) {
        this.value = value;
        type = "string";
    }

    public Value(float value) {
        this.value = value;
        type = "float";
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static void main(String[] args) {
        Value v0 = new Value("New York");
        Value v1 = new Value(2015);
        Value v2 = new Value((float) 0.3);
        Value[] actual = new Value[]{v0, v1, v2};
        System.out.println(actual[1].value);
    }
}
