package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.FicheDeConsultation;

import java.util.HashMap;

public class NewLettreReorientationController extends Controller {

    @FXML TextArea lettre_field;
    @FXML Button save_btn;

    FicheDeConsultation consultation;

    public NewLettreReorientationController() {
        width = 400;
        min_height = height = 250;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        consultation = new FicheDeConsultation(id);
        ConsultationController main_window = (ConsultationController) args.get("main_window");

        save_btn.setOnAction(e -> {
            consultation.setLettreDOrientation(lettre_field.getText());
            consultation.saveConsultation();

            App.setView(getWindow(), "ShowLettreReorientation", new HashMap() {{
                put("id", consultation.getId());
            }});

            main_window.setupActionButtons();
        });
    }
}
