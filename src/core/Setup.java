package core;

import models.Medecin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Setup {
    private static boolean under_setup = false;

    public static void start() {
        under_setup = true;
    }

    public static boolean check() {
        return (new File(System.getProperty("user.home") + "/" + App.app_foldername)).exists();
    }

    public static void setup_database() {
            new File(System.getProperty("user.home") + "/" + App.app_foldername).mkdir();

            String sql = "";

            try (InputStreamReader instream = new InputStreamReader(App.getApp().getClass().getResourceAsStream("/assets/migration.sql"));
                 BufferedReader buffer = new BufferedReader(instream)) {



                String line;
                while ((line = buffer.readLine()) != null) {
                    sql += line;
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }

            String[] commands = sql.split(";");

            DB.start(true);

            for (String cmd: commands) {
                DB.query(cmd);
            }

    }

    public static void setup_admin() {
        App.setView("AdminSetup");
    }


    public static boolean underSetup() {
        return under_setup;
    }

    public static void doneSetup() {
        under_setup = false;
        App.setView(App.entry);
    }

}
