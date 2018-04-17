package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.RDV;

public class PatienRdvController extends Controller {


        @FXML
        private JFXDatePicker date;
        @FXML
        private  JFXTimePicker heure;

        @FXML
        private TextArea description;
        @FXML
        private TextField name;
        public PatienRdvController(){
             title = "+ Rendez-vous Professionel";
            height=  515;
            width =365;

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

                PatRdv.setType("R");

                PatRdv.save();




                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Terminer");
                alert.setHeaderText(null);
                alert.setContentText("Rendez vous ajouté ! ");
                alert.showAndWait();


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


