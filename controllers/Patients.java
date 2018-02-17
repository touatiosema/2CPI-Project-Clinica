package controllers;

import core.App;
import core.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patients extends Controller {
    @FXML
    private ListView patients;

    @Override
    public void init() {
        try {
            // Get doctors from database
            ResultSet q = DB.query("SELECT * FROM patients");

            ObservableList<String> items = FXCollections.observableArrayList();

            // For each code
            while (q.next()) {
                items.add(q.getString("lastname"));
            }

            patients.setItems(items);
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQL Error while fetching patients list : " + e.getMessage());
        }
    }

    public void viewDoctors() {
        App.setView("Doctors");
    }
}

