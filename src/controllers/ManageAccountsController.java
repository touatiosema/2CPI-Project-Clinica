package controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
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

    TableColumn<Medecin, Void> col_btn;

    @FXML
    TableColumn username_col;

    @FXML
    TableColumn nom_col;

    @FXML
    TableColumn prenom_col;

    @FXML
    JFXTextField search;

    @FXML
    JFXCheckBox search_deactivated;

    public ManageAccountsController() {
        width = min_width = 800;
        width = min_height = 600;
        title = "Gestion des comptes";
    }

    public void acceuil() {
        App.setView("Acceuil");
    }

    public void init() {
        search_deactivated.setOnAction((e) -> update());
        search.textProperty().addListener((v, oldValue, NewValue) -> update());

        users_table.setRowFactory( tv -> {
            TableRow<Medecin> row = new TableRow<Medecin>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Medecin medecin = row.getItem();
                    editMedecin(medecin);
                }
            });

            return row;
        });

        addActionsButtons();




//        menu_settings.setOnAction(e -> App.newWindow("CabinetSetup", new HashMap() {{ put("isSetup", false); }}));

        update();
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


                        ActionButton edit_btn = new ActionButton(this, "Modifier", FontAwesomeIcon.PENCIL, "#672ca0") {
                            @Override
                            public void onAction(Object medecin) {
                                editMedecin((Medecin) medecin);
                            }
                        };



                        layout.getChildren().addAll(edit_btn);
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

        col_btn.setResizable(false);

        col_btn.prefWidthProperty().bind(
                users_table.widthProperty()
                        .subtract(nom_col.widthProperty())
                        .subtract(prenom_col.widthProperty())
                        .subtract(username_col.widthProperty())
                        .subtract(2)  // a border stroke?
        );

    }


    private void activationMedecin(Medecin medecin) {
        medecin.setActive(!medecin.isActive());
        medecin.save();
        update();
    }

    public void newMedecin() {
        Controller self = this;

        App.newWindow("EditAccount", new HashMap() {{
            put("main_window", self);
            put("modify", false);
        }});
    }

    private void editMedecin(Medecin medecin) {
        Controller self = this;
        App.newWindow("EditAccount", new HashMap() {{
            put("id", medecin.getId());
            put("main_window", self);
            put("modify", true);
        }});
    }

    public void update() {
        String s = search.getText();
        boolean status = search_deactivated.isSelected();

        if (s == null) s = "";
        ArrayList<Medecin> users = Medecin.search(s, status);
        ObservableList<Medecin> observableUsers = FXCollections.observableArrayList();
        observableUsers.addAll(users);

        users_table.setItems(observableUsers);
    }
}
