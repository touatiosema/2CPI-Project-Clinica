    package controllers;

import com.jfoenix.controls.*;
import core.notification.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Agenda;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

    public class RDVController extends Controller {


        @FXML
        private JFXDatePicker date;
        @FXML
        private JFXTimePicker heure;

        @FXML
        private JFXTextArea description;

        @FXML
        JFXTextField notification_time;

        @FXML
        JFXComboBox type;

        boolean modify=false;
        Agenda agenda;
        AgendaController main_window;

      @FXML
      private TextField name;

      @FXML
        VBox root;

      @FXML
      VBox name_container;

    public RDVController(){
            title = "Rendez-vous";
            min_height = height = max_height = 500;
            min_width = width = max_width = 350;
        }

        public void init(HashMap args) {

            type.getItems().addAll("Professionel", "Personel");

            type.getSelectionModel().selectFirst();
            type.getSelectionModel().selectedItemProperty().addListener((v, oldValue, NewValue) -> {
                root.getChildren().remove(name_container);

                if (NewValue == "Professionel") {
                    root.getChildren().add(name_container);
                }
            });

            main_window = (AgendaController) args.get("main_window");
            agenda=(Agenda) args.get("agenda");
            modify=(boolean) args.get("modify");
            if(modify){
                if (agenda.getTypeChar().equals("R")) {
                    root.getChildren().remove(name_container);
                    type.getSelectionModel().selectLast();
                }

                else {
                    name.setText(agenda.getPatient());
                }


                description.setText(agenda.getDescription());
                date.setValue(agenda.getDate().toLocalDate());
                heure.setValue(agenda.getHeure().toLocalTime());
                notification_time.setText(agenda.getRemindingTime() + "");
            }
        }


        public void initialize(){
            ArrayList<String> possibleWords = Agenda.patientsName();
            TextFields.bindAutoCompletion(name, possibleWords);
        }

        public void SaveRdv2(){
            if (date.getValue() != null && Date.valueOf(date.getValue()).toString().length() !=0 &&
                    description.getText().length() != 0

                &&
                    heure.getValue() != null &&
                    java.sql.Time.valueOf(heure.getValue()).toString().length() !=0
                   )
            {



                Agenda PatRdv = new Agenda();
                PatRdv.setDate(Date.valueOf(date.getValue()));
                PatRdv.setDescription(description.getText());
                PatRdv.setHeure(Time.valueOf(heure.getValue()));

                if (((String)type.getSelectionModel().getSelectedItem()).equals("Professionel")) {
                    PatRdv.setType("F");

                    if ( name.getText().length() == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Attention ! ");
                        alert.setHeaderText(null);
                        alert.setContentText("Nom Patient est invalide");
                        alert.showAndWait();
                        return;
                    }

                    PatRdv.setPatient(name.getText());
                }

                else {
                    PatRdv.setType("R");
                    PatRdv.setPatient("-");

                }

                int reminding_time = 0;

                try {
                    reminding_time = Integer.parseInt(notification_time.getText());
                }

                catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Attention ! ");
                    alert.setHeaderText(null);
                    alert.setContentText("Rappel avant est invalide");
                    alert.showAndWait();

                    return;
                }

                PatRdv.setRemindingTime(reminding_time);
                if(modify)
                    PatRdv.setId(agenda.getId());
                PatRdv.save();
                Notification.createNotificationList();



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


