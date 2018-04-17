package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.LigneEnOrdonnance;

import java.util.ArrayList;

public class Ordonnance {
    private ArrayList<LigneEnOrdonnance> ordonnances;
    private Stage stage;


    @FXML
    TableView<LigneTable> tableView;
    @FXML
    TableColumn<LigneTable, String> columnMedicament;
    @FXML
    TableColumn<LigneTable, String> columnType;
    @FXML
    TableColumn<LigneTable, String> columnDose;
    @FXML
    TableColumn<LigneTable, String> columnRemarques;
    @FXML
    Button buttonRetour;

    public void initialize(){
            buttonRetour.setOnAction(e->close());
            columnMedicament.setCellValueFactory(
                    new PropertyValueFactory<LigneTable, String>("medicament"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            columnDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
            columnRemarques.setCellValueFactory(new PropertyValueFactory<>("remarque"));
            //tableView.getColumns().addAll(columnDose,columnMedicament,columnRemarques,columnType);
            //tableView.setItems();
    }

    public void setArgs(Stage stage, ArrayList<LigneEnOrdonnance> ordonnances){
    this.stage=stage;
    this.ordonnances=ordonnances;
    setInfo();
    }

    private void close(){
        stage.close();
    }

    private void setInfo(){
        tableView.setItems(getData(ordonnances));
    }



    public class LigneTable{
        private String medicament;
        private String dose;
        private String remarque;
        private String type;

        public LigneTable(){
            medicament="";
            dose="";
            remarque="";
            type="";
        }
        public LigneTable(String medicament, String dose, String remarque, String type){
            this.medicament=medicament;
            this.remarque=remarque;
            this.dose=dose;
            this.type=type;
        }

        public String getMedicament() {
            return medicament;
        }

        public void setMedicament(String medicament) {
            this.medicament = medicament;
        }

        public String getDose() {
            return dose;
        }

        public void setDose(String dose) {
            this.dose = dose;
        }

        public String getRemarque() {
            return remarque;
        }

        public void setRemarque(String remarque) {
            this.remarque = remarque;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private ObservableList<LigneTable> getData(ArrayList<LigneEnOrdonnance> ordonnances){
        ObservableList<LigneTable> observableList = FXCollections.observableArrayList();
        for(LigneEnOrdonnance ligneEnOrdonnance: ordonnances){
            observableList.add(new LigneTable(ligneEnOrdonnance.getMedicament().getNomCommercial(),
                                              ligneEnOrdonnance.getDose(),
                                              ligneEnOrdonnance.getDetails(),
                                              ligneEnOrdonnance.getMedicament().getType()));
        }
        return observableList;

    }
}




