package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertMessage extends Controller {
    private String message="avertissement";
    private Stage stage;

    public void initialize(){
        buttonFermer.setOnAction(e->close());
    }

    @FXML
    Label labelMessage;
    @FXML
    Button buttonFermer;

    public void setParams(String message, Stage stage){
        this.message=message;
        this.stage=stage;
        setMessage();
    }
    private void  close(){
        stage.close();
    }

    private void setMessage(){
        labelMessage.setText(message);
    }

}
