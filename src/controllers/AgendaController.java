package controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Agenda;


import utils.ActionButton;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class AgendaController extends Controller{
    private ArrayList<Agenda> agenda;
    private Stage stage;
    private int id;

    @FXML
    TableView<Agenda> table;
    @FXML
    TableColumn<Agenda, String> columntypeRDV;
    @FXML
    TableColumn<Agenda, String> columnpatient;
    @FXML
    TableColumn<Agenda, String> columnheure;
    @FXML
    TableColumn<Agenda, String> columndate;
    @FXML
    TableColumn<Agenda, String> columnRemarque;
    TableColumn<Agenda, Void> columnOperation;
    @FXML
    JFXDatePicker ddate;

    @FXML
    JFXButton add_btn;

    @FXML
    JFXTextField searchText;

    Date jour;
    private ObservableList<Agenda> rdvList;
    private FilteredList<Agenda> filteredList;

    @FXML
    JFXComboBox type_box;


    char prev;

    public AgendaController() {
        min_width = width  = 820;
        min_height = height = 550;
    }

    private void addActionsButton() {
        if (columnOperation != null)
            table.getColumns().removeAll(columnOperation);
        columnOperation = new TableColumn<>("");
        Callback<TableColumn<Agenda, Void>, TableCell<Agenda, Void>> cellFactory = new Callback<TableColumn<Agenda, Void>, TableCell<Agenda, Void>>() {
            @Override
            public TableCell<Agenda, Void> call(final TableColumn<Agenda, Void> param) {
                final TableCell<Agenda, Void> cell = new TableCell<Agenda, Void>() {
                    private Node create() {
                        HBox layout = new HBox();
                        int ligne = this.getIndex();
                        ActionButton modifieBtn = new ActionButton(this, "Supprimer", FontAwesomeIcon.TRASH, "#2c81a0" ){
                            @Override
                            public void onAction(Object med) {
                                supp(ligne);
                            }
                        };
                        layout.getChildren().addAll(modifieBtn);
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
        columnOperation.setCellFactory(cellFactory);
        table.getColumns().add(columnOperation);
    }

    public void initialize(){
        AgendaController self = this;

        add_btn.setOnAction(e -> {
            App.newWindow("RDV", new HashMap() {{
                put("main_window", self);
                put("modify", false);
                put("agenda", null);
            }});
        });

        ddate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                ddate.setValue(null);
            }
        });

        ddate.setOnAction(e -> getRdvdate());

        type_box.getItems().addAll("All", "Professionels", "Personels");
        type_box.getSelectionModel().selectFirst();

        type_box.getSelectionModel().selectedItemProperty().addListener((v, oldValue, NewValue) -> {
            if (NewValue == "All") getData('a');
            else if (NewValue == "Professionels") getData('F');
            else getData('P');
        });

        columntypeRDV.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnpatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        columnheure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        columndate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnRemarque.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount()==2){
                    modify(table.getSelectionModel().getSelectedIndex());
                }
            }
        });

        getData('a');
        addActionsButton();
    }
    public void refresh() {
        getData('a');
    }


    public void modify(int i){
        AgendaController self  = this;
        Agenda agenda = table.getItems().get(i);
        App.newWindow("RDV", new HashMap() {{
                put("main_window", self);
                put("modify", true);
                put("agenda", agenda);
        }});
    }



        public ObservableList<Agenda> getRdvPersonel () {
            ObservableList<Agenda> obs = FXCollections.observableArrayList();
            obs.addAll( Agenda.getRdvPersonel(Auth.getUserID()));
            return obs;
        }

    public ObservableList<Agenda> getRdvProfessionnel () {
            ObservableList<Agenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvProfessionnel(Auth.getUserID()));
            return obs;
        }

    public void getData(char c) {
        prev = c;
        if (rdvList != null) {
            rdvList.removeAll();
        }
        if (c == 'P')
            rdvList = getRdvPersonel();
        else if (c == 'F')
            rdvList = getRdvProfessionnel();
        else {
            rdvList = getRdvProfessionnel();
            rdvList.addAll(getRdvPersonel());
        }
        wrapListAndAddFiltering();
    }

    public void wrapListAndAddFiltering(){
        filteredList = new FilteredList<>(rdvList, p -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(agenda -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if (agenda.getType().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (agenda.getPatient()!=null&&agenda.getPatient().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (agenda.getDate()!=null&&agenda.getDate().toString().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (agenda.getHeure()!=null&&agenda.getHeure().toString().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (agenda.getDescription()!=null&&agenda.getDescription().toLowerCase().contains(lowercaseFilter))
                    return true;
                return false;
            });
        });

        SortedList<Agenda> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

    }

    public ObservableList<Agenda> getRdvdate () {
            if (ddate.getValue() == null) {
               if ( type_box.getSelectionModel().selectedItemProperty().getValue().equals("Personel") ){
                   getData('P');
               }

               else if (type_box.getSelectionModel().selectedItemProperty().getValue().equals("All")) {
                   getData('a');
               }

               else {
                   getData('F');
               }

               return null;
            }


            jour  =Date.valueOf(ddate.getValue());
            ObservableList<Agenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvdate(Auth.getUserID(), jour));
            table.setItems(obs);
            return obs;
        }

    public void supp(int id) {
        Agenda.supprimer(table.getItems().get(id).getId());
        rdvList.remove(id);
        wrapListAndAddFiltering();
    }
}
