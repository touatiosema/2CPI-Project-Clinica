package controllers;

import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Bilan;
import models.FicheDeConsultation;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;

public class NewConsultationController extends Controller {
    @FXML
    TextField poids_field;

    @FXML
    TextArea motif_field;

    @FXML
    TextArea exam_clinic_field;

    @FXML
    TextArea exam_supp_field;

    @FXML
    TextArea diagnostique_field;

    @FXML
    Button save_btn;

    ShowPatientController main_window;

    int patient_id;


    public NewConsultationController() {
        width = 400;
        height = 550;
    }

    public void init(HashMap args) {
        if (args.get("main_window") == null) {
            main_window = null;
        }

        else main_window = (ShowPatientController) args.get("main_window");

        patient_id = (int) args.get("patient_id");

        save_btn.setOnAction(e -> {
            String poids_str = poids_field.getText();
            int poids = 0;

            if (!poids_str.equals("")) {
                try {
                    poids = Integer.parseInt(poids_str);
                }

                catch (Exception o) {
                    warn("Poids Invalide");
                    return;
                }
            }


            String motif = motif_field.getText();
            String exam_clinic = exam_clinic_field.getText();
            String exam_supp = exam_supp_field.getText();
            String diagnostique = diagnostique_field.getText();

            if (motif.equals("")) {  warn("Motif invalide."); return; }
            if (diagnostique.equals("")) {  warn("Diagnostique invalide."); return; }

            FicheDeConsultation consultation = new FicheDeConsultation();
            consultation.setPoids(poids);
            consultation.setExamenClinique(exam_clinic);
            consultation.setExamenSuppl(exam_supp);
            consultation.setDiagnostic(diagnostique);
            consultation.setMotif(motif);

            consultation.setDate(new Date());
            consultation.setTime(Time.valueOf(LocalTime.now()));
            consultation.setIdMedecin(Auth.getUserID() == 0 ? 2 : Auth.getUserID());
            consultation.setIdPatient(patient_id);
            consultation.saveConsultation();

            int id = consultation.getId();

            if (main_window != null) main_window.refreshList();
            App.setView(getWindow(), "Consultation", new HashMap() {{
                put("id", id);
            }});
        });
    }

    private void warn(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
