package core;
import java.sql.*;

public class DB {

    private static Connection db;
    private static final String db_name = "db";

    public static void start() {
        try {
            db = DriverManager.getConnection("jdbc:derby:" + db_name);
        }

        catch (SQLException ex) {
            System.out.println("[ERROR] Database Connection exception : " + ex.getMessage());
        }
    }

    public static void stop() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            db.close();
        }

        catch (SQLException ex) {

            if (!((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
                System.out.println("[ERROR] Database shutdown exception : " + ex.getMessage());
            }

        }
    }

    public static ResultSet query(String sql) {
        try {
            Statement s = db.createStatement();

            if (s.execute(sql)) {
                return s.getResultSet();
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQL Query : " + sql);
        }

        return null;
    }

    public static ResultSet query(String sql, Object ...vals) {
        try {
            PreparedStatement s = db.prepareStatement(sql);

            int i = 1;
            for (Object val : vals) {
                if (val instanceof Integer) s.setInt(i, (int) val);
                if (val instanceof Float) s.setFloat(i, (float) val);
                if (val instanceof Double) s.setDouble(i, (double) val);
                if (val instanceof String) s.setString(i, (String) val);
                i++;
            }

            if (s.execute()) {
                return s.getResultSet();
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQL Query : " + sql);
        }

        return null;
    }
}
