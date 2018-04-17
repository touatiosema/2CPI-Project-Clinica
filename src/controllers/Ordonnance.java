package controllers;

import core.MyPrinter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.LigneEnOrdonnance;
import models.Medecin;
import models.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Ordonnance {
    private ArrayList<LigneEnOrdonnance> ordonnances;
    private Stage stage;
    private Patient patient;
    private Medecin medecin;


    @FXML
    MenuItem printPreviewItem;
    @FXML
    MenuItem fermer;
    @FXML
    MenuItem printItem;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text textOrdonnance;

    @FXML
    Label labelInfo;
    @FXML
    Label labelMedecin;
    @FXML
    Label labelCabinet;
    @FXML
    Label labelLieu;
    @FXML
    Label labelDate;
    @FXML
    Label labelAge;
    @FXML
    Label labelPatient;

    public void initialize(){
        fermer.setOnAction(event -> close());
        printItem.setOnAction(event -> print());
        printPreviewItem.setOnAction(event -> printPreview());
    }

    public void setArgs(Stage stage, ArrayList<LigneEnOrdonnance> ordonnances, Patient patient, Medecin medecin){
    this.stage=stage;
    this.ordonnances=ordonnances;
    this.patient=patient;
    this.medecin=medecin;
    setInfo();
    }

    private void setInfo(){
        labelAge.setText(String.valueOf(patient.getAge())+" ans");
        labelDate.setText("le :"+new Date().toString());
        labelMedecin.setText("DR :"+medecin.getNom().toUpperCase()+" "+medecin.getPrenom());
        labelCabinet.setText("nom du Cabinet");
        labelLieu.setText("le lieu ....");
        labelInfo.setText("les information .......");
        labelPatient.setText(patient.getNom().toUpperCase()+" "+patient.getPrenom());

        if(ordonnances==null || ordonnances.isEmpty()) {
            textOrdonnance.setText("Aucune Ordonnance pour cette consultation!");
            return;
        }
       for(LigneEnOrdonnance ligne : ordonnances){
           textOrdonnance.setText(textOrdonnance.getText().concat(ligne.getAll()+'\n'+'\n'));
       }

    }




    public void print(){
        MyPrinter.print(stage, anchorPane);
    }

    private void close(){
        stage.close();
    }

    public void printPreview(){
        try {
            MyPrinter.preview(anchorPane);
        } catch (IOException e) {
            System.out.println("Certificat: printPreview: Exception");
            e.printStackTrace();
        }
    }

}




