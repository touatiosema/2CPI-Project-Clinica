package controllers;
import core.App;
import core.Auth;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import models.LigneEnagenda;
import models.Medicament;
import utils.ActionButton;

import java.beans.EventHandler;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class AgendaController extends Controller{
    private ArrayList<LigneEnagenda> agenda;
    private Stage stage;
    private int id;

    @FXML
    TableView<LigneEnagenda> table;
    @FXML
    TableColumn<LigneEnagenda, String> columntypeRDV;
    @FXML
    TableColumn<LigneEnagenda, String> columnpatient;
    @FXML
    TableColumn<LigneEnagenda, String> columnheure;
    @FXML
    TableColumn<LigneEnagenda, String> columndate;
    @FXML
    TableColumn<LigneEnagenda, String> columnRemarque;
    TableColumn<LigneEnagenda, Void> columnOperation;
    @FXML
    Button buttonaffpro;
    @FXML
    Button buttonaffpers;
    @FXML
    DatePicker ddate;
    @FXML
    private  Button okk;
    @FXML
    ChoiceBox ajouterRdv;
    @FXML
    TextField searchText;
    @FXML
    Button btnShow;
    Date jour;
    private ObservableList<LigneEnagenda> rdvList;
    private FilteredList<LigneEnagenda> filteredList;


    char prev;

    public AgendaController() {
        min_width = width = max_height = 837;
        min_height = height = max_height = 482;
    }

    private void addActionsButton() {
        if (columnOperation != null)
            table.getColumns().removeAll(columnOperation);
        columnOperation = new TableColumn<>("Modifier");
        Callback<TableColumn<LigneEnagenda, Void>, TableCell<LigneEnagenda, Void>> cellFactory = new Callback<TableColumn<LigneEnagenda, Void>, TableCell<LigneEnagenda, Void>>() {
            @Override
            public TableCell<LigneEnagenda, Void> call(final TableColumn<LigneEnagenda, Void> param) {
                final TableCell<LigneEnagenda, Void> cell = new TableCell<LigneEnagenda, Void>() {
                    private Node create() {
                        HBox layout = new HBox();
//                        LigneEnagenda medicament = this.getTableView().getItems().get(this.getIndex());
                       // int id = this.getTableRow().getIndex();//this.getTableView().getItems().get(this.getIndex()).getId();
                        //LigneEnagenda ligneEnagenda = this.getTableView().getItems().get()
                        int ligne = this.getIndex();
//                        ActionButton deleteBtn;
//                        deleteBtn = new ActionButton(this, "Supprimer", FontAwesomeIcon.CLOSE, "#B4302A") {
//                            @Override
//                            public void onAction(Object med) {
//                                delete(id, ligne);
//                            }
//                        };

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

    public void modify(int i){
        System.out.println("it's here: "+i);
    }
    public void initialize(){
        ajouterRdv.getItems().addAll("Personnelle", "Professionnel");
        ajouterRdv.getSelectionModel().selectedItemProperty().addListener((v, ov, nv) -> {
            AgendaController self = this;
            if (v.getValue().equals("Professionnel") == true) {
                App.newWindow("PatientRdv", new HashMap() {{
                    put("main_window", self);
                }})     ;
            }

            else {
                App.newWindow("PersonRdv", new HashMap() {{
                    put("main_window", self);
                }});
            }
            addActionsButton();

        });

        okk.setOnAction(e -> getRdvdate());
        btnShow.setOnAction(event->getData('a'));
        buttonaffpro.setOnAction(e -> getData('F'));
        buttonaffpers.setOnAction(e-> getData('P'));

        columntypeRDV.setCellValueFactory(new PropertyValueFactory<>("Type_de_RDV"));
        columnpatient.setCellValueFactory(new PropertyValueFactory<>("Patient"));
        columnheure.setCellValueFactory(new PropertyValueFactory<>("Heure"));
        columndate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        columnRemarque.setCellValueFactory(new PropertyValueFactory<>("Remarques"));
        table.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount()==2){
                    System.out.println(table.getSelectionModel().getSelectedIndex());
                    modify(1);
                }
            }

        });

        getData('a');
        addActionsButton();
    }

    public void refresh() {
        //rdvList.removeAll();
        getData('a');
    }


    private void ajouter(){}


        public ObservableList<LigneEnagenda> getRdvPersonel () {
            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvPersonel(Auth.getUserID()));
            return obs;
        }
    public void affichpers() {

    }

    public ObservableList<LigneEnagenda> getRdvProfessionnel () {
            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvProfessionnel());
            return obs;
        }
    public void getData(char c) {
        prev = c;
        if (rdvList != null) rdvList.removeAll(rdvList);
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
            filteredList.setPredicate(ligneEnagenda -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if (ligneEnagenda.getType_de_RDV().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (ligneEnagenda.getPatient()!=null&&ligneEnagenda.getPatient().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (ligneEnagenda.getDate()!=null&&ligneEnagenda.getDate().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (ligneEnagenda.getHeure()!=null&&ligneEnagenda.getHeure().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (ligneEnagenda.getRemarques()!=null&&ligneEnagenda.getRemarques().toLowerCase().contains(lowercaseFilter))
                    return true;
                return false;
            });
        });

        SortedList<LigneEnagenda> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

    }

    public ObservableList<LigneEnagenda> getRdvdate () {
         LocalDate ddd = ddate.getValue();

            if (ddd == null) return null;

                    jour  = java.sql.Date.valueOf(ddd);

            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvdate(Auth.getUserID(), jour));
            table.setItems(obs);
            return obs;
        }
    public void affichrdv() {

    }



    public void supp(int id) {
        LigneEnagenda ligneEnagenda=table.getItems().get(id);
        LigneEnagenda.supprimer(ligneEnagenda.getId());
        rdvList.remove(id);
        //filteredList.remove(id);
        wrapListAndAddFiltering();
    }
}
