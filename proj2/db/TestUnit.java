package db;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by 11519 on 2018/7/15.
 */
public class TestUnit {
    @Test
    public void testGetRow() {
        String result = Database.loadTable("t1");
        if (result.equals("")) {
            Database db = new Database();
            Table tb = db.getTable("t1");
            Column[] columns = tb.getColumns();

            Value v0 = new Value(2);
            Value v1 = new Value(5);
            Value[] values = new Value[]{v0, v1};

            Row actual = tb.getRowByIndex(0);
            Row expected = new Row(values);
            assertEquals(expected, actual);
        }
    }
}
