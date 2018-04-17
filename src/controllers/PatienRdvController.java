    package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.RDV;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.HashMap;

    public class PatienRdvController extends Controller {


        @FXML
        private JFXDatePicker date;
        @FXML
        private  JFXTimePicker heure;

        @FXML
        private TextArea description;

        AgendaController main_window;

      @FXML
    private TextField name;
      private  RDV PatRdv = new RDV();

    public PatienRdvController(){
            title = "+ Rendez-vous professionel";
            min_height = height = max_height = 515;
            min_width = width = max_width = 365;
        }

        public void init(HashMap args) {
            main_window = (AgendaController) args.get("main_window");
        }


        public void initialize(){
            ArrayList<String> possibleWords = PatRdv.patientsName();
            TextFields.bindAutoCompletion(name, possibleWords);
        }

        public void SaveRdv2(){
            if (java.sql.Date.valueOf(date.getValue()).toString().length() !=0 &&
                    description.getText().length() != 0 &&
                    java.sql.Time.valueOf(heure.getValue()).toString().length() !=0 &&

                    name.getText().length() !=0)
            {



                RDV PatRdv = new RDV();
                PatRdv.setDate(java.sql.Date.valueOf(date.getValue()));
                PatRdv.setDescription(description.getText());
                PatRdv.setHeure(java.sql.Time.valueOf(heure.getValue()));
                PatRdv.setPatient(name.getText());

                PatRdv.setType("F");

                PatRdv.save();




                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Terminer");
                alert.setHeaderText(null);
                alert.setContentText("Rendez vous ajouté ! ");
                alert.showAndWait();

                main_window.refresh();
                getWindow().close();


            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention ! ");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez compléter touts les champs svp !  ");
                alert.showAndWait();
            }
        }



        }


