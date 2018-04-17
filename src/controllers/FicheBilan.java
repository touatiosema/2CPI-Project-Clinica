package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FicheBilan {

    private ArrayList<String> bilan;
    private Stage stage;

    @FXML
    ListView<String> listView;
    @FXML
    Button buttonRetour;

    public void initialize(){
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        buttonRetour.setOnAction(e->close());
    }

    private void close(){
        stage.close();
    }

    public void setArgs(ArrayList<String> bilan, Stage stage){
        this.stage=stage;
        this.bilan=bilan;
        setInfo();
    }

    public void setInfo(){
        listView.getItems().removeAll();
        if(bilan.isEmpty()) {
            listView.getItems().add("Pas de Bilan pour cette consultation...");
        }
        else
            for(String str : bilan){
                listView.getItems().add(str);
            }
    }

}
