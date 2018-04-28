package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.FicheDeConsultation;

import java.util.HashMap;

public class NewCertificatController extends Controller {

    @FXML TextArea certificat_field;
    @FXML Button save_btn;

    FicheDeConsultation consultation;

    public NewCertificatController() {
        width = 400;
        min_height = height = 250;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        consultation = new FicheDeConsultation(id);
        ConsultationController main_window = (ConsultationController) args.get("main_window");

        save_btn.setOnAction(e -> {
            consultation.setCertificat(certificat_field.getText());
            consultation.saveConsultation();

            App.setView(getWindow(), "ShowCertificat", new HashMap() {{
                put("id", consultation.getId());
            }});

            main_window.setupActionButtons();
        });
    }
}
