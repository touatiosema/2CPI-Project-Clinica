package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Doctor {
    public int id;
    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public String phone;

    public Doctor() {

    }

    public Doctor(int id, String username, String password, String firstname, String lastname, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public static ArrayList<Doctor> all() {
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();

        try {
            // Get doctors from database
            ResultSet q = DB.query("SELECT * FROM doctors ORDER BY id");

            // For each code
            while (q.next()) {
                doctors.add(new Doctor(
                    q.getInt("id"),
                    q.getString("username"),
                    q.getString("password"),
                    q.getString("firstname"),
                    q.getString("lastname"),
                    q.getString("phone")
                ));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQL Error while fetching all doctors list : " + e.getMessage());
        }

        return doctors;
    }
}
