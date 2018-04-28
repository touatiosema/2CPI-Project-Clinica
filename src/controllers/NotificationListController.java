package controllers;

import core.Auth;
import core.notification.Notification;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Agenda;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class NotificationListController extends Controller {
    //TreeSet<Agenda> notificationList;
    Label label = new Label("Aucune notification pour le moment...");


    public NotificationListController() {
        min_width  = max_width = 400;
    }

    @FXML
    VBox myList;
    public void init(){

        label.setPadding(new Insets(10, 10, 10 ,10));

        myList.getChildren().addListener((ListChangeListener) c -> {
            if(myList.getChildren().isEmpty())
                myList.getChildren().add(label);
        });
      //  notificationList=Notification.getNotifcationList();
                //Agenda.getRdvdate(Auth.getUserID(), Date.valueOf(LocalDate.now()));

        int i = 0;

        for(Agenda agenda: Notification.getNotifcationList()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NotificationCard.fxml"));
            try {
                Node node = loader.load();
                NotificationCardController notificationCardController = loader.getController();
                notificationCardController.init(new HashMap(){{
                    put("agenda", agenda);
                    put("notificationList", myList);
                    put("self", node);
                }});

                if (i % 2 == 0) node.setStyle("-fx-background-color: #fcfcfc");
                else node.setStyle("-fx-background-color: #efefef");
                myList.getChildren().add(node);

            } catch (IOException e) {
                e.printStackTrace();
            }

            i++;
        }
        if(myList.getChildren().isEmpty())
            myList.getChildren().add(label);

    }



}
