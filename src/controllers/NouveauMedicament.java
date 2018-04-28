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
    NewOrdonnanceController main_window;

    public NouveauMedicament(){
        min_height=max_height=286;
        min_width =max_width =400;
    }
    public void initialize(){
        //this.getWindow().initModality(Modality.APPLICATION_MODAL);
        btnSave.setOnAction(event -> save());
    }

    public void init(HashMap map){
        if (map.containsKey("main_window")) {
            main_window = (NewOrdonnanceController) map.get("main_window");
        }

        else {
            modify = (boolean) map.get("modify");
            idMedicament = (int) map.get("idMed");
            medicamentsList = (MedicamentsList) map.get("Controller");
        }

    }

    private void save(){
        Medicament medicament;
        if(labNomCom.getText().isEmpty()||labNomSci.getText().isEmpty()||labType.getText().isEmpty())
            labWarning.setText("Remplir tous les champs !");
        else{
            medicament = new Medicament(labNomCom.getText(), labNomSci.getText(), labType.getText());
            if(Medicament.exist(medicament))
                labWarning.setText("Ce medicament existe deja !");
            else{

                if (main_window != null) {
                    Medicament.insertNewMed(medicament);
                    main_window.update();
                    getWindow().close();
                }

                else {
                    if(modify){
                        Medicament.modifieMed(idMedicament, medicament);
                    }else
                        Medicament.insertNewMed(medicament);
                    medicamentsList.refresh();
                    close();
                }

            }
        }
    }

    private void close(){
        this.getWindow().close();
    }
}
