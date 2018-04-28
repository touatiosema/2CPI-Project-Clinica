package controllers;

import com.jfoenix.controls.JFXComboBox;
import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.FicheDeConsultation;
import models.Patient;
import models.Personne;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class EditPatientController extends Controller {
    @FXML
    TextField nom_field;

    @FXML
    TextField prenom_field;

    @FXML
    DatePicker birthdate_field;

    @FXML
    TextField adresse_field;

    @FXML
    TextField telephone_field;

    @FXML
    TextField profession_field;

    @FXML
    TextField lieuTravail_field;

    @FXML
    JFXComboBox groupage_field;

    @FXML
    TextField taille_field;

    @FXML
    TextArea synopsis_area;

    @FXML
    ListView consultations_list;

    @FXML
    Button save_btn;

    private Patient patient;
    private ArrayList<FicheDeConsultation> consultations;

    PatientsController main_window;

    public EditPatientController() {
        min_width = width = max_width = 500;
        min_height = height = max_height = 560;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        main_window = (PatientsController) args.get("main_window");

        groupage_field.getItems().addAll("B+","B-", "A+", "A-", "AB+", "AB-", "O+", "O-");
        groupage_field.setValue("AB+");

        patient = new Patient(id);

        nom_field.setText(patient.getNom());
        prenom_field.setText(patient.getPrenom());
        birthdate_field.setValue(patient.getDateDeNaissance().toLocalDate());
        adresse_field.setText(patient.getAddress());
        telephone_field.setText(patient.getTelephone());
        profession_field.setText(patient.getProfession());
        lieuTravail_field.setText(patient.getLieuDeTravail());
        groupage_field.setValue(patient.getGroupage());
        taille_field.setText(((Integer)patient.getTaille()).toString());
        synopsis_area.setText(patient.getSynopsis());

        consultations = FicheDeConsultation.getConsultations(id);
        for (FicheDeConsultation consultation : consultations) {
            consultations_list.getItems().add(consultation.getDate().toString() + " " + consultation.getTime().toString());
        }


        consultations_list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int i  = (int) consultations_list.getSelectionModel().getSelectedIndices().toArray()[0];
                System.out.println("Consultation id : " + i);
            }
        });

        save_btn.setOnAction((e) -> {

            if (nom_field.getText().equals("")) {
                alert("Nom invalide");
                return;
            }

            if (prenom_field.getText().equals("")) {
                alert("Prenom invalide");
                return;
            }

            if (adresse_field.getText().equals("")) {
                alert("Adresse invalide");
                return;
            }

            if (telephone_field.getText().equals("")) {
                alert("Telephone invalide");
                return;
            }

            if (profession_field.getText().equals("")) {
                alert("Profession invalide");
                return;
            }

            if (lieuTravail_field.getText().equals("")) {
                alert("Lieu de travail invalide");
                return;
            }

            if (taille_field.getText().equals("")) {
                alert("Taille invalide");
                return;
            }

            try {
                Integer.parseInt(taille_field.getText());
            }

            catch (Exception o) {
                alert("Taille invalide");
                return;
            }

            if (birthdate_field.getValue() == null) {
                alert("Date de naissance invalide");
                return;
            }

            Personne p = new Personne();
            p.setId(patient.getId_personne());
            p.setNom(nom_field.getText());
            p.setPrenom(prenom_field.getText());
            p.setDateDeNaissance(Date.valueOf(birthdate_field.getValue()));
            p.setAddress(adresse_field.getText());
            p.setTelephone(telephone_field.getText());
            p.setGenre(patient.getGenre());
            p.save();


            patient.setProfession(profession_field.getText());
            patient.setLieuDeTravail(lieuTravail_field.getText());
            patient.setGroupage(groupage_field.getSelectionModel().getSelectedItem().toString());
            patient.setTaille(Integer.parseInt(taille_field.getText()));
            patient.setSynopsis(synopsis_area.getText());
            patient.save();

            App.setView(getWindow(), "ShowPatient", new HashMap() {{
                put("id", patient.getId());

            }});

            main_window.update();
        });

        synopsis_area.requestFocus();
    }

    public void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
