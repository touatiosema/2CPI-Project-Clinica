package controllers;

import core.MyPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Medecin;
import models.Patient;
import org.bouncycastle.asn1.dvcs.PathProcInput;

import java.io.IOException;
import java.util.Date;


public class Certificat {
    Stage stage;
    String certificat;
    Patient patient;
    Medecin medecin;
    @FXML
    MenuItem printPreviewItem;
    @FXML
    MenuItem fermer;
    @FXML
    MenuItem printItem;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text textCertificat;

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
        //setInfo();
            fermer.setOnAction(event -> close());
            printItem.setOnAction(event -> print());
            printPreviewItem.setOnAction(event -> printPreview());
    }
    public void setArgs(Stage stage, String string, Patient patient, Medecin medecin){
        this.stage=stage;
        this.patient=patient;
        this.medecin=medecin;
        certificat=string;
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

            if(certificat!=null) {
                if (certificat.isEmpty()) {
                    textCertificat.setText("Aucune certificat medical n'est redige pour cette consultation.");
                } else
                    textCertificat.setText(certificat);
            }
            else
                textCertificat.setText("Aucune certificat medical n'est redige pour cette consultation.");

    }

    private void close(){
        stage.close();
    }


    public void print(){
        MyPrinter.print(stage, anchorPane);
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
