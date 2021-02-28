import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
    }

    public void extractData(ResultSet rs) {
        try {
            Object item = rs.getObject(columnName);
            data.add(item);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void debug() {
        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
        for (var d : data) {
            System.out.println(d);
        }
    }
}
