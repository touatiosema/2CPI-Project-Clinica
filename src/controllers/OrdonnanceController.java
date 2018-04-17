package controllers;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.AlertBox;
import models.LigneEnOrdonnance;
import models.Medicament;

import models.Ordonnance;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


public class OrdonnanceController extends Controller  {
    public static int  Id=4 ;
    private AjouterConsultation ajouterConsultation;
    public static ArrayList <TextField> Meds=new ArrayList<TextField>();
    public static ArrayList <ChoiceBox> Pos=new ArrayList<ChoiceBox>();
    public static ArrayList <ChoiceBox> Num=new ArrayList<ChoiceBox>();
    public static ArrayList <ChoiceBox> Per=new ArrayList<ChoiceBox>();
    @FXML
    private ResourceBundle resources;

    public void setArgs(AjouterConsultation ajouterCons, Stage stage){
        ajouterConsultation=ajouterCons;
        this.stage=stage;
    }


    private Stage stage;
    @FXML
    private URL location;
    @FXML
    private TextField med1=TextFields.createClearableTextField();
    @FXML
    private TextField med2;
    @FXML
    private TextField med3;
    @FXML
    private TextField med4;
    @FXML
    private ChoiceBox<String> posologie1;
    @FXML
    private ChoiceBox<String> posologie2;

    @FXML
    private ChoiceBox<String> posologie3;

    @FXML
    private ChoiceBox<String> posologie4;
    @FXML
    private ChoiceBox<String> Num4;
    @FXML
    private ChoiceBox<String> Num3;
    @FXML
    private ChoiceBox<String> Num2;
    @FXML
    private ChoiceBox<String> Num1;
    @FXML
    private ChoiceBox<String> periode1;
    @FXML
    private ChoiceBox<String> periode2;
    @FXML
    private ChoiceBox<String> periode3;
    @FXML
    private ChoiceBox<String> periode4;
    @FXML
    private Button ajouter;
    @FXML
    private GridPane Gridpane;
    @FXML
    private AnchorPane anchortaille;

    @FXML
    private ScrollPane scroll;

    @FXML
    private HBox mainhbox;


    @FXML
    private Region region;
    @FXML
    private VBox medsVbox;

    @FXML
    private VBox duréeVbox;


    @FXML
    private VBox posologieVbox;
    public ArrayList<TextField> sauvegarderText(TextField T,  ArrayList <TextField> Meds){
        Meds.add(T);
        return Meds;
    }
    public ArrayList<ChoiceBox> sauvegarderText(ChoiceBox T,  ArrayList <ChoiceBox> P){
        P.add(T);
        return P;
    }
    @FXML public ArrayList <LigneEnOrdonnance> validerClicked(){
       ArrayList <LigneEnOrdonnance> LigneEnOrd=new ArrayList<LigneEnOrdonnance>();
       if(med1.getText().isEmpty()&& med2.getText().isEmpty()&&med3.getText().isEmpty()&&med4.getText().isEmpty()){
           AlertBox.AlertMssg("Erreur", "Vous n'avez rien entrer!");
       }
       if(med1.getText().isEmpty()==false) {
           Medicament medi = new Medicament(med1.getText(), "", "");
           String P = posologie1.getValue();
           String Du = Num1.getValue() + periode1.getValue();

           LigneEnOrdonnance li = new LigneEnOrdonnance(medi, P, Du);

           LigneEnOrd.add(li);
       }
       if(med2.getText().isEmpty()==false){
               Medicament medi = new Medicament(med2.getText(),"","");
               String P=posologie2.getValue();
               String Du=Num2.getValue() + periode2.getValue();

               LigneEnOrdonnance li=new LigneEnOrdonnance(medi,P,Du);
               LigneEnOrd.add(li);

       }
        if(med3.getText().isEmpty()==false) {
            Medicament medi = new Medicament(med3.getText(), "", "");
            String P = posologie3.getValue();
            String Du = Num3.getValue() + periode3.getValue();

            LigneEnOrdonnance li = new LigneEnOrdonnance(medi, P, Du);
            LigneEnOrd.add(li);
        }
        if(med4.getText().isEmpty()==false) {
            Medicament medi = new Medicament(med4.getText(), "", "");
            String P = posologie4.getValue();
            String Du = Num4.getValue() + periode4.getValue();

            LigneEnOrdonnance li = new LigneEnOrdonnance(medi, P, Du);
            LigneEnOrd.add(li);
        }
        Iterator<TextField> it = Meds.iterator();
        while (it.hasNext()) {
            TextField p1 = it.next();

            if (p1.getText().isEmpty()){AlertBox.AlertMssg("alert", "veuillez remplir les champs vides");}
            else {
                Medicament medi = new Medicament(p1.getText(), "", "");
                int index=Meds.indexOf(p1);

                ChoiceBox <String> A = Pos.get(index);
                String P= A.getValue();
                ChoiceBox <String> B = Num.get(index);
                String N= B.getValue();
                ChoiceBox <String> C = Per.get(index);
                String Pe= C.getValue();
                String Du = N+Pe;
                LigneEnOrdonnance li = new LigneEnOrdonnance(medi, P, Du);
                LigneEnOrd.add(li);
            }
        }
        saveAndExite(LigneEnOrd);
            return LigneEnOrd;



    }
    @FXML
    public void initChoice(){
        posologie1.getItems().addAll("1/j","2/j","3/j","4/j","1/2 par j");
        posologie1.setValue("1/j");

        posologie2.getItems().addAll("1/j","2/j","3/j","4/j","1/2 par j");
        posologie2.setValue("1/j");

        posologie3.getItems().addAll("1/j","2/j","3/j","4/j","1/2 par j");
        posologie3.setValue("1/j");

        posologie4.getItems().addAll("1/j","2/j","3/j","4/j","1/2 par j");
        posologie4.setValue("1/j");

        Num4.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11");
        Num4.setValue("1");

        Num3.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11");
        Num3.setValue("1");

        Num2.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11");
        Num2.setValue("1");

        Num1.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11");
        Num1.setValue("1");

       periode1.getItems().addAll("jour","semaine","mois");
        periode1.setValue("jour");

        periode2.getItems().addAll("jour","semaine","mois");
        periode2.setValue("jour");

        periode3.getItems().addAll("jour","semaine","mois");
        periode3.setValue("jour");

        periode4.getItems().addAll("jour","semaine","mois");
        periode4.setValue("jour");

    }
    @FXML
    public void initialize(){

        initChoice();
        autocomplete();


    }

    public OrdonnanceController() {
        title = "Ordonnance";
        width = 600;
        height = 600;
    }
    @FXML
    public void ajouterDansScrollPane(ActionEvent event) {


        Iterator<TextField> it = Meds.iterator();
        while (it.hasNext()) {
            TextField p1 = it.next();

            if (p1.getText().isEmpty()){AlertBox.AlertMssg("alert", "veuillez remplir les champs vides");}

        }


        if (med1.getText().isEmpty() || med2.getText().isEmpty() || med3.getText().isEmpty() || med4.getText().isEmpty()) {

            AlertBox.AlertMssg("alert", "veuillez remplir les champs vides");



        }
        else {


            ChoiceBox<String> pos = new ChoiceBox<>();
            pos.getItems().addAll("1/j", "2/j", "3/j", "4/j", "1/2 par j");
            Pos.add(pos);
            pos.setValue("1/j");
            pos.setPrefWidth(46);

/*****************************************************/
            ChoiceBox<String> dur1 = new ChoiceBox<>();
            dur1.getItems().addAll("jour", "semaine", "mois");
            Per.add(dur1);
            dur1.setValue("jour");
            dur1.setPrefWidth(46);

            ChoiceBox<String> dur2 = new ChoiceBox<>();
            dur2.getItems().addAll("1", "2", "3", "4", "5", "6","7","8","9","10","11");
            Num.add(dur2);
            dur2.setValue("1");
            dur2.setPrefWidth(46);

            HBox durhbox = new HBox();
            durhbox.setSpacing(5);
            durhbox.getChildren().addAll(dur2, dur1);
            /***************************************************/
            TextField text = new TextField();
            text.setId("med"+Id);
            Meds=sauvegarderText(text,Meds);
            Id++;
            text.setPromptText("entrez le med");
            anchortaille.setPrefHeight(anchortaille.getHeight() + 500);
            medsVbox.getChildren().add(text);
            posologieVbox.getChildren().add(pos);
            duréeVbox.setSpacing(10);
            duréeVbox.getChildren().add(durhbox);
            /****************************************************/

    }


    }
    public void afficherListeOrd(ArrayList <LigneEnOrdonnance> e){
        Iterator<LigneEnOrdonnance> it = e.iterator();
        while (it.hasNext()) {
            LigneEnOrdonnance p1 = it.next();
            String deleteme=p1.getMedicament().getNomCommercial();
            String deleteme1=p1.getDetails();
            System.out.println(deleteme1);
        }

    }

    public void saveAndExite(ArrayList<LigneEnOrdonnance> moh){
        ajouterConsultation.setOrdonnance(moh);
        stage.close();
    }



    public  void autocomplete()  {



        ArrayList<String> M= Ordonnance.getMedList();

    TextFields.bindAutoCompletion(med1,M);
        TextFields.bindAutoCompletion(med2,M);
        TextFields.bindAutoCompletion(med3,M);
        TextFields.bindAutoCompletion(med4,M);


    }




}


