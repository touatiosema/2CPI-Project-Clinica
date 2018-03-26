package core;

import models.Medecin;

import java.io.File;
import java.io.IOException;
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
        try {
            new File(System.getProperty("user.home") + "/" + App.app_foldername).mkdir();
            String sql = new String(Files.readAllBytes(Paths.get("src/assets/migration.sql")));
            String[] commands = sql.split(";");

            DB.start(true);

            for (String cmd: commands) {
                DB.query(cmd);
            }
        }

        catch (IOException e) {
            System.out.println("Setup database failed.. opening sql file");
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
