package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LettreDorientation {

    private String lettre;
    private Stage stage;
    public void initialize(){
        buttonRetour.setOnAction(e-> close());
    }
    public void setInfo(){
        if((lettre!=null)&&(!lettre.equals(new String("null")))&& !lettre.isEmpty())
            textLettre.setText(lettre);
        else
            textLettre.setText("Aucune lettre n'est redigee pour cette consultation");
    }
    @FXML
    TextField textLettre;
    @FXML
    Button buttonRetour;

    public void setArgs(String lettre, Stage stage) {
        this.stage=stage;
        this.lettre = lettre;
        setInfo();
    }

    public void close(){
        stage.close();
    }
}
