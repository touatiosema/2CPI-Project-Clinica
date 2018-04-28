package controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Print;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;


public class PreviewController extends Controller{
    private Node node;

    @FXML
    VBox image_container;

    @FXML
    ImageView imageView;
    @FXML
    Button print;

    @FXML
    Button zoomIn;
    @FXML
    Button zoomOut;
    @FXML
    JFXComboBox<String> format;
    @FXML
    JFXComboBox<String> direction;
    @FXML
    JFXComboBox<String> margins_box;
    @FXML
    Button reset;

    double scale = 1;
    int margin_index = 0;
    double margin_x;
    double margin_y;

    Image img;

    public PreviewController() {
        title = "Apercue Avant Impression";
        width = 480;
        height = 650;
    }

    public void init(HashMap args) {
        node = (Node) args.get("node");
        img = node.snapshot(new SnapshotParameters(), null);
        update();
    }

    public void initialize(){

        fillChoiceBox();

        print.setOnAction(event -> {
            Paper paper;
            switch (format.getSelectionModel().getSelectedIndex()){
                case 1:
                paper=Paper.A4;
                break;
               default:
                   paper=Paper.A5;
            }

            PageOrientation pagerOrientation;
            switch (direction.getSelectionModel().getSelectedIndex()) {
                case 1:
                    pagerOrientation = PageOrientation.LANDSCAPE;
                    break;
                default:
                    pagerOrientation = PageOrientation.PORTRAIT;
            }

            Print.print(node, margin_y, margin_y, margin_x, margin_x, paper, pagerOrientation );
        ;
        });

        reset.setOnAction(event -> { scale = 1; update(); });
        zoomIn.setOnAction(event -> { scale *= 1.1; update(); });
        zoomOut.setOnAction(event -> { scale /= 1.1; update(); });

        direction.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)) {
                update();
            }
        });

        format.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               update();
            }
        });

        margins_box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                margin_index = (int) newValue;
                update();
            }
        });

        direction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                update();
            }
        });

    }

    private void fillChoiceBox(){
        format.getItems().addAll("A5", "A4");
        format.getSelectionModel().selectFirst();
        direction.getItems().addAll("Portrait","Paysage");
        direction.getSelectionModel().selectFirst();
        margins_box.getItems().addAll(  "Ultra Norrow: (0.64 cm) x 4", "Narrow: (1.27 cm) x 4", "Normal: (2.5 cm) x 4 ");
        margins_box.getSelectionModel().selectFirst();
    }

    private void update() {
        String page = format.getValue();
        boolean landscape = direction.getValue().equals("Paysage");

        double height, width, rotation = 0;

        if (page.equals("A5")) width = 421;
        else width = 608;

        height = Math.sqrt(2) * width;

        if (landscape) {
            rotation = -90;
        }

        width *= scale;
        height *= scale;


        switch (margin_index){
            case 0:
                margin_x = margin_y = 0;
                break;
            case 1:
                margin_x = margin_y = 18;
                break;
            case 2:
                margin_x = margin_y = 53;
                break;
            default:
                margin_x = margin_y = 0;
        }

        margin_x *= scale;
        margin_y *= scale;

        width -= 2 * margin_x;
        height -= 2 * margin_y;


        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setImage(img);

        if (landscape) {
            AnchorPane.setLeftAnchor(image_container, 70.0);
            AnchorPane.setTopAnchor(image_container, -40.0);
        }

        else {
            AnchorPane.setLeftAnchor(image_container, 0.0);
            AnchorPane.setTopAnchor(image_container, 0.0);
        }

        image_container.setRotate(rotation);
        image_container.setPadding(new Insets(margin_x, margin_y, margin_x, margin_y));


    }
}
