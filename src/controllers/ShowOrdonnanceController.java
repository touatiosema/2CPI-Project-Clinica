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


public class ShowOrdonnanceController extends Controller {

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
    HBox ordonnance_list;

    FicheDeConsultation consultation;
    Medecin medecin;
    Patient patient;

    public ShowOrdonnanceController() {
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

        ArrayList<LigneEnOrdonnance> ordonnance = (new Ordonnance(consultation.getId())).getOrdonnances();

        VBox names = new VBox();
        HBox.setHgrow(names, Priority.ALWAYS);

        VBox dozes = new VBox();
        HBox.setHgrow(dozes, Priority.NEVER);

        VBox detailss = new VBox();
        HBox.setHgrow(detailss, Priority.NEVER);

        ordonnance_list.getChildren().addAll(names, dozes, detailss);

        for (LigneEnOrdonnance med_line : ordonnance) {

            Label name = new Label(med_line.getMedicament().getNomCommercial().toUpperCase());
            name.setPadding(new Insets(10, 10, 10, 10));
            names.getChildren().add(name);

            Label doze = new Label(med_line.getDose().toUpperCase());
            doze.setPadding(new Insets(10, 10, 10, 10));
            dozes.getChildren().add(doze);

            Label details = new Label(med_line.getDetails().toUpperCase());
            details.setPadding(new Insets(10, 10, 10, 10));
            detailss.getChildren().add(details);
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
