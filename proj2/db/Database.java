package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
    private static String DATAADDRESS = "examples/";
    private static final String REST  = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND   = "\\s+and\\s+";

    private static final String INTEGER = "\\s*(\\d+)\\s*";
    private static final String STRING = "\'.*\'";
    private static final String FLOAT = "^\\s*([+-]?\\d*\\.\\d*)\\s*$";
    private static final Pattern INTEGER_TYPE = Pattern.compile(INTEGER);
    private static final Pattern STRING_TYPE = Pattern.compile(STRING);
    private static final Pattern FLOAT_TYPE = Pattern.compile(FLOAT);

    private static Map<String,Table> tableMap;

    public Database() {
        // YOUR CODE HERE
        tableMap = new HashMap<>();
    }

    public String transact(String query) {
        String result = Parse.eval(query);
        return result;
    }

    public Table getTable(String name) {
        if (tableMap.containsKey(name)) {
            return tableMap.get(name);
        }
        return null;
    }

    static Table createNewTable(String name, String[] colsStr) throws Exception {
        List<Column> cols = new ArrayList<>();
        for (String str: colsStr) {
            String[] args = str.split("\\s+");
            Column col;
            int argsLen = args.length;
            if (argsLen != 2) {
                throw new Exception(String.format(
                        "Wrong arguments number for %s, except 2, get %d",args[0],argsLen));
            } else {
                String colName = args[0];
                String colType = args[1];
                if (colType.equals("int")) {
                    col = new Column(colName, "int");
                } else if(colType.equals("string")) {
                    col = new Column(colName, "string");
                } else if (colType.equals("float")) {
                    col = new Column(colName, "float");
                } else {
                    throw new Exception(String.format(String.format("unknown class type: %s", colType)));
                }
            }
            cols.add(col);
        }

        Table newTable = new Table(name, cols);
        tableMap.put(name, newTable);
        return newTable;
    }

    static Value strToObj(String str) {
        Matcher m;
        if((m=INTEGER_TYPE.matcher(str)).matches()){
            return new Value(Integer.parseInt(m.group(1)));
        } else if((m=FLOAT_TYPE.matcher(str)).matches()){
            return new Value(Float.parseFloat(m.group(1)));
        } else{
            return new Value(str);
        }
    }

    /** convert row line string to row */
    private static Row rowStrToRow(String line) {
        Row r = new Row();
        String[] rowItemsStr = line.split(",");
        for (String itemStr: rowItemsStr) {
            r.add(strToObj(itemStr));
        }
        return r;
    }

    static String loadTable(String name) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(DATAADDRESS + name + ".tbl"));
            String line;
            line = br.readLine();
            // get column name and type from first line
            String[] colsStr = line.split(",");
            Table newTable;
            try {
                newTable = createNewTable(name, colsStr);
            } catch (Exception e) {
                return "" + e;
            }

            //read data
            while ((line = br.readLine()) != null) {
                Row r = rowStrToRow(line);
                try {
                    newTable.addRow(r);
                } catch (Exception e) {
                    return "" + e;
                }
            }
            return "";

        } catch (IOException e) {
            return String.format("ERROR: TBL file not found: %s.tbl", name);
        }
    }

    static String printTable(String name) {
        if (tableMap.containsKey(name)) {
            return tableMap.get(name).toString();
        } else {
            return "ERROR: Table not found: " + name;
        }
    }

}
