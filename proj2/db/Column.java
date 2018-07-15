package db;

import java.util.ArrayList;
import java.util.List;

/**
 * A class represent one column of table.
 */
public class Column {
    public String name;
    public List<Value> items;
    public int size;
    public String type;

    public Column(String n, String ty) {
        name = n;
        items = new ArrayList<>();
        size = 0;
        type = ty;
    }

    public void add(Value item) {
        items.add(item);
        size++;
    }
}
