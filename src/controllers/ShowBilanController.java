package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import models.Ordonnance;
import utils.Print;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowBilanController extends Controller {

    @FXML
    JFXButton printPreviewItem;
    @FXML AnchorPane anchorPane;
    @FXML Label labelMedecin;
    @FXML Label labelCabinet;
    @FXML Label labelLieu;
    @FXML Label labelDate;
    @FXML Label labelAge;
    @FXML Label labelPatient;
    @FXML
    VBox bilan_list;

    FicheDeConsultation consultation;
    Medecin medecin;
    Patient patient;

    public ShowBilanController() {
        min_width = width = max_width = 500;
        min_height = height = max_height = 670;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        consultation = new FicheDeConsultation(id);

        medecin = new Medecin(consultation.getIdMedecin());
        patient = new Patient(consultation.getIdPatient());

        labelAge.setText(patient.getAge() + " ans");
        labelDate.setText("Le " + consultation.getDate().toString() + " " + consultation.getTime().toString());
        labelMedecin.setText("Dr. " + medecin.getFullName());
        labelCabinet.setText(Config.get("cabinet_name"));
        labelLieu.setText(Config.get("cabinet_adresse"));
        labelPatient.setText(patient.getFullName());

        ArrayList<Examen> bilan = Bilan.getBilanById(consultation.getId()).getFicheBilan();

        for (Examen line : bilan) {
            Label name = new Label(line.getName().toUpperCase());
            name.setPadding(new Insets(10, 10, 10, 10));
            bilan_list.getChildren().add(name);
        }


        Stage win = getWindow();
        printPreviewItem.setOnAction(event -> {
            Print.preview(anchorPane);
            win.close();
        });


    }

    public void print() {
        Print.print(getWindow(), anchorPane);
    }
}
