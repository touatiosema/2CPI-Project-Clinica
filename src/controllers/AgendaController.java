package controllers;
import core.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Agenda;
import models.LigneEnagenda;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


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
    @FXML
    Button buttonsupp;
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

    Date jour;

    public AgendaController() {
        min_width = width = max_height = 790;
        min_height = height = max_height = 400;
    }

    public void init() {

        ajouterRdv.getItems().addAll("Personnelle", "Professionnel");

        ajouterRdv.getSelectionModel().selectedItemProperty().addListener((v, ov, nv) -> {

            if (v.getValue().equals("Professionnel") == true) {
                App.newWindow("PatientRdv") ;
            }

            else {
                App.newWindow("PersonRdv");
            }

        });
    }

    public void initialize(){

        okk.setOnAction(e -> getRdvdate());
        buttonsupp.setOnAction(e -> supp());
        buttonaffpro.setOnAction(e -> getRdvProfessionnel());
        buttonaffpers.setOnAction(e-> getRdvPersonel());


        columntypeRDV.setCellValueFactory(new PropertyValueFactory<>("Type_de_RDV"));
        columnpatient.setCellValueFactory(new PropertyValueFactory<>("Patient"));
        columnheure.setCellValueFactory(new PropertyValueFactory<>("Heure"));
        columndate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        columnRemarque.setCellValueFactory(new PropertyValueFactory<>("Remarques"));
    }

    private void ajouter(){}

    public void setArgs(Stage stage, ArrayList<LigneEnagenda> agenda){
        this.stage=stage;
        this.agenda=agenda;
        setInfo();
    }

    private void setInfo(){
        table.setItems(getData(agenda));
    }




   private ObservableList<LigneEnagenda> getData(ArrayList<LigneEnagenda> agenda){
        ObservableList<LigneEnagenda> observableList = FXCollections.observableArrayList();
        for(LigneEnagenda ligneEnagenda: agenda){
            observableList.add(new LigneEnagenda(ligneEnagenda.getType_de_RDV(),
                    ligneEnagenda.getPatient(),
                    ligneEnagenda.getHeure(),
                    ligneEnagenda.getDate(),
                    ligneEnagenda.getRemarques()));

        }
        return observableList;
    }

        public ObservableList<LigneEnagenda> getRdvPersonel () {
            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvPersonel(1));
            table.setItems(obs);
            return obs;
        }
    public void affichpers() {

    }

    public ObservableList<LigneEnagenda> getRdvProfessionnel () {
            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvProfessionnel());
            table.setItems(obs);
            return obs;
        }

        /*
    public void affichprof() {
        getRdvdate();

    }
    */
        public ObservableList<LigneEnagenda> getRdvdate () {
            LocalDate ddd = ddate.getValue();

            if (ddd == null) return null;

                    jour  = java.sql.Date.valueOf(ddd);

            ObservableList<LigneEnagenda> obs = FXCollections.observableArrayList();
            obs.addAll(Agenda.getRdvdate(1, jour));
            table.setItems(obs);
            return obs;
        }
    public void affichrdv() {

    }



    public void supp() {
        LigneEnagenda rendez_vous = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(rendez_vous);
        Agenda element = new Agenda();

        if (element != null) {
            element.setId(rendez_vous.getId());
            element.supprimer(element.getId());
        }
    }
}
