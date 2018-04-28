package controllers;

import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import models.Bilan;
import models.FicheDeConsultation;
import models.Ordonnance;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;

public class ConsultationController extends Controller {

    FicheDeConsultation consultation;

    @FXML
    Label poids_label;

    @FXML
    Label motif_label;

    @FXML
    Label exam_clinic_label;

    @FXML
    Label exam_supp_label;

    @FXML
    Label diagnostique_label;

    @FXML
    Button ordonnance_btn;

    @FXML
    Button bilan_btn;

    @FXML
    Button certificat_btn;

    @FXML
    Button lettrereorientation_btn;

    @FXML
    Label medecin_field;


    public ConsultationController() {
        min_width = width = max_width = 450;
        height = 550;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        consultation = new FicheDeConsultation(id);

        poids_label.setText(((Integer) consultation.getPoids()).toString() + " Kg");
        motif_label.setText(consultation.getMotif());
        exam_clinic_label.setText(consultation.getExamenClinique());
        exam_supp_label.setText(consultation.getExamenSuppl());
        diagnostique_label.setText(consultation.getDiagnostic());

        medecin_field.setText(consultation.getMedecin().getFullName());


        setupActionButtons();
    }

    public void setupActionButtons() {
        consultation = new FicheDeConsultation(consultation.getId());
        ConsultationController self = this;

        boolean add = false;

        if (LocalDate.now().getDayOfMonth() == consultation.getDate().getDate()) {
            LocalTime now = LocalDateTime.now().toLocalTime();
            LocalTime con_time = consultation.getTime().toLocalTime();

            if (now.getHour() - con_time.getHour() < 1) {
                add = true;
            }

            else if (now.getHour() - con_time.getHour() <= 1 && now.getMinute() < con_time.getMinute()) {
                add = true;
            }
        }

        if (Auth.getUserID() != consultation.getIdMedecin()) add = false;

        FontAwesomeIconView icon;

        if (!consultation.getCertificat().equals("")) {
            icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
            icon.setFill(Color.WHITE);
            certificat_btn.setGraphic(icon);

            certificat_btn.setOnAction(e -> {
                App.newWindow("ShowCertificat", new HashMap() {{
                    put("id", consultation.getId());
                }});
            });
        }

        else {
            icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
            icon.setFill(Color.WHITE);
            certificat_btn.setGraphic(icon);

            certificat_btn.setOnAction(e -> {
                App.newWindow("NewCertificat", new HashMap() {{
                    put("id", consultation.getId());
                    put("main_window", self);
                }});
            });

            if (!add) certificat_btn.setDisable(true);
        }

        if (!consultation.getLettreDOrientation().equals("")) {
            icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
            icon.setFill(Color.WHITE);
            lettrereorientation_btn.setGraphic(icon);

            lettrereorientation_btn.setOnAction(e -> {
                App.newWindow("ShowLettreReorientation", new HashMap() {{
                    put("id", consultation.getId());
                }});
            });
        }

        else {
            icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
            icon.setFill(Color.WHITE);
            lettrereorientation_btn.setGraphic(icon);

            lettrereorientation_btn.setOnAction(e -> {
                App.newWindow("NewLettreReorientation", new HashMap() {{
                    put("id", consultation.getId());
                    put("main_window", self);
                }});
            });

            if (!add) lettrereorientation_btn.setDisable(true);
        }

        if (Bilan.getBilanById(consultation.getId()).getFicheBilan().size() == 0) {
            icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
            icon.setFill(Color.WHITE);
            bilan_btn.setGraphic(icon);

            bilan_btn.setOnAction(e -> {
                App.newWindow("NewBilan", new HashMap() {{
                    put("id", consultation.getId());
                    put("main_window", self);
                }});
            });

            if (!add) bilan_btn.setDisable(true);
        }

        else {
            icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
            icon.setFill(Color.WHITE);
            bilan_btn.setGraphic(icon);

            bilan_btn.setOnAction(e -> {
                App.newWindow("ShowBilan", new HashMap() {{
                    put("id", consultation.getId());
                }});
            });
        }


        if (Ordonnance.getOrdonnanceById(consultation.getId()).getOrdonnances().size() == 0) {
            icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
            icon.setFill(Color.WHITE);
            ordonnance_btn.setGraphic(icon);

            ordonnance_btn.setOnAction(e -> {
                App.newWindow("NewOrdonnance", new HashMap() {{
                    put("id", consultation.getId());
                    put("main_window", self);
                }});
            });

            if (!add) ordonnance_btn.setDisable(true);
        }

        else {
            icon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
            icon.setFill(Color.WHITE);
            ordonnance_btn.setGraphic(icon);

            ordonnance_btn.setOnAction(e -> {
                App.newWindow("ShowOrdonnance", new HashMap() {{
                     put("id", consultation.getId());
                }});
            });
        }
    }
}
