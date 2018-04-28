package controllers;

import com.jfoenix.controls.JFXTextField;
import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Patient;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ActionButton;

public class PatientsController extends Controller {

    @FXML
    TableView patients_table;

    @FXML
    JFXTextField search;

    TableColumn<Patient, Void> col_btn;


    public PatientsController() {
        title = "Patients";
        min_width = width = 800;
        min_height = height = 600;
    }

    public void acceuil() {
        App.setView("Acceuil");
    }

    @Override
    public void init() {

        patients_table.setRowFactory( tv -> {
            TableRow<Patient> row = new TableRow<Patient>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Patient patient = row.getItem();

                    showPatient(patient);
                }
            });

            return row;
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            update();
        });

        addActionsButtons();

        search.requestFocus();
        update();
    }
    public void update() {


        ArrayList<Patient> patients = Patient.search(search.getText());

        ObservableList<Patient> observablePatients = FXCollections.observableArrayList();
        observablePatients.addAll(patients);

        patients_table.setItems(observablePatients);
    }


    private void addActionsButtons() {
        if (col_btn != null)
            patients_table.getColumns().removeAll(col_btn);

        col_btn = new TableColumn("");

        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                final TableCell<Patient, Void> cell = new TableCell<Patient, Void>() {

                    private Node create(){
                        HBox layout = new HBox();

                        ActionButton show_btn = new ActionButton(this, "Dossier medical", FontAwesomeIcon.EYE, "#2c81a0") {
                            @Override
                            public void onAction(Object patient) {
                                showPatient((Patient) patient);
                            }
                        };

                        ActionButton add_btn = new ActionButton(this, "Ajouter Consultation", FontAwesomeIcon.PLUS, "#672ca0") {
                            @Override
                            public void onAction(Object patient) {
                                addConsultation((Patient) patient);
                            }
                        };

                        layout.getChildren().addAll(show_btn, add_btn);

                        return layout;
                    }


                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(create());
                        }
                    }
                };
                return cell;
            }
        };


        col_btn.setCellFactory(cellFactory);
        patients_table.getColumns().add(col_btn);
    }

    private void showPatient(Patient patient) {
        PatientsController self = this;
        App.newWindow("ShowPatient", new HashMap() {{
            put("id", patient.getId());
            put("main_window", self);
        }});
    }

    private void addConsultation(Patient patient) {
        App.newWindow("NewConsultation", new HashMap() {{
            put("main_window", null);
            put("patient_id", patient.getId());
        }});
    }

    public void newPatient() {
        PatientsController self = this;
        Stage window = App.newWindow("NewPatient", new HashMap() {{
            put("main_window", self);
        }});
    }

}

