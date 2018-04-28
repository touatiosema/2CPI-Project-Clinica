package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.*;
import utils.Print;

import java.util.ArrayList;
import java.util.HashMap;


public class NewOrdonnanceController extends Controller {

    @FXML AnchorPane anchorPane;
    @FXML Label labelMedecin;
    @FXML Label labelCabinet;
    @FXML Label labelLieu;
    @FXML Label labelDate;
    @FXML Label labelAge;
    @FXML Label labelPatient;

    @FXML
    JFXButton save_btn;

    @FXML
    TextField med_search;

    @FXML
    VBox ordonnance_list;

    @FXML
    ListView meds_list;

    @FXML JFXButton add_med_btn;

    FicheDeConsultation consultation;
    Medecin medecin;
    Patient patient;

    ArrayList<Medicament> meds;

    ConsultationController main_window;

    public NewOrdonnanceController() {
        min_width = width = max_width = 750;
        min_height = height = max_height = 670;
    }

    public void init(HashMap args) {
        int id = (int) args.get("id");
        consultation = new FicheDeConsultation(id);
        main_window = (ConsultationController) args.get("main_window");

        medecin = new Medecin(consultation.getIdMedecin());
        patient = new Patient(consultation.getIdPatient());

        labelAge.setText(patient.getAge() + " ans");
        labelDate.setText("Le " + consultation.getDate().toString() + " " + consultation.getTime().toString());
        labelMedecin.setText("Dr. " + medecin.getFullName());
        labelCabinet.setText(Config.get("cabinet_name"));
        labelLieu.setText(Config.get("cabinet_adresse"));
        labelPatient.setText(patient.getFullName());

        meds_list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList list = meds_list.getSelectionModel().getSelectedIndices();
                if (list.size() == 0) return;

                int i = (int) list.toArray()[0];
                add(meds.get(i));
            }
        });

        med_search.textProperty().addListener((v, oldValue, NewValue) -> update());
        update();


        save_btn.setOnAction(e -> {
            ArrayList<LigneEnOrdonnance> ordonnance = new ArrayList<LigneEnOrdonnance>();

            for (Node med_line: ordonnance_list.getChildren()) {
                int _id = Integer.parseInt(med_line.getId().substring(5));
                String dose = ((JFXTextField)((HBox) med_line).getChildren().get(1)).getText();
                String period = ((JFXTextField)((HBox) med_line).getChildren().get(2)).getText();

                ordonnance.add(new LigneEnOrdonnance(Medicament.getById(_id), dose, period));
            }

            for (LigneEnOrdonnance ligne : ordonnance) {
                ligne.saveLigneEnOrdonnance(consultation.getId());
            }

            main_window.setupActionButtons();
            App.setView(getWindow(), "ShowOrdonnance", new HashMap() {{
                put("id", consultation.getId());
            }});
        });

        NewOrdonnanceController self = this;

        add_med_btn.setOnAction(e -> {
            App.newWindow("NouveauMedicament", new HashMap() {{
                put("main_window", self);
            }});
        });
    }

    public void update() {
        meds = Medicament.search(med_search.getText());
        meds_list.getItems().clear();

        for (Medicament med : meds) {
            meds_list.getItems().add(med.getNomCommercial() + " - " + med.getType());
        }
    }

    public void add(Medicament med) {

        med_search.setText("");

        HBox hbox = new HBox();

        Button remove_btn = new Button();
        FontAwesomeIconView remove_icon = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);
        remove_icon.setFill(Color.valueOf("#309bd1"));
        remove_btn.setGraphic(remove_icon);
        remove_btn.setStyle("-fx-fill: #309bd1; -fx-background-color: transparent; -fx-cursor: hand;");
        remove_btn.setPadding(new Insets(5, 5, 5, 5));

        remove_btn.setOnAction(e -> {
            remove(hbox);
        });

        Label name = new Label(med.getNomCommercial().toUpperCase());
        name.setPadding(new Insets(5, 5, 5, 5));

        JFXTextField doze = new JFXTextField("1 mg");
        doze.setPrefWidth(50);
        doze.setFocusColor(Color.valueOf("#309bd1"));

        JFXTextField period = new JFXTextField("1 par jour");
        period.setPrefWidth(80);
        period.setFocusColor(Color.valueOf("#309bd1"));


        HBox lbox = new HBox();
        lbox.getChildren().addAll(remove_btn, name);
        lbox.setPrefWidth(200);

        hbox.setSpacing(15);
        hbox.getChildren().addAll(lbox, doze, period);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setId("line_" + med.getId());

        ordonnance_list.getChildren().add(hbox);
    }

    private void remove(HBox med_line) {
        ordonnance_list.getChildren().remove(med_line);
    }

    public void print() {
        Print.print(getWindow(), anchorPane);
    }
}
