package controllers;

import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import models.FicheDeConsultation;
import models.Patient;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPatientController extends Controller {
    @FXML
    Label nom_label;

    @FXML
    Label prenom_label;

    @FXML
    Label age_label;

    @FXML
    Label adresse_label;

    @FXML
    Label telephone_label;

    @FXML
    Label profession_label;

    @FXML
    Label lieuTravail_label;

    @FXML
    Label groupage_label;

    @FXML
    Label taille_label;

    @FXML
    Label synopsis_area;

    @FXML
    ListView consultations_list;

    @FXML
    Button edit_btn;

    @FXML
    Button add_consultation_btn;

    PatientsController main_window;

    private Patient patient;
    private ArrayList<FicheDeConsultation> consultations;

    public ShowPatientController() {
        min_width = width = max_width = 500;
        min_height = height = max_height = 560;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        main_window = (PatientsController) args.get("main_window");
        patient = new Patient(id);

        nom_label.setText(patient.getNom());
        prenom_label.setText(patient.getPrenom());
        age_label.setText(((Integer)patient.getAge()).toString() + " ans");
        adresse_label.setText(patient.getAddress());
        telephone_label.setText(patient.getTelephone());
        profession_label.setText(patient.getProfession());
        lieuTravail_label.setText(patient.getLieuDeTravail());
        groupage_label.setText(patient.getGroupage());
        taille_label.setText(((Integer)patient.getTaille()).toString());
        synopsis_area.setText(patient.getSynopsis());

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        icon.setFill(Color.WHITE);

        add_consultation_btn.setGraphic(icon);

        icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        icon.setFill(Color.WHITE);
        edit_btn.setGraphic(icon);

        refreshList();

        ShowPatientController self = this;

        add_consultation_btn.setOnAction(e -> {
            App.newWindow("NewConsultation", new HashMap() {{
                put("main_window", self);
                put("patient_id", patient.getId());
            }});
        });

        consultations_list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList list = consultations_list.getSelectionModel().getSelectedIndices();
                if (list.size() == 0) return;

                int i  = (int) list.toArray()[0];
                App.newWindow("Consultation", new HashMap() {{
                    put("id", consultations.get(i).getId());
                }});
            }
        });

        edit_btn.setOnAction((e) -> {
            App.setView(getWindow(), "EditPatient", new HashMap() {{
                put("id", patient.getId());
                put("main_window", main_window);
            }});
        });


    }

    public void refreshList() {
        consultations_list.getItems().clear();
        consultations = FicheDeConsultation.getConsultations(patient.getId());
        for (FicheDeConsultation consultation : consultations) {
            consultations_list.getItems().add(consultation.getDate().toString() + " " + consultation.getTime().toString());
        }
    }
}
