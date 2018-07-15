package db;

import java.util.ArrayList;
import java.util.List;

/**
 * A class represent dataBase table.
 */
public class Table {

    public String name;
    public List<Row> rows;
    public List<Column> columns;
    public List<String> colNames;

    public Table(String tableName, List<Column> cols) {
        name = tableName;
        columns = cols;
        rows = new ArrayList<>();
        colNames = new ArrayList<>();

        for (int i=0; i < cols.get(0).size; i++) {
            rows.add(new Row());
        }

        for (int i=0; i < columnsNum(); i++) {
            Column col = cols.get(i);
            colNames.add(col.name);
            expendItem(rows, col);
        }
    }

    public void addRow(Row r) {
        rows.add(r);
        //need add col after row
        expendItem(columns, r);
    }

    public Column[] getColumns() {
        return (Column[]) this.columns.toArray();
    }

    public Row[] getRows() {
        return (Row[]) this.rows.toArray();
    }

    public Row getRowByIndex(int i) {
        if (i >= rowsNum()) {
            return null;
        }
        return rows.get(i);
    }

    public Column getColByIndex(int i) {
        if (i >= columnsNum()) {
            return null;
        }
        return columns.get(i);
    }

    public int rowsNum() {
        return rows.size();
    }

    public int columnsNum() {
        return columns.size();
    }


    /** insert items from a col into each row */
    private void expendItem(List<Row> rows, Column col) {
        for (int i=0; i<rows.size(); i++) {
            rows.get(i).add(col.items.get(i));
        }
    }

    /** insert items from a row into each col */
    private void expendItem(List<Column> cols, Row r) {
        for (int i=0; i<cols.size(); i++) {
            cols.get(i).add(r.items.get(i));
        }
    }

    private String changeLine(String str){
        str = str.substring(0,str.length() -1);
        str+="\n";
        return str;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i=0; i<columnsNum(); i++){
            String colName = colNames.get(i);
            String type = columns.get(i).type;
            result+=(colName+" "+type+",");
        }
        result = changeLine(result);
        for(Row r : rows){
            for(Value item : r.items){
                result+=(item.toString() + ",");
            }
            result = changeLine(result);
        }
        result = result.substring(0,result.length() -1);
        return result;
    }

}
