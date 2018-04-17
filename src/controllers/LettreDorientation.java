package controllers;

import core.MyPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Medecin;
import models.Patient;

import java.io.IOException;
import java.util.Date;


public class LettreDorientation {

    private String lettre;
    private Stage stage;
    Patient patient;
    Medecin medecin;
    public void initialize(){
        fermer.setOnAction(event -> close());
        printItem.setOnAction(event -> print());
        printPreviewItem.setOnAction(event -> printPreview());

    }
    public void setInfo(){
        labelAge.setText(String.valueOf(patient.getAge())+" ans");
        labelDate.setText("le :"+new Date().toString());
        labelMedecin.setText("DR :"+medecin.getNom().toUpperCase()+" "+medecin.getPrenom());
        labelCabinet.setText("nom du Cabinet");
        labelLieu.setText("le lieu ....");
        labelInfo.setText("les information .......");
        labelPatient.setText(patient.getNom().toUpperCase()+" "+patient.getPrenom());

        if(lettre!=null) {
            if (lettre.isEmpty()) {
                textLettre.setText("Aucune Lettre d'Orientation n'est redigee pour cette consultation.");
            } else
                textLettre.setText(lettre);
        }
        else
            textLettre.setText("Aucune Lettre d'Orientation n'est redigee pour cette consultation.");


        if((lettre!=null)&&(!lettre.equals(new String("null")))&& !lettre.isEmpty())
            textLettre.setText(lettre);
        else
            textLettre.setText("Aucune lettre n'est redigee pour cette consultation");
    }
    @FXML
    MenuItem printPreviewItem;
    @FXML
    MenuItem fermer;
    @FXML
    MenuItem printItem;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text textLettre;

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
    public void setArgs(String lettre, Stage stage, Patient patient, Medecin medecin) {
        this.patient=patient;
        this.stage=stage;
        this.medecin=medecin;
        this.lettre = lettre;
        setInfo();
    }

    public void close(){
        stage.close();
    }

    public void print(){
        MyPrinter.print(stage, anchorPane);
    }

    public void printPreview(){
        try {
            MyPrinter.preview(anchorPane);
        } catch (IOException e) {
            System.out.println("Lettre d'Orientation: printPreview: Exception");
            e.printStackTrace();
        }
    }

}
