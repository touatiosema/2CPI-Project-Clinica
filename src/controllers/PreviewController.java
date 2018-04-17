package controllers;

import core.MyPrinter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;



public class PreviewController {
    private Node node;

    double initUp=18;
    double initLeft=18;
    double initRight=18;
    double initDown=18;

    private double widthRec=421;
    private double heightRec=608;

    private double marginUp=initUp;
    private double marginDown=initDown;
    private double marginLeft=initLeft;
    private double marginRight=initRight;

    double valueX=initLeft;
    double valueY=initUp;




    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView imageView;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Button print;
    @FXML
    Rectangle rectangle;
    @FXML
    Button zoomIn;
    @FXML
    Button zoomOut;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    ChoiceBox<String> choiceBox1;
    @FXML
    ChoiceBox<String> choiceBox2;
    @FXML
    Button reset;
    public void putNode(Node node){
        this.node=node;
        set();
    }

    public void initialize(){
        reset();
        fillChoiceBox();
        print.setOnAction(event -> {
            Paper paper;
            switch (choiceBox.getSelectionModel().getSelectedIndex()){
                case 1:
                paper=Paper.A4;
                break;
               default:
                   paper=Paper.A5;
            }
            PageOrientation pagerOrientation;
            switch (choiceBox1.getSelectionModel().getSelectedIndex()) {
                case 1:
                    pagerOrientation = PageOrientation.LANDSCAPE;
                    break;
                default:
                    pagerOrientation = PageOrientation.PORTRAIT;
            }

            MyPrinter.print(imageView, marginUp, marginDown, marginLeft, marginRight, paper, pagerOrientation );
        });

        reset.setOnAction(event -> {
            reset();
        });


        zoomIn.setOnAction(event -> {
            imageView.setFitHeight(imageView.getFitHeight()*1.1);
            imageView.setFitWidth(imageView.getFitWidth()*1.1);
            rectangle.setWidth(rectangle.getWidth()*1.1);
            rectangle.setHeight(rectangle.getHeight()*1.1);
            valueY*=1.1;
            valueX*=1.1;
            AnchorPane.setLeftAnchor(imageView,valueX);
            AnchorPane.setTopAnchor(imageView,valueY);

        });
        zoomOut.setOnAction(event -> {
            imageView.setFitHeight(imageView.getFitHeight()/1.1);
            imageView.setFitWidth(imageView.getFitWidth()/1.1);
            rectangle.setWidth(rectangle.getWidth()/1.1);
            rectangle.setHeight(rectangle.getHeight()/1.1);
            valueY/=1.1;
            valueX/=1.1;
            AnchorPane.setLeftAnchor(imageView,valueX);
            AnchorPane.setTopAnchor(imageView,valueY);

        });
        choiceBox1.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)) {
                double x = widthRec;
                widthRec = heightRec;
                heightRec = x;
                set();
            }
        });
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               if((Integer)newValue==0){
                   widthRec=421;
                   heightRec=608;
               }
               if((Integer)newValue==1){
                   widthRec=608;
                   heightRec=842;
               }
               choiceBox1.getSelectionModel().selectFirst();
               set();
            }
        });
        choiceBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                set();
                switch ((int)newValue){
                    case 0:
                        marginDown=marginLeft=marginRight=marginUp=18;
                        break;
                    case 1:
                        marginDown=marginLeft=marginRight=marginUp=36;
                        break;
                    case 2:
                        marginDown=marginUp=72;
                        marginRight=marginLeft=54;
                        break;
                    case 3:
                        marginLeft=marginRight=marginUp=marginDown=71;
                        break;
                    default:
                        marginDown=marginLeft=marginRight=marginUp=18;
                }
                valueX=marginLeft;
                valueY=marginUp;
                WritableImage image = node.snapshot(new SnapshotParameters(), null);
                imageView.setFitWidth(image.getWidth());
                imageView.setFitHeight(image.getHeight());
                imageView.setImage(image);
//                if(imageView.getFitHeight()+marginDown>heightRec) {
//                    imageView.setFitHeight(heightRec-2*marginDown);
//                }
//                if(imageView.getFitWidth()+marginLeft>widthRec)
//                    imageView.setFitWidth(widthRec-2*marginLeft);
                imageView.setFitHeight(heightRec-2*marginUp);
                imageView.setFitWidth(widthRec-2*marginLeft);
                AnchorPane.setTopAnchor(imageView, marginUp);
                AnchorPane.setLeftAnchor(imageView, marginLeft);
            }
        });

    }

    private void reset() {
        marginRight=initRight;
        marginLeft=initLeft;
        marginDown=initDown;
        marginUp=initUp;
        choiceBox.getSelectionModel().selectFirst();
        choiceBox1.getSelectionModel().selectFirst();
        choiceBox2.getSelectionModel().selectFirst();
        set();
    }
    public void set(){
        if(node!=null) {
            WritableImage image = node.snapshot(new SnapshotParameters(), null);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
        }
        imageView.setFitHeight(heightRec-2*marginUp);
        imageView.setFitWidth(widthRec-2*marginLeft);

        AnchorPane.setLeftAnchor(imageView, marginLeft);
        AnchorPane.setTopAnchor(imageView, marginUp);
        AnchorPane.setLeftAnchor(rectangle, 0.0);
        AnchorPane.setTopAnchor(rectangle, 0.0);
        rectangle.setHeight(heightRec);
        rectangle.setWidth(widthRec);

    }
    private void fillChoiceBox(){
        choiceBox.getItems().addAll("A5", "A4");
        choiceBox.getSelectionModel().selectFirst();
        choiceBox1.getItems().addAll("Portrait","Paysage");
        choiceBox1.getSelectionModel().selectFirst();
        choiceBox2.getItems().addAll(  "Ultra Norrow: (0.64 cm) x 4", "Narrow: (1.27 cm) x 4",  "Moderate: ( 1.91 cm Left/Right\n" +
                                                                                                  "            2.54 cm Up/Down)",
                "Normal: (2.5 cm) x 4 ");
        choiceBox2.getSelectionModel().selectFirst();
    }
}
