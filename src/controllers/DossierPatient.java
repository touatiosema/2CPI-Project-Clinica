package controllers;

import core.Auth;
import models.Medecin;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class DossierPatient extends Controller {


    private Patient patient;
    private Medecin medecin;
    private Medecin tmp;
    private ArrayList<FicheDeConsultation> consultations ;
    private int patientID;
    private int medecinID = Auth.getUserID();
    private int index;
   // private float position1 = 0.10f;
    //private float position2 = 0.48f;
    //    Label labelPrenom2;
    @FXML
    Label labelPrenom;


    @FXML
    TextField textMotif;
    @FXML
    TextField textClinique;
    @FXML
    Label labelPoids;

//    @FXML
//    Button buttonDevelpper;
//
//    @FXML
//    SplitPane splitPane;

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
        refresh();
        setPatientInfo();
        //setConsultationInfo();

    }
    // Label labelID;
    // @FXML
    public void refresh(){
        consultations = FicheDeConsultation.getConsultations(patientID);
        Collections.sort(consultations);
        setConsultations();

    }
    @FXML
    Label labelNom;
    // @FXML
    //  Label labelID2;
//    @FXML
//    Label labelNom2;
    @FXML
    Label labelTaille;
    @FXML
    Label labelDateDeNaissance;
    //    @FXML
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


    public DossierPatient(){
        width = 1000;
        height = 600;
    }
//    @FXML
//    public void initialize(){
//    }
////    public void changeDevider(){
//        double pos = splitPane.getDividerPositions()[0];
//        if(pos<=0.2)
//            splitPane.setDividerPositions(position2);
//        else
//            splitPane.setDividerPositions(position1);
//
//    }

    public void setPatientInfo(){
     // labelID.setText(String.valueOf(patient.getId()));
      labelNom.setText(patient.getNom());
      labelPrenom.setText(patient.getPrenom());
      //labelID2.setText(String.valueOf(patient.getId()));
      //labelNom2.setText(patient.getNom());
     // labelPrenom2.setText(patient.getPrenom());
      labelDateDeNaissance.setText(patient.getDateDeNaissance().toString());
      labelAge.setText(String.valueOf(patient.getAge()));
      labelAddresse.setText(patient.getAddress());
      labelTelephone.setText(patient.getTelephone());
      labelProfession.setText(patient.getProfession());
      labelLieuDeTravail.setText(patient.getLieuDeTravail());
      labelGroupage.setText(patient.getGroupage());
      labelTaille.setText(String.valueOf(patient.getTaille())+" cm");
      synopsis.setText(patient.getSynopsis());
      //buttonDevelpper.setOnAction(e -> changeDevider());


  }


  public void setConsultations(){
        listView.getItems().remove(0, listView.getItems().size());
        for(FicheDeConsultation ficheDeConsultation : consultations){
            listView.getItems().add(ficheDeConsultation.getDate().toString());
        }
        listView.setOnMouseClicked(e -> setConsultationInfo());
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
            ficheBilan.setArgs(consultations.get(index).getFicheBilan().getFicheBilan(), stage);
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
          //System.out.println(consultations.get(index).getLettreDOrientation());
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Certificat.fxml"));
          Parent root1 = (Parent) fxmlLoader.load();
          Certificat certificat = (Certificat) fxmlLoader.getController();
          Stage stage = new Stage();
          certificat.setArgs(stage ,consultations.get(index).getCertificat());
          stage.initModality(Modality.APPLICATION_MODAL);
          //stage.initStyle(StageStyle.UNDECORATED);
          stage.setTitle("Certificat medical");
          stage.setScene(new Scene(root1));
          stage.show();
      }catch (IOException e){
          System.out.println("[ERROR] Controllers: DossierPatient: showCertificat(): "+e.getMessage());
      }

  }
//
  private void showLettreDeReorientation(){
        try{

            //System.out.println(consultations.get(index).getLettreDOrientation().length());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/lettreDorientation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            LettreDorientation lettreDorientation = (LettreDorientation) fxmlLoader.getController();
            Stage stage = new Stage();
            lettreDorientation.setArgs(consultations.get(index).getLettreDOrientation(), stage);
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
            //System.out.println(consultations.get(index).getLettreDOrientation());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Ordonnance.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Ordonnance ordonnance = (Ordonnance) fxmlLoader.getController();
            Stage stage = new Stage();
            ordonnance.setArgs(stage ,consultations.get(index).getOrdonnance().getOrdonnances());
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
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
