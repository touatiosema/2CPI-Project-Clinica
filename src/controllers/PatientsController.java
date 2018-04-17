package controllers;

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
    TextField search_nom;

    @FXML
    TextField search_prenom;

    @FXML
    ChoiceBox<String> search_genre;

    @FXML
    TextField search_telephone;

    TableColumn<Patient, Void> col_btn;

    @FXML
    MenuItem menu_new;

    @FXML
    MenuItem menu_show;

    @FXML
    MenuItem menu_logout;

    public PatientsController() {
        title = "Patients";
        min_width = width = 600;
        min_height = height = 400;
    }

    public void acceuil() {
        App.setView("Acceuil");
    }

    @Override
    public void init() {
        search_genre.getItems().addAll("Genre", "M", "F");


        search_genre.getSelectionModel().selectedItemProperty().addListener((v, oldValue, NewValue) -> update());
        search_nom.textProperty().addListener((v, oldValue, NewValue) -> update());
        search_prenom.textProperty().addListener((v, oldValue, NewValue) -> update());
        search_telephone.textProperty().addListener((v, oldValue, NewValue) -> update());

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

        patients_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeMenuState(patients_table.getSelectionModel().getSelectedItem() != null);
            }
        });

        patients_table.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                Patient selected_patient;

                if (newPropertyValue)
                {
                    selected_patient = (Patient) patients_table.getSelectionModel().getSelectedItem();
                }
                else
                {
                    selected_patient = null;
                }

                changeMenuState(selected_patient != null);
            }
        });

//        addActionsButtons();

        search_nom.requestFocus();
        changeMenuState(false);

        menu_logout.setOnAction((ActionEvent event) -> {
            App.logout();
        });

        menu_new.setOnAction((ActionEvent event) -> {
            newPatient();
        });

        menu_show.setOnAction((ActionEvent event) -> showPatient((Patient) patients_table.getSelectionModel().getSelectedItem()));

        update();
    }

    private void changeMenuState(boolean enabled) {
        if (enabled == false) {
            menu_show.setDisable(true);
        }

        else {
            menu_show.setDisable(false);
        }
    }

    public void update() {

        String nom_text = search_nom.getText();
        String prenom_text = search_prenom.getText();
        String telephone_text = search_telephone.getText();
        String genre_text = search_genre.getValue();

        if (nom_text == null) nom_text = "";
        if (prenom_text == null) prenom_text = "";
        if (genre_text == null) genre_text = "";
        if (telephone_text == null) telephone_text = "";

        ArrayList<Patient> patients = Patient.search(nom_text, prenom_text, genre_text, telephone_text);

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

                        layout.getChildren().addAll(show_btn);

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
        App.newWindow("dossierPatient", new HashMap() {{
            put("id", patient.getId());
        }});
    }

    public void newPatient() {
        PatientsController self = this;
        Stage window = App.newWindow("NewPatient", new HashMap() {{
            put("main_window", self);
        }});
    }
    public void resetSearch() {
        search_nom.setText("");
        search_prenom.setText("");
        search_telephone.setText("");
        search_genre.setValue("");
    }
}

