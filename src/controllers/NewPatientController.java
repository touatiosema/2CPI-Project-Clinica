package controllers;

import core.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Patient;
import models.Personne;

import java.sql.Date;
import java.util.HashMap;

public class NewPatientController extends Controller {
    private PatientsController main_window;

    @FXML
    TextField Name;

    @FXML
    TextField lastname;

    @FXML
    ChoiceBox genreBox;

    @FXML
    DatePicker date;

    @FXML
    TextField num ;

    @FXML
    TextField adresse;

    @FXML
    TextField travaille;

    @FXML
    TextField lieuTrav;

    @FXML
    TextArea synopsis;

    @FXML
    TextField taille;

    @FXML
    ChoiceBox groupageBox;

    @FXML
    public void init(HashMap args){
        main_window = (PatientsController) args.get("main_window");

        groupageBox.getItems().addAll("AB+","AB-","A+","A-"
                ,"B+","B-","O+","O-");
        groupageBox.setValue("AB+");

        genreBox.getItems().addAll("M","F");
        genreBox.setValue("M");

    }
    public NewPatientController() {
        title = "Nouveau Patient";
        min_width = width = max_width = 400;
        min_height = height = max_height = 450;
    }


    public void sauvgarder() {

        if (Name.getText().length() != 0 && lastname.getText().length() !=0 && adresse.getText().length()!=0
                && genreBox.getSelectionModel().getSelectedItem().toString().length() !=0 && date.getValue() != null
                && num.getText().length() !=0 &&
                groupageBox.getSelectionModel().getSelectedItem().toString().length()!=0 &&
                taille.getText().length() !=0 && travaille.getText().length() !=0 && lieuTrav.getText().length()!=0 ){

            int t = -1;

            try {
                t = Integer.parseInt(taille.getText());
            }

            catch (NumberFormatException e) {
                alert("Attention !", "La valeur de la taille doit etre un nombre", Alert.AlertType.ERROR);
            }


            if (t != -1) {
                Personne P = new Personne();
                P.setNom(Name.getText());
                P.setPrenom(lastname.getText());
                P.setAddress(adresse.getText());
                P.setTelephone(num.getText());
                P.setGenre(genreBox.getSelectionModel().getSelectedItem().toString().charAt(0));
                P.setDateDeNaissance(Date.valueOf(date.getValue()));
                P.save();

                Patient Pat = new Patient();
                Pat.setId_personne(P.getId());

                if (synopsis.getText().length() ==0){synopsis.setText(""); }
                Pat.setSynopsis(synopsis.getText());

                Pat.setProfession(travaille.getText());

                Pat.setLieuDeTravail(lieuTrav.getText());

                Pat.setGroupage(groupageBox.getSelectionModel().getSelectedItem().toString());


                Pat.setTaille(t);
                Pat.save();
                alert("Terminer", "Votre patient est ajouté avec succès ! ", Alert.AlertType.INFORMATION);
                main_window.update();
                getWindow().close();
            }

        }

        else {
            alert("Attention ! ", "Veuillez compléter les champs nécessaire !  ", Alert.AlertType.ERROR);
        }

    }


    public void consultWindow(ActionEvent actionEvent) {
        if (Name.getText().length() != 0 && lastname.getText().length() !=0 && adresse.getText().length()!=0
                && genreBox.getSelectionModel().getSelectedItem().toString().length() !=0 && date.toString().length() != 0
                && num.getText().length() !=0 &&
                groupageBox.getSelectionModel().getSelectedItem().toString().length()!=0 &&
                taille.getText().length() !=0
                && travaille.getText().length() !=0 && lieuTrav.getText().length()!=0 ) {
            Stage window = App.newWindow("Consultation", new HashMap());
        }

        else {
            alert("Attention ! ", "Veuillez inserer les informations de patient", Alert.AlertType.WARNING);
        }
    }

    public void alert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}













  
