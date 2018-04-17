package controllers;

import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import models.Medecin;
import models.Medicament;
import models.Medicament;
import utils.ActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MedicamentsList extends Controller {
    @FXML
    TableView<Medicament> tableView ;
    @FXML
    TableColumn<Medicament, String> colType;
    @FXML
    TableColumn<Medicament, String> colNomSci;
    @FXML
    TableColumn<Medicament, String> colNomCom;
    @FXML
    Button btnRetour;
    @FXML
    Button btnAdd;
    @FXML
    TextField textRecherche;
    TableColumn<Medicament, Void> colOperation;
    ObservableList<Medicament> medsList;
    public MedicamentsList(){
        min_height=499;
        min_width=629;
    }

    public void initialize() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colNomCom.setCellValueFactory(new PropertyValueFactory<>("nomCommercial"));
        colNomSci.setCellValueFactory(new PropertyValueFactory<>("nomSientifique"));
        //tableView.setItems(getData());
        getData();
        btnRetour.setOnAction(event -> {
            App.setView("Acceuil");
        });
        btnAdd.setOnAction(event -> {
            modify(-1, false);
        });
        addActionsButton();

    }

    private void getData(){
        medsList= FXCollections.observableArrayList();
        medsList.addAll(Medicament.getAllMedicament());
        FilteredList<Medicament> filteredList = new FilteredList<>(medsList, p -> true);
        textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(medicament -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if (medicament.getNomCommercial().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (medicament.getNomSientifique().toLowerCase().contains(lowercaseFilter))
                    return true;
                else if (medicament.getType().toLowerCase().contains(lowercaseFilter)) {
                    return !false;
                }
                return false;
            });
        });
        SortedList<Medicament> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

      //  return medsList;
    }

    private void addActionsButton() {
        if (colOperation != null)
            tableView.getColumns().removeAll(colOperation);
        colOperation = new TableColumn<>("Modifier");
        Callback<TableColumn<Medicament, Void>, TableCell<Medicament, Void>> cellFactory = new Callback<TableColumn<Medicament, Void>, TableCell<Medicament, Void>>() {
            @Override
            public TableCell<Medicament, Void> call(final TableColumn<Medicament, Void> param) {
                final TableCell<Medicament, Void> cell = new TableCell<Medicament, Void>() {
                    private Node create() {
                        HBox layout = new HBox();
//                        Medicament medicament = this.getTableView().getItems().get(this.getIndex());
                        int id = this.getTableView().getItems().get(this.getIndex()).getId();
                        int ligne = this.getIndex();
//                        ActionButton deleteBtn;
//                        deleteBtn = new ActionButton(this, "Supprimer", FontAwesomeIcon.CLOSE, "#B4302A") {
//                            @Override
//                            public void onAction(Object med) {
//                                delete(id, ligne);
//                            }
//                        };

                        ActionButton modifieBtn = new ActionButton(this, "Modifier", FontAwesomeIcon.RECYCLE, "#2c81a0" ){
                            @Override
                            public void onAction(Object med) {
                                modify(id, true);
                            }
                        };



                        layout.getChildren().addAll(modifieBtn);
                        return layout;
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(create());
                        }
                    }
                };
                return cell;
            }
        };
        colOperation.setCellFactory(cellFactory);
        tableView.getColumns().add(colOperation);
    }

//    private void delete(int id, int ligne){
//        //show some alert message.
//        tableView.getItems().remove(ligne);
//        //Medicament.removeMed(id);
//        System.out.println("id ="+id);
//    }
    private void modify(int id , boolean how){
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("idMed",id);
        hashMap.put("modify", how);
        hashMap.put("Controller", this);
        App.newWindow("NouveauMedicament", hashMap);
        }

    public void refresh(){
        tableView.getItems().removeAll();
       // tableView.setItems(getData());
        getData();
    }
}


