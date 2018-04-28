package models;

import core.Auth;
import core.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;

public class Agenda implements Comparable<Agenda> {

    private int id;
    private Date date;
    private Time heure;
    private String type;
    private String patient;
    private String description;
    private int remindingTime;

    public Agenda(int id, Date date, Time time, String type, String patient, String description) {
        this.id = id;
        this.date = date;
        this.heure = time;
        this.type = type;
        this.description = description;
        this.patient=patient;
    }

    public Agenda(int id, String date, String time, String type, String patient, String description){
        this.id = id;
        this.date = Date.valueOf(date);
        this.heure = Time.valueOf(time);
        this.type = type;
        this.description = description;
        this.patient=patient;
    }

    public Agenda(){}

    public static ArrayList<String> patientsName() {
        ArrayList<String> list = new ArrayList<String>();

        ResultSet result = DB.query("SELECT * FROM PATIENTS JOIN PERSONNE ON PATIENTS.ID_PERSONNE = PERSONNE.ID");

        try {
            while (result.next()) {
                Patient P = new Patient(
                        result.getInt("id"),
                        result.getInt("id_personne"),
                        result.getString("synopsis"),
                        result.getString("profession"),
                        result.getString("lieuDeTravail"),
                        result.getString("groupage"),
                        result.getInt("taille"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("address"),
                        result.getString("telephone"),
                        result.getString("genre").charAt(0),
                        result.getDate("dateDeNaissance")
                );
                list.add(P.getNom() + " " +P.getPrenom());
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQLException while fetching patients list");
        }

        return list;
    }



    public void setType(char c){
        if(c=='R'){
            type="R" ;

        }
        else
            type="F";
    }

    public String getTypeChar() {
        return type;
    }

    public String getPatient() {
        return patient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getType() {
        return type.equals("R") ? "Personel" : "Professionel";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void save() {
        if (this.id > 0)
            DB.query(
                    "UPDATE agenda SET date = '"+this.date+"', time = '"+this.heure+"', Patient = '"+this.patient+
                            "', description = '"+this.description+"', type = '"+this.type+"', RemindingTime="+remindingTime+
                            " WHERE id = "+this.id);

        else {
            DB.query("INSERT INTO agenda(date, time,patient,description,type, RemindingTime) VALUES(\'"
                    + this.date + "\',\'" + this.heure + "\',\'" + this.patient + "\',\'" + this.description + "\',\'" + this.type + "\',"+
                    this.remindingTime+")");


            try {
                ResultSet idt = DB.query("SELECT ID FROM AGENDA ORDER BY ID DESC");
                idt.next();
                id = idt.getInt("id");


                DB.query("INSERT INTO AGENDA_MEDECIN(ID_RDV, ID_MEDECIN) VALUES(?, ?)", id, Auth.getUserID());

            } catch (SQLException e) {
                System.out.println("[ERROR] Could not get last  id after insertion");
            }
        }



    }



    public static ArrayList<Agenda> getRdvPersonel(int id_medecin) {
        ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
                id_medecin);
        ResultSet resultSet1;
        ArrayList<Agenda> rdvs = new ArrayList<>();
        Agenda element = new Agenda();
        char c;
        try {
            while (resultSet.next()) {
                resultSet1 = DB.query("SELECT * from AGENDA where ID = " +
                        resultSet.getInt("ID_RDV") + " and TYPE='R' ");
                element = new Agenda();
                try {
                    if (resultSet1.next()) {
                        element.setId(resultSet1.getInt("id" ));
                        element.setDate(Date.valueOf(resultSet1.getString("DATE")));
                        element.setHeure(Time.valueOf(resultSet1.getString("TIME")));
                        element.setPatient(resultSet1.getString("PATIENT"));
                        element.setDescription(resultSet1.getString("DESCRIPTION"));
                        element.setType('R');
                        element.setRemindingTime(resultSet1.getInt("REmindingTime"));
                        rdvs.add(element);
                    }
                } catch (SQLException e) {
                    System.out.println("Agenda: getRdvPersonel 2: " + e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println("Agenda: getRdvPersonle: " + e.getMessage());
        }
        return rdvs;
    }

    public static ArrayList<Agenda> getRdvProfessionnel(int id_medecin) {
////         ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
////        id_medecin);
//
//
//        ResultSet resultSet1;
//        ArrayList<Agenda> rdvs = new ArrayList<>();
//        Agenda element;
//        char c;
//        try {
//            resultSet1 = DB.query("SELECT * from AGENDA where TYPE='F' ");
//            while (resultSet1.next()) {
//                element = new Agenda();
//                try {
//                    element.setId(resultSet1.getInt("id"));
//                    element.setDate(Date.valueOf(resultSet1.getString("DATE")));
//                    element.setHeure(Time.valueOf(resultSet1.getString("TIME")));
//                    element.setPatient(resultSet1.getString("PATIENT"));
//                    element.setDescription(resultSet1.getString("DESCRIPTION"));
//                    element.setRemindingTime(resultSet1.getInt("RemindingTime"));
//                    element.setType('F');
//                    element.setId(resultSet1.getInt("id" ));
//                    rdvs.add(element);
//                } catch (SQLException e) {
//                    System.out.println("Agenda: getRdvProffesionnel 2: " + e.getMessage());
//                }
//
//            }
//        } catch (SQLException e) {
//            System.out.println("Agenda: getRdvProfessionnel: " + e.getMessage());
//        }
//        return rdvs;

        ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
                id_medecin);
        ResultSet resultSet1;
        ArrayList<Agenda> rdvs = new ArrayList<>();
        Agenda element = new Agenda();
        char c;
        try {
            while (resultSet.next()) {
                resultSet1 = DB.query("SELECT * from AGENDA where ID = " +
                        resultSet.getInt("ID_RDV") + " and TYPE='F' ");
                element = new Agenda();
                try {
                    if (resultSet1.next()) {
                        element.setId(resultSet1.getInt("id" ));
                        element.setDate(Date.valueOf(resultSet1.getString("DATE" + "")));
                        element.setHeure(Time.valueOf(resultSet1.getString("TIME")));
                        element.setPatient(resultSet1.getString("PATIENT"));
                        element.setDescription(resultSet1.getString("DESCRIPTION"));
                        element.setType('F');
                        element.setRemindingTime(resultSet1.getInt("REmindingTime"));
                        rdvs.add(element);
                    }
                } catch (SQLException e) {
                    System.out.println("Agenda: RdvPro 2: " + e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println("Agenda: RdvPro: " + e.getMessage());
        }
        return rdvs;
    }

    public static ArrayList<Agenda> getRdvdate(int id_medecin, Date jour){
        ResultSet resultSet = DB.query("SELECT * from AGENDA INNER JOIN AGENDA_MEDECIN AM ON AGENDA.ID = AM.ID_RDV WHERE (DATE=\'"+
                jour.toString()+"\'or DATE =\'"+jour.toLocalDate().plusDays(1).toString()+"\' ) and (TYPE='F' or TYPe='R' and ID_MEDECIN="+Auth.getUserID()+")");
        ArrayList<Agenda> rdvs = new ArrayList<>();
        Agenda element;
        try {
            while (resultSet.next()) {
                try {
                    element= new Agenda();
                    element.setId(resultSet.getInt("id"));
                    element.setDate(Date.valueOf(resultSet.getString("DATE")));
                    element.setHeure(Time.valueOf(resultSet.getString("TIME")));
                    element.setPatient(resultSet.getString("Patient"));
                    element.setDescription(resultSet.getString("DESCRIPTION"));
                    element.setType(resultSet.getString("TYPE").charAt(0));
                    element.setRemindingTime(resultSet.getInt("RemindingTime"));
                    element.setId(resultSet.getInt("id"));
                    rdvs.add(element);

                } catch (SQLException e) {
                    System.out.println("Agenda: getRdv 2: " + e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println("Agenda: getRdv: " + e.getMessage());
        }
        return rdvs;

    }
    public String toString(){
        return "[ "+type+this.description+" "+heure.toString()+" "+remindingTime+" ]";
    }
    public static void supprimer(int id) {
        DB.query("DELETE from AGENDA_MEDECIN where ID_RDV = " + id);
        DB.query("DELETE from AGENDA where ID = " + id);

    }

    public long calculateReminingTime(){

        LocalDateTime local = LocalDateTime.of(date.toLocalDate(), heure.toLocalTime());
        local.minusMinutes(this.remindingTime);
        //System.out.println("it's the local time: "+local.toString());
        LocalDateTime now  = LocalDateTime.now();
        return Duration.between(now, local).toMinutes();
    }

    @Override
    public int compareTo(Agenda o) {
        if(this.calculateReminingTime()>o.calculateReminingTime())
            return 1;
        else if(this.calculateReminingTime()<o.calculateReminingTime())
            return -1;
        else {
            if (heure.compareTo(o.getHeure()) != 0)
                return heure.compareTo(o.getHeure());
            else if(type.compareTo(o.getType())!=0)
                return type.compareTo(o.getType());
            else if(patient.compareTo(o.getPatient())!=0)
                return patient.compareTo(o.getPatient());
            else if(description.compareTo(o.getDescription())!=0)
                return description.compareTo(o.getDescription());
            else
                return 0;
        }

    }

    public boolean isReady(){
        //  System.out.println("isReady: calculatREmining time: "+calculateReminingTime());
        if(calculateReminingTime()<=0)
            return true;
        return false;
    }

    public int getRemindingTime() {
        return remindingTime;
    }

    public void setRemindingTime(int remindingTime) {
        this.remindingTime = remindingTime;
    }
}


