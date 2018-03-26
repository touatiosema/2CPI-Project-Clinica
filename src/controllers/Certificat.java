package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Certificat {
    Stage stage;
    String certificat;

    public void initialize(){
        setInfo();
        buttonRetour.setOnAction(e -> close());
    }

    public void setArgs(Stage stage, String string){
        this.stage=stage;
        certificat=string;
        setInfo();
    }
    @FXML
    TextArea textCertificat;
    @FXML
    Button buttonRetour;

    private void setInfo(){
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

}
