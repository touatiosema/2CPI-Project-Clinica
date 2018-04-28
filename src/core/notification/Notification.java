package core.notification;

import core.App;
import core.Auth;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Agenda;
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Notification extends ScheduledService<Object> {
    static LocalDate today  = LocalDate.now();
    static TreeSet<Agenda> notificationList= new TreeSet<>();
    static TreeSet<Agenda> deletedNotificationList = new TreeSet<>();
    private static boolean agendaChanged=false;


    public Notification(){
        createNotificationList();
        System.out.println("le system de notification est declenche");
//        System.out.println("its in notification..."+notificationList.size()+notificationList.toString()
//        +'\n'+deletedNotificationList.toString());

    }

    @Override
    protected Task createTask() {
        return new Task<Object>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("===u are calling the thread right now:....===");
                if(LocalDate.now().minusDays(1).equals(today)){
                    today=LocalDate.now();
                    createNotificationList();
                }
                if(notificationList.first().isReady()) {
                    deletedNotificationList.add(notificationList.first());

                    notifyTheUser(notificationList.pollFirst());
                    System.out.println(notificationList.size()+notificationList.first().toString()+"  "+deletedNotificationList.first().toString());

                }

                return null;
            }
        };

    }



//    ScheduledService<Void> service = new ScheduledService<>() {
//        @Override
//        protected Task<Void> createTask() {
//            return task;
//        }
//    } ;

    public static void createNotificationList(){
        notificationList.clear();
        ArrayList<Agenda> arrayList=Agenda.getRdvdate(Auth.getUserID(), Date.valueOf(LocalDate.now()));
        for(Agenda agenda:arrayList){
            if(!deletedNotificationList.contains(agenda)) {
                notificationList.add(agenda);
            }
        }
    }

    public static TreeSet<Agenda> getDeletedNotificationList() {
        return deletedNotificationList;
    }

     public static TreeSet<Agenda> getNotifcationList(){
        return notificationList;
    }
    public static void setAgendaChanged(boolean boo){
        agendaChanged=boo;
    }

    public static void deleteNotification(Agenda agenda){
        showCurrentStat();
        if(notificationList.contains(agenda)){
            deletedNotificationList.add(agenda);
            notificationList.remove(agenda);
        }
    }

    public static void showCurrentStat(){
        System.out.println(">>the notification list of today is : "+'\n'+notificationList.toString());
        System.out.println(">>the deleted notification list of today is : "+'\n'+deletedNotificationList.toString());
    }

    public void verifyNotification(){
        // start the
    }

    public void notifyTheUser(Agenda agenda){
        System.out.println(">>>>>>>>>>>show the notification for the user..."+agenda.getDescription());
        Platform.runLater(() -> {
            Notifications notificationbuilder = Notifications.create()
                    .title("Rendez-vous " + agenda.getType() + " "+agenda.getHeure().toString())
                    .position(Pos.TOP_RIGHT)
                    .text(agenda.getDescription())
                    .hideAfter(Duration.INDEFINITE);

            notificationbuilder.show();
        });


    }

}
