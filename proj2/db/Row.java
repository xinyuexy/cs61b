package db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class represent one row of table.
 */
public class Row {
    public List<Value> items;
    public int size;

    public Row() {
        items = new ArrayList<>();
        size = 0;
    }

    public Row(Value[] values) {
        items = Arrays.asList(values);
        size = items.size();
    }

    public Row(List<Value> values) {
        items = values;
        size = items.size();
    }

    public void add(Value item) {
        items.add(item);
        size++;
    }
}
