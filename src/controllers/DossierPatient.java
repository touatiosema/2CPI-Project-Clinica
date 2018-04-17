package controllers;

import core.Auth;
import models.Medecin;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DossierPatient extends Controller {

    private Patient patient;

    private Medecin medecin;
    private Medecin tmp;
    private ArrayList<FicheDeConsultation> consultations ;
    private int patientID;
    private int medecinID = Auth.getUserID();
    private int index;
    @FXML
    Label labelNom;
    @FXML
    Label labelTaille;
    @FXML
    Label labelDateDeNaissance;
    @FXML
    Label labelAge;
    @FXML
    Label labelAddresse;
    @FXML
    Label labelTelephone;
    @FXML
    Label labelProfession;
    @FXML
    Label labelLieuDeTravail;
    @FXML
    Label labelGroupage;
    @FXML
    TextField synopsis;
    @FXML
    ListView<String> listView ;
    @FXML
    Button btnModifier;
    @FXML
    Label labelPrenom;
    @FXML
    TextField textMotif;
    @FXML
    TextField textClinique;
    @FXML
    Label labelPoids;
    @FXML
    TextField textSupplim;
    @FXML
    Label labelDate;
    @FXML
    Label labelTime;
    @FXML
    Button buttonLettre;
    @FXML
    Button buttonCertificat;
    @FXML
    Button buttonOrdonnance;
    @FXML
    Button buttonFicheBilan;
    @FXML
    TextField textDiagnositque;
    @FXML
    Label labelMedecin;
    @FXML
    Button buttonAjouterConsultation;

    @Override
    public void init(HashMap data) {
       this.patientID= (int) data.get("id");
        patient = new Patient();
        medecin = new Medecin();
        tmp=new Medecin();
        consultations = new ArrayList<>();
        patient.getPatient(patientID);
        medecin.getMedecin(medecinID);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //listView.getSelectionModel().selectFirst();
        refresh();
        setPatientInfo();
        btnModifier.setOnAction(event ->{
          patient.modifieSynopsis(synopsis.getText());
        });

    }
    public void refresh(){
        consultations = FicheDeConsultation.getConsultations(patientID);
        Collections.sort(consultations);
        setConsultations();

    }
    public DossierPatient(){
        width = 1000;
        height = 600;
    }

    public void setPatientInfo(){
      labelNom.setText(patient.getNom());
      labelPrenom.setText(patient.getPrenom());
      labelDateDeNaissance.setText(patient.getDateDeNaissance().toString());
      labelAge.setText(String.valueOf(patient.getAge()));
      labelAddresse.setText(patient.getAddress());
      labelTelephone.setText(patient.getTelephone());
      labelProfession.setText(patient.getProfession());
      labelLieuDeTravail.setText(patient.getLieuDeTravail());
      labelGroupage.setText(patient.getGroupage());
      labelTaille.setText(String.valueOf(patient.getTaille())+" cm");
      synopsis.setText(patient.getSynopsis());
  }


  public void setConsultations(){
        listView.getItems().remove(0, listView.getItems().size());

        for(FicheDeConsultation ficheDeConsultation : consultations){
            listView.getItems().add(ficheDeConsultation.getDate().toString());
        }
        listView.getSelectionModel().selectFirst();
        listView.setOnMouseClicked(e -> setConsultationInfo());
        setConsultationInfo();
        buttonLettre.setOnAction(e -> showLettreDeReorientation());
        buttonCertificat.setOnAction(e ->showCertificat());
        buttonOrdonnance.setOnAction(e -> showOrdonnance());
        buttonFicheBilan.setOnAction(e -> showFichBilan());
        buttonAjouterConsultation.setOnAction(e->showAjouterConsultation());

  }

    public void setConsultationInfo(){
        try{
            index =(int) listView.getSelectionModel().getSelectedIndices().toArray()[0];
            if (consultations.get(index).getExamenClinique()!=null)
                textClinique.setText(consultations.get(index).getExamenClinique());
            else
                textClinique.setText("aucun examen clinique n'est fait...");
            if(consultations.get(index).getIdMedecin()!=0){
                tmp.getMedecin(consultations.get(index).getIdMedecin());
                labelMedecin.setText(tmp.getNom()+" "+tmp.getPrenom());
            }else {
                labelMedecin.setText("INDETERMINER??");
            }
            if(consultations.get(index).getMotif()!=null)
                textMotif.setText(consultations.get(index).getMotif());
            else
                textMotif.setText("aucun motif n'est redige pour cette consultation...");
            if(consultations.get(index).getExamenSuppl()!=null)
                textSupplim.setText(consultations.get(index).getExamenSuppl());
            else
                textSupplim.setText("aucun examen supplimentaire n'est fait...");
            if(consultations.get(index).getPoids()>0)
                labelPoids.setText(String.valueOf(consultations.get(index).getPoids())+" kg");
            else
                labelPoids.setText("pas de mesure pour cette consultation");
            if(consultations.get(index).getDate().toString()!=null)
                labelDate.setText(consultations.get(index).getDate().toString());
            else
                labelDate.setText("Non Prise");
            if(consultations.get(index).getTime().toString()!=null)
                labelTime.setText(consultations.get(index).getTime().toString());
            else
                labelTime.setText("Non Prise");
            if(consultations.get(index).getDiagnostic()!=null)
                textDiagnositque.setText(consultations.get(index).getDiagnostic());
            else
                textDiagnositque.setText("Non Prise");


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


  private void showFichBilan(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/FicheBilan.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FicheBilan ficheBilan = (FicheBilan) fxmlLoader.getController();
            Stage stage = new Stage();
            ficheBilan.setArgs(consultations.get(index).getFicheBilan().getFicheBilan(), stage, patient, medecin, consultations.get(index).getIdMedecin());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Fiche Bilan");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException exp){
            System.out.println("[ERROR] Controllers: DossierPatient: showFicheBilan(): "+exp.getMessage());
        }
  }

  private void showCertificat(){
      try{
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Certificat.fxml"));
          Parent root1 = (Parent) fxmlLoader.load();
          Certificat certificat = (Certificat) fxmlLoader.getController();
          Stage stage = new Stage();
          certificat.setArgs(stage ,consultations.get(index).getCertificat(), patient, medecin);
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.setTitle("Certificat medical");
          stage.setScene(new Scene(root1));
          stage.show();
      }catch (IOException e){
          System.out.println("[ERROR] Controllers: DossierPatient: showCertificat(): ");
          e.printStackTrace();
      }

  }

  private void showLettreDeReorientation(){
        try{

            //System.out.println(consultations.get(index).getLettreDOrientation().length());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/lettreDorientation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            LettreDorientation lettreDorientation = (LettreDorientation) fxmlLoader.getController();
            Stage stage = new Stage();
            lettreDorientation.setArgs(consultations.get(index).getLettreDOrientation(), stage, patient, medecin);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Lettre de Reorientation");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println("Controllers: DossierPatient: showLettreDeReorientation: "+e.getMessage());
        }
    }

  private void showOrdonnance(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Ordonnance.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Ordonnance ordonnance = (Ordonnance) fxmlLoader.getController();
            Stage stage = new Stage();
            ordonnance.setArgs(stage ,consultations.get(index).getOrdonnance().getOrdonnances(), patient, medecin);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ordonnance");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println("[ERROR] Controllers: DossierPatient: showOrdonnance(): "+e.getMessage());
        }

    }

  private void showAjouterConsultation(){
      try{
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/AjouterConsultation.fxml"));
          Parent root1 = (Parent) fxmlLoader.load();
          AjouterConsultation ajouterConsultation = (AjouterConsultation) fxmlLoader.getController();
          Stage stage = new Stage();
          ajouterConsultation.setParam(stage , patient, medecin, ajouterConsultation, this);
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.setTitle("Ajouter fiche de consultation");
          stage.setScene(new Scene(root1));
          stage.show();
      }catch (IOException e){
          System.out.println("[ERROR] Controllers: DossierPatient: showAjouterConsultation(): "+e.getMessage());
      }

  }

}
