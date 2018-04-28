package controllers;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Config;

import java.util.HashMap;

public class CabinetSetupController extends Controller {
    @FXML
    TextField name_field;

    @FXML
    TextField adresse_field;

    @FXML
    Button save_btn;

    public CabinetSetupController() {
        min_width = width = max_width = 330;
        min_height = height = max_height = 180;
    }

    public void init(HashMap args) {

        if (!((boolean) args.get("isSetup"))) {
            name_field.setText(Config.get("cabinet_name", ""));
            adresse_field.setText(Config.get("cabinet_adresse", ""));
        }


        save_btn.setOnAction(e -> {
            String name = name_field.getText();
            String adressse = adresse_field.getText();

            Config.set("cabinet_name", name);
            Config.set("cabinet_adresse", adressse);

            if ((boolean) args.get("isSetup"))
                App.setView(App.entry_setup);
            else
                getWindow().close();

        });
    }

}
