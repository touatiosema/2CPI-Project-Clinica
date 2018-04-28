package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Config {

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String def) {
        ResultSet q = DB.query("SELECT config_value FROM config WHERE config_key = ?", key);

        try {
            if (q.next()) {
                return q.getString("config_value");
            }

            else {
                return def;
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] Could not get config");
        }

        return def;
    }

    public static void set(String key, String value) {
        if (Config.get(key) == null) DB.query("INSERT INTO CONFIG(config_key, config_value) VALUES(?, ?)", key, value);
        else DB.query("UPDATE CONFIG SET config_value = ? WHERE config_key = ?", value, key);
    }
}
