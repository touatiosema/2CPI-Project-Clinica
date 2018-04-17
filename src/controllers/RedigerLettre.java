package controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Patient;

@SuppressWarnings("ALL")
public class RedigerLettre {

    //private Patient patient;
    //private Models.Medecin medecin;
    //private String modele;  // a developper vers une lettre plus convineint
    private Stage stage;
    private AjouterConsultation ajouterConsultation;
    private boolean lettreValide;
    @FXML
    Label labelNom;
    @FXML
    Label labelPrenom;
    @FXML
    Button buttonImprimer;
    @FXML
    Button buttonValider;
    @FXML
    Button buttonAnnuler;
    @FXML
    TextArea textLettre;
    @FXML
    Button buttonEffacer;
    @FXML
    Label labelInteraction;
    @FXML
    Label labelNom1;
    @FXML
    Label labelPrenom1;
    public void setParms(Stage stage, AjouterConsultation aj){
        //this.patient=patient;
        //this.medecin=medecin;
        this.stage=stage;
        this.ajouterConsultation=aj;
        //modele=aj.getLettreOrientation();
        lettreValide=aj.isLettreValide();
        if(lettreValide){
            buttonValider.setText("Unvalider");
            labelInteraction.setText("l'etat est valide.");
        }else{
            buttonValider.setText("Valider");
            labelInteraction.setText("l'etat est unvalide.");
        }
        textLettre.setText(aj.getLettreOrientation());
        labelNom.setText(aj.getPatient().getNom());
        labelPrenom.setText(aj.getPatient().getPrenom());
        labelNom1.setText(aj.getMedecin().getNom());
        labelPrenom1.setText(aj.getMedecin().getPrenom());
    }

    public void initialize(){
        buttonAnnuler.setOnAction(e -> close());
        buttonValider.setOnAction(e->valider());
        buttonEffacer.setOnAction(e->effacer());
        //textLettre.setText();
        labelNom.setOnMouseClicked(e->insertNom());
        labelPrenom.setOnMouseClicked(e->insertPrenom());
        labelNom1.setOnMouseClicked(e->insertNom1());
        labelPrenom1.setOnMouseClicked(e->insertPrenom1());
    }

    private void valider(){
        if(lettreValide){
            ajouterConsultation.setLettreValide(false);
            close();
        }else {
            try {
                if (textLettre.getText().isEmpty()) {
                    labelInteraction.setText("Introduire une lettre d'orientation avant de valider!");
                } else {
                    ajouterConsultation.setLettreOrientation(textLettre.getText());
                    ajouterConsultation.setLettreValide(true);
                    close();
                }
            }catch (NullPointerException e){

            }
        }
    }

    private void effacer(){
        textLettre.setText("");
    }

    private void insertNom(){
        textLettre.setText(textLettre.getText()+" "+ajouterConsultation.getPatient().getNom());
        textLettre.setCursor(Cursor.CROSSHAIR);
    }

    private void insertPrenom(){
        textLettre.setText(textLettre.getText()+" "+ajouterConsultation.getPatient().getPrenom());
    }

    private void insertNom1(){
        textLettre.setText(textLettre.getText()+" "+ajouterConsultation.getMedecin().getNom());
        textLettre.setCursor(Cursor.CROSSHAIR);
    }

    private void insertPrenom1(){
        textLettre.setText(textLettre.getText()+" "+ajouterConsultation.getMedecin().getPrenom());
    }

    private void close(){
        stage.close();
    }

}
