package controllers;

import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Medecin;
import models.Patient;
import utils.ActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageAccountsController extends Controller {

    @FXML
    TableView users_table;

    @FXML
    TextField search_nom;

    @FXML
    TextField search_prenom;

    @FXML
    TextField search_username;

    @FXML
    CheckBox search_deactivated;

    TableColumn<Medecin, Void> col_btn;

    @FXML
    MenuItem menu_new;

    @FXML
    MenuItem menu_profile;

    @FXML
    MenuItem menu_edit;

    @FXML
    MenuItem menu_activation;

    @FXML
    MenuItem menu_logout;

    public ManageAccountsController() {
        width = min_width = 600;
        width = min_height = 400;
        title = "Gestion des comptes";
    }

    public void acceuil() {
        App.setView("Acceuil");
    }

    public void init() {
        search_deactivated.setOnAction((e) -> update());
        search_nom.textProperty().addListener((v, oldValue, NewValue) -> update());
        search_prenom.textProperty().addListener((v, oldValue, NewValue) -> update());
        search_username.textProperty().addListener((v, oldValue, NewValue) -> update());

        users_table.setRowFactory( tv -> {
            TableRow<Medecin> row = new TableRow<Medecin>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Medecin medecin = row.getItem();
                    showMedecin(medecin);
                }
            });

            return row;
        });

        users_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeMenuState(users_table.getSelectionModel().getSelectedItem() != null);
            }
        });

        users_table.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                Medecin selected_user;

                if (newPropertyValue)
                {
                    selected_user = (Medecin) users_table.getSelectionModel().getSelectedItem();
                }
                else
                {
                    selected_user = null;
                }

                changeMenuState(selected_user != null);
            }
        });

        addActionsButtons();

        menu_new.setOnAction(e -> newMedecin());
        menu_profile.setOnAction(e -> showMedecin((Medecin) users_table.getSelectionModel().getSelectedItem()));
        menu_edit.setOnAction(e -> editMedecin((Medecin) users_table.getSelectionModel().getSelectedItem()));
        menu_activation.setOnAction(e -> activationMedecin((Medecin) users_table.getSelectionModel().getSelectedItem()));
        menu_logout.setOnAction(e -> App.logout());

        update();
    }

    private void changeMenuState(boolean enabled) {
        if (enabled == false) {
            menu_profile.setDisable(true);
            menu_edit.setDisable(true);
            menu_activation.setDisable(true);
        }

        else {
            menu_profile.setDisable(false);
            menu_edit.setDisable(false);
            menu_activation.setDisable(false);

            Medecin medecin = (Medecin) users_table.getSelectionModel().getSelectedItem();

            if (medecin.isActive()) menu_activation.setText("Désactiver");
            else menu_activation.setText("Activer");

            if (medecin.isAdmin()) {
                menu_activation.setDisable(true);
            }
        }
    }

    private void addActionsButtons() {
        if (col_btn != null)
            users_table.getColumns().removeAll(col_btn);

        col_btn = new TableColumn("");

        Callback<TableColumn<Medecin, Void>, TableCell<Medecin, Void>> cellFactory = new Callback<TableColumn<Medecin, Void>, TableCell<Medecin, Void>>() {
            @Override
            public TableCell<Medecin, Void> call(final TableColumn<Medecin, Void> param) {
                final TableCell<Medecin, Void> cell = new TableCell<Medecin, Void>() {

                    private Node create(){
                        HBox layout = new HBox();

                        Medecin medecin = this.getTableView().getItems().get(this.getIndex());
                        ActionButton deactivate_btn = null;

                        if (!medecin.isAdmin()) {

                            if (medecin.isActive()) {
                                deactivate_btn = new ActionButton(this, "Désactivater", FontAwesomeIcon.CLOSE, "#B4302A") {
                                    @Override
                                    public void onAction(Object medecin) {
                                        activationMedecin((Medecin) medecin);
                                    }
                                };
                            }

                            else {
                                deactivate_btn = new ActionButton(this, "Activer", FontAwesomeIcon.CHECK, "#29A131") {
                                    @Override
                                    public void onAction(Object medecin) {
                                        activationMedecin((Medecin) medecin);
                                    }
                                };
                            }
                        }


                        ActionButton show_btn = new ActionButton(this, "Profile", FontAwesomeIcon.EYE, "#2c81a0") {
                            @Override
                            public void onAction(Object medecin) {
                                showMedecin((Medecin) medecin);
                            }
                        };

                        ActionButton edit_btn = new ActionButton(this, "Modifier", FontAwesomeIcon.PENCIL, "#672ca0") {
                            @Override
                            public void onAction(Object medecin) {
                                editMedecin((Medecin) medecin);
                            }
                        };



                        layout.getChildren().addAll(show_btn, edit_btn);
                        if (!medecin.isAdmin()) layout.getChildren().add(deactivate_btn);

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
        users_table.getColumns().add(col_btn);
    }

    private void showMedecin(Medecin medecin) {
        Controller self = this;
        App.newWindow("ShowAccount", new HashMap() {{
            put("id", medecin.getId());
            put("main_window", self);
        }});
    }

    private void activationMedecin(Medecin medecin) {
        medecin.setActive(!medecin.isActive());
        medecin.save();
        update();
    }

    private void newMedecin() {
        Controller self = this;

        App.newWindow("NewAccount", new HashMap() {{
            put("main_window", self);
        }});
    }

    private void editMedecin(Medecin medecin) {
        Controller self = this;
        App.newWindow("EditAccount", new HashMap() {{
            put("id", medecin.getId());
            put("main_window", self);
        }});
    }

    public void update() {
        String nom_text = search_nom.getText();
        String prenom_text = search_prenom.getText();
        String username_text = search_username.getText();
        boolean status = search_deactivated.isSelected();

        if (nom_text == null) nom_text = "";
        if (prenom_text == null) prenom_text = "";
        if (username_text == null) username_text = "";

        ArrayList<Medecin> users = Medecin.search(nom_text, prenom_text, username_text, status);
        ObservableList<Medecin> observableUsers = FXCollections.observableArrayList();
        observableUsers.addAll(users);

        users_table.setItems(observableUsers);
    }
}
