package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import models.Medicament;

import java.util.HashMap;

public class NouveauMedicament extends Controller{

    private boolean modify;
    private int idMedicament;
    private MedicamentsList medicamentsList;
    @FXML
    Label labWarning;

    @FXML
    TextField labNomCom;
    @FXML
    TextField labNomSci;
    @FXML
    TextField labType;
    @FXML
    Button btnSave;
    @FXML
    Button btnCancel;

    public NouveauMedicament(){
        min_height=max_height=217;
        min_width =max_width =337;
    }
    public void initialize(){
        //this.getWindow().initModality(Modality.APPLICATION_MODAL);
        btnSave.setOnAction(event -> save());
        btnCancel.setOnAction(event -> close());
    }

    public void init(HashMap map){
        modify=(boolean) map.get("modify");
        idMedicament=(int) map.get("idMed");
        medicamentsList = (MedicamentsList) map.get("Controller");
    }

    private void save(){
        Medicament medicament;
        if(labNomCom.getText().isEmpty()||labNomSci.getText().isEmpty()||labType.getText().isEmpty())
            labWarning.setText("Remplir tous les champs!!");
        else{
            medicament = new Medicament(labNomCom.getText(), labNomSci.getText(), labType.getText());
            if(Medicament.exist(medicament))
                labWarning.setText("Ce medicament existe deja!");
            else{
                if(modify){
                  Medicament.modifieMed(idMedicament, medicament);
                }else
                    Medicament.insertNewMed(medicament);
                medicamentsList.refresh();
                close();
            }
        }
    }

    private void close(){
        this.getWindow().close();
    }
}
