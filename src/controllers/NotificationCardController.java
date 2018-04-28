package controllers;

import core.notification.Notification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Agenda;

import java.util.HashMap;

public class NotificationCardController extends Controller{
    private VBox notificationList;
    private Node node;
    private Agenda agenda;

    @FXML
    Label type;
    @FXML
    Label time;
    @FXML
    Label description;
    @FXML
    FontAwesomeIconView close;
    public void init(HashMap hashMap){
        this.agenda= (Agenda)hashMap.get("agenda");
        this.notificationList = (VBox) hashMap.get("notificationList");
        this.node = (Node) hashMap.get("self");
        type.setText(agenda.getType());
        time.setText(agenda.getHeure().toString());
        description.setText(agenda.getDescription());
        close.setOnMouseClicked(event -> close());
    }

    public void close(){
        System.out.println("it's closing....");
        if(agenda!=null)
            Notification.deleteNotification(agenda);
        notificationList.getChildren().remove(node);
    }

}
