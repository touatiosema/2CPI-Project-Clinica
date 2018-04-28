package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Config;
import models.FicheDeConsultation;
import models.Medecin;
import models.Patient;
import utils.Print;

import java.util.HashMap;


public class ShowLettreReorientationController extends Controller {

    @FXML
    JFXButton printPreviewItem;
    @FXML AnchorPane anchorPane;
    @FXML Label textLettre;
    @FXML Label labelMedecin;
    @FXML Label labelCabinet;
    @FXML Label labelLieu;
    @FXML Label labelDate;
    @FXML Label labelAge;
    @FXML Label labelPatient;

    FicheDeConsultation consultation;
    Medecin medecin;
    Patient patient;

    public ShowLettreReorientationController() {
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
        labelCabinet.setText(Config.get("cabinet_name" + ""));
        labelLieu.setText(Config.get("cabinet_adresse"));
        labelPatient.setText(patient.getFullName());
        textLettre.setText(consultation.getLettreDOrientation());

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
