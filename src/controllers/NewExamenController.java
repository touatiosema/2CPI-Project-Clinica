package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Examen;
import models.Medicament;

import java.util.HashMap;

public class NewExamenController extends Controller{

    @FXML
    Button btnSave;

    @FXML
    TextField name;

    @FXML
    NewBilanController main_window;

    public NewExamenController(){
        min_height=max_height=100;
        min_width =max_width =400;
    }
    public void initialize(){
        //this.getWindow().initModality(Modality.APPLICATION_MODAL);
        btnSave.setOnAction(event -> save());
    }

    public void init(HashMap map){
        main_window = (NewBilanController) map.get("main_window");

    }

    private void save(){
        if (!name.getText().equals("")) {
            Examen examen = new Examen(0, name.getText());
            examen.save();
            main_window.update();
            getWindow().close();
        }
    }

    private void close(){
        this.getWindow().close();
    }
}
