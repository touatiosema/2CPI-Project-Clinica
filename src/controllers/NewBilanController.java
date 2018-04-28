package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import core.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.*;
import utils.Print;

import java.util.ArrayList;
import java.util.HashMap;


public class NewBilanController extends Controller {

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
    TextField exam_search;

    @FXML
    VBox bilan_list;

    @FXML
    JFXListView exams_list;

    @FXML JFXButton add_exam_btn;

    FicheDeConsultation consultation;
    Medecin medecin;
    Patient patient;

    ArrayList<Examen> exams;

    ConsultationController main_window;

    public NewBilanController() {
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

        exams_list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList list = exams_list.getSelectionModel().getSelectedIndices();
                if (list.size() == 0) return;

                add(exams.get((int) list.toArray()[0]));
            }
        });

        exam_search.textProperty().addListener((v, oldValue, NewValue) -> update());
        update();


        save_btn.setOnAction(e -> {
            ArrayList<Examen> bilan = new ArrayList<Examen>();

            for (Node line: bilan_list.getChildren()) {
                int _id = Integer.parseInt(line.getId().substring(5));

                bilan.add(new Examen(_id));
            }


            Bilan b = new Bilan(bilan);
            b.setConsultationId(consultation.getId());
            b.save();

            main_window.setupActionButtons();
            App.setView(getWindow(), "ShowBilan", new HashMap() {{
                put("id", consultation.getId());
            }});
        });

        NewBilanController self = this;

        add_exam_btn.setOnAction(e -> {
            App.newWindow("NewExamen", new HashMap() {{
                put("main_window", self);
            }});
        });
    }

    public void update() {
        exams = Examen.search(exam_search.getText());
        exams_list.getItems().clear();

        for (Examen exam : exams) {
            exams_list.getItems().add(exam.getName());
        }
    }

    public void add(Examen exam) {

        exam_search.setText("");

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

        Label name = new Label(exam.getName().toUpperCase());
        name.setPadding(new Insets(5, 5, 5, 5));

        hbox.setSpacing(15);
        hbox.getChildren().addAll(remove_btn, name);
        hbox.setPadding(new Insets(10, 10, 5, 10));
        hbox.setId("line_" + exam.getId());

        bilan_list.getChildren().add(hbox);
    }

    private void remove(HBox med_line) {
        bilan_list.getChildren().remove(med_line);
    }

    public void print() {
        Print.print(getWindow(), anchorPane);
    }
}
