package core;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB {

    public static Connection db;
    public static final String db_name = "db";

    private static final String DEFAULT_DELIMITER = ";";
    private static final Pattern NEW_DELIMITER_PATTERN = Pattern.compile("(?:--|\\/\\/|\\#)?!DELIMITER=(.+)");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^(?:--|\\/\\/|\\#).+");

    public static void start() {
        start(false);
    }

    public static void start(boolean create) {
        try {
            db = DriverManager.getConnection("jdbc:derby:" + System.getProperty("user.home") + "/" + App.app_foldername + "/" + db_name + (create ? ";create=true" : ""));
        }

        catch (SQLException ex) {
            System.out.println("[ERROR] Database Connection exception : " + ex.getMessage());
            ex.printStackTrace();
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
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet query(String sql, Object ...vals) {
        try {
            PreparedStatement s = db.prepareStatement(sql);


            int i = 1;
            for (Object val : vals) {

                if (val instanceof Date) {
                    String str = ((Date) val).toString();
                    s.setDate(i, Date.valueOf(str));
                }

                else if (val instanceof Integer) s.setInt(i, (int) val);
                else if (val instanceof Float)   s.setFloat(i, (float) val);
                else if (val instanceof Double)  s.setDouble(i, (double) val);
                else if (val instanceof Boolean) s.setBoolean(i, (Boolean) val);
                else if (val instanceof String)  s.setString(i, (String) val);

                i++;
            }

            if (s.execute()) {
                return s.getResultSet();
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ERROR] SQL Query : " + sql);
        }

        return null;
    }
}
