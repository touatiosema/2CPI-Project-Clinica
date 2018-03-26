package controllers;

import models.Medecin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.FicheDeConsultation;
import models.*;
import models.Ordonnance;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
public class AjouterConsultation {

    @FXML
    private TextField textMotif;
    @FXML
    private TextField textPoids;
    @FXML
    private TextField textClinique;
    @FXML
    private TextField textSupplim;
    @FXML
    private TextField textDiagnositque;
    @FXML
    private Label labelDate;
    @FXML
    private Button buttonLettre;
    @FXML
    private Button buttonCertificat;
    @FXML
    private Button buttonOrdonnance;
    @FXML
    private Button buttonFicheBilan;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonClose;

    private Stage stage;
    AjouterConsultation ajouterConsultation;
    private Patient patient;
    private Medecin medecin;
    private FicheDeConsultation ficheDeConsultation;
    private String lettreOrientation;
    private boolean lettreValide = false;
    private boolean certificatValide = false;
    private String certificat;
    private Ordonnance ordonnance=new Ordonnance();
    private Bilan bilan=null;
    private int poids;
    private DossierPatient dossierPatient;
    public AjouterConsultation(){
        ficheDeConsultation=new FicheDeConsultation();
    }

    public void setOrdonnance(ArrayList<LigneEnOrdonnance> ligneEnOrdonnances){
        this.ordonnance.setOrdonnances(ligneEnOrdonnances);
    }

    public void initialize(){

        buttonSave.setOnAction(e -> valider());
        buttonClose.setOnAction(e -> close());
        buttonLettre.setOnAction(e-> redigerLettre());
        buttonCertificat.setOnAction(e->redigerCertificat());
        buttonOrdonnance.setOnAction(e->redigerOrdonnance());
        labelDate.setText((new Date()).toString());

    }

    public void setParam(Stage stage, Patient patient, Medecin medecin, AjouterConsultation aj, DossierPatient dp) {//add somme exception hadeling for null pointers
        this.stage=stage;
        this.ajouterConsultation=aj;
        this.dossierPatient=dp;
        if (patient == null || medecin == null) {
            System.out.println("introduire le medecin et le patient courant.");
            close();
        }else {
            this.patient = patient;
            this.medecin = medecin;
        }
    }

    private void valider(){
        if(textDiagnositque.getText().isEmpty()||!isPoidsInt()){//poids is a number...
            alertMessage();
        }else{
            prepare();
            save();
            close();
        }
    }

    private void save(){
        ficheDeConsultation.saveConsultation();
        dossierPatient.refresh();

    }

    private boolean isPoidsInt(){
        try{
            if(textPoids.getText().isEmpty())
                poids=-1;
            else
                poids= Integer.parseInt(textPoids.getText());
            return true;
        }catch (NumberFormatException exp){
            return false;
        }
    }

    private void close(){
        stage.close();
    }

    private void prepare(){
        ficheDeConsultation.setMotif(textMotif.getText());
        ficheDeConsultation.setPoids(poids);
        ficheDeConsultation.setExamenClinique(textClinique.getText());
        ficheDeConsultation.setDiagnostic(textDiagnositque.getText());
        ficheDeConsultation.setExamenSuppl(textSupplim.getText());
        ficheDeConsultation.setDate(new Date());
        ficheDeConsultation.setTime(Time.valueOf(LocalTime.now()));
        ficheDeConsultation.setIdMedecin(medecin.getId());
        ficheDeConsultation.setIdPatient(patient.getId());
        ficheDeConsultation.setFicheBilan(bilan);
        ficheDeConsultation.setOrdonnance(ordonnance);
        if(certificatValide)
            ficheDeConsultation.setCertificat(certificat);
        if(lettreValide)
            ficheDeConsultation.setLettreDOrientation(this.lettreOrientation);
        ficheDeConsultation.afficher();
    }

    private void alertMessage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/alertMessage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            AlertMessage alertMessage = (AlertMessage) fxmlLoader.getController();
            alertMessage.setParams("introduire les champs necessaire...", stage);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Avertissement");
            stage.show();
        }catch (IOException e){
            System.out.println("AjouterConsultation: alertMessage(): "+e.getMessage()+e.toString());
        }
    }

    private void redigerLettre(){
        try{
            System.out.println(this.lettreOrientation);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/RedigerLettre.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            RedigerLettre redigerLettre = (RedigerLettre) fxmlLoader.getController();
            Stage stage = new Stage();
            redigerLettre.setParms(stage, ajouterConsultation);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("redaction de la lettre de Reorientation");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println("Controllers: DossierPatient: showLettreDeReorientation: "+
                    e.getMessage());
        }

    }

    private void redigerCertificat(){
        try{
            System.out.println(this.lettreOrientation);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/RedigerCertificat.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            RedigerCertificat redigerCertificat = (RedigerCertificat ) fxmlLoader.getController();
            Stage stage = new Stage();
            redigerCertificat.setParms(stage, ajouterConsultation);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("redaction du certificat Medical");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println("Controllers: ajouterConsultation: redigerCertificat(): "+
                    e.getCause());
        }

    }
    private void redigerOrdonnance() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/OrdonnanceSellma.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            OrdonnanceController redigerCertificat = (OrdonnanceController) fxmlLoader.getController();
            Stage stage = new Stage();
            redigerCertificat.setArgs( ajouterConsultation, stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Redaction d'une Ordonnance");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            System.out.println("Controllers: ajouterConsultation: redigerOrdonnance(): " +
                    e.getCause());
        }
    }



    public void setLettreOrientation(String lettreOrientation) {
        this.lettreOrientation = lettreOrientation;
    }

    public String  getLettreOrientation(){
        return lettreOrientation;
    }
    public boolean isLettreValide() {
        return lettreValide;
    }

    public boolean isCertificatValide() {
        return certificatValide;
    }

    public void setLettreValide(boolean lettreValide) {
        this.lettreValide = lettreValide;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setCertificat(String certificat) {
        this.certificat = certificat;
    }

    public String getCertificat() {
        return certificat;
    }

    public void setCertificatValide(boolean certificatValide) {
        this.certificatValide = certificatValide;
    }
}

