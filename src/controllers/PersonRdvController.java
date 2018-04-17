package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import models.RDV;

public class PersonRdvController extends Controller {

    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker heure;

    @FXML
    private TextArea description;


    public PersonRdvController() {
        title = "+ Rendez-vous personnel";
        min_height = height = max_height = 515;
        min_width = width=max_width=365;


    }


    @FXML
    public void SaveRdv() {

        if (java.sql.Date.valueOf(date.getValue()).toString().length() != 0 &&
                description.getText().length() != 0 &&
                java.sql.Time.valueOf(heure.getValue()).toString().length() != 0 ){
            RDV personel = new RDV();
            personel.setDate(java.sql.Date.valueOf(date.getValue()));
            personel.setDescription(description.getText());
            personel.setHeure(java.sql.Time.valueOf(heure.getValue()));
            personel.setType("P");
            personel.setPatient("");


            personel.save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Terminer");
            alert.setHeaderText(null);
            alert.setContentText("Rendez vous ajouté ! ");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention ! ");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez compléter les champs nécessaire !  ");
            alert.showAndWait();
        }








         }







        }


