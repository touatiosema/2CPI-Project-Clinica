package controllers;

import core.MyPrinter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Examen;
import models.LigneEnOrdonnance;
import models.Medecin;
import models.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class FicheBilan {

    private ArrayList<Examen> bilan;
    private Stage stage;
    private Patient patient;
    private Medecin medecinConnecte;
    private Medecin medecinRedigeat= new Medecin();
    @FXML
    MenuItem printPreviewItem;
    @FXML
    MenuItem fermer;
    @FXML
    MenuItem printItem;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Text textBilan;
    @FXML
    Label labelRedigeant;
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

    private void close(){
        stage.close();
    }

    public void setArgs(ArrayList<Examen> bilan, Stage stage, Patient patient, Medecin medecin, int idMedecin){
        this.stage=stage;
        this.bilan=bilan;
        this.medecinConnecte=medecin;
        this.patient=patient;
        medecinRedigeat.getMedecin(idMedecin);
        setInfo();
    }

    public void setInfo(){

        labelAge.setText(String.valueOf(patient.getAge())+" ans");
        labelDate.setText("le :"+new Date().toString());
        labelMedecin.setText("DR :"+medecinRedigeat.getNom().toUpperCase()+" "+medecinRedigeat.getPrenom());
        labelRedigeant.setText("Redige par:"+medecinRedigeat.getNom().toUpperCase()+" "+medecinRedigeat.getPrenom());
        labelCabinet.setText("nom du Cabinet");
        labelLieu.setText("le lieu ....");
        labelInfo.setText("les information .......");
        labelPatient.setText(patient.getNom().toUpperCase()+" "+patient.getPrenom());

        if(bilan==null || bilan.isEmpty()) {
            textBilan.setText("Aucun Bilan pour cette consultation!");
            return;
        }
        Collections.sort(bilan);
        Iterator<Examen> iterator = bilan.iterator();
        int i =0;
        while(iterator.hasNext()){
            Examen examen = iterator.next();
            textBilan.setText(textBilan.getText().concat(examen.getType()+" :")+'\n'+'\t');
            do{
                textBilan.setText(textBilan.getText().concat(examen.getName()+"    "));
                i++;
                if(i%3==0 )
                    textBilan.setText(textBilan.getText().concat(""+'\n'));


            }while(iterator.hasNext()&&iterator.next().getType().equals(examen.getType()));
        }

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
