package controllers;

import core.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Doctor;

import java.util.ArrayList;

public class Doctors extends Controller {
    @FXML
    private ListView doctors;

    @Override
    public void init() {
        ObservableList<String> items = FXCollections.observableArrayList();

        ArrayList<Doctor> docs = Doctor.all();
        for (Doctor doctor: docs) {
            items.add(doctor.lastname);
        }

        doctors.setItems(items);
    }

    public void viewPatients() {
        App.setView("Patients");
    }
}

