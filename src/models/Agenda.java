package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;

public class Agenda {

    private int id;
    private Date date;
    private Time heure;
    private char type;
    private String patient;
    private String description;

    public String getPatient() {
        return patient;
    }
    public Agenda(){}

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

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agenda(int id, Date date, Time time, char type, String patient, String description) {
        this.id = id;
        this.date = date;
        this.heure = time;
        this.type = type;
        this.description = description;
        this.patient=patient;
    }

    public Agenda(int id, String date, String time, char type, String patient, String description){
        this.id = id;
        this.date = Date.valueOf(date);
        this.heure = Time.valueOf(time);
        this.type = type;
        this.description = description;
        this.patient=patient;
    }

    public static ArrayList<LigneEnagenda> getRdvPersonel(int id_medecin) {
        ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
                id_medecin);
        ResultSet resultSet1;
        ArrayList<LigneEnagenda> rdvs = new ArrayList<>();
        LigneEnagenda element = new LigneEnagenda();
        char c;
        try {
            while (resultSet.next()) {
                resultSet1 = DB.query("SELECT * from AGENDA where ID = " +
                        resultSet.getInt("ID_RDV") + " and TYPE='R' ");
                try {
                    if (resultSet1.next()) {
                        element.setDate(resultSet1.getString("DATE"));
                        element.setHeure(resultSet1.getString("TIME"));
                        element.setPatient(resultSet1.getString("PATIENT"));
                        element.setRemarques(resultSet1.getString("DESCRIPTION"));
                        element.setType_de_RDV('R');
                        System.out.println("it's here");
                        rdvs.add(element);
                        element.setId(resultSet1.getInt("id" ));
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

    public static ArrayList<LigneEnagenda> getRdvProfessionnel() {
       // ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
                //id_medecin);


        ResultSet resultSet1;
        ArrayList<LigneEnagenda> rdvs = new ArrayList<>();
        LigneEnagenda element = new LigneEnagenda();
        char c;
        try {
            resultSet1 = DB.query("SELECT * from AGENDA where TYPE='F' ");
            while (resultSet1.next()) {

                try {
                   // if (resultSet1.next()) {
                        element.setId(resultSet1.getInt("id"));
                        element.setDate(resultSet1.getString("DATE"));
                        element.setHeure(resultSet1.getString("TIME"));
                        element.setPatient(resultSet1.getString("PATIENT"));
                        element.setRemarques(resultSet1.getString("DESCRIPTION"));
                        element.setType_de_RDV('F');
                        rdvs.add(element);
                        element.setId(resultSet1.getInt("id" ));
                   // }
                } catch (SQLException e) {
                    System.out.println("Agenda: getRdvProffesionnel 2: " + e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println("Agenda: getRdvProfessionnel: " + e.getMessage());
        }
        return rdvs;

    }
    public void save() {
        if (id > 0)
            DB.query(
                    "UPDATE agenda SET date = ?, patient = ?,  = ?, heure = ?, description = ?, type = ? WHERE  id = ?",
                    date,
                    patient,
                    heure,
                    description,
                    type,
                    id


            );

        else
            DB.query(
                    "INSERT INTO patients(date, patient,heure,description, type) VALUES(?, ?, ?, ?, ?)",
                    date,
                    patient,
                    heure,
                    description,
                    type
            );
    }

    public static ArrayList<LigneEnagenda> getRdvdate(int id_medecin, Date jour){
       /*ResultSet resultSet1;
        ArrayList<LigneEnagenda> rdvs = new ArrayList<>();
        LigneEnagenda element = new LigneEnagenda();

        try {
            resultSet1 = DB.query("SELECT * from AGENDA where ID = " + id + " and DATE="+jour.toString());
            while (resultSet1.next()) {*/
        ResultSet resultSet = DB.query("SELECT * from AGENDA_MEDECIN where ID_MEDECIN = " +
                id_medecin);
        ResultSet resultSet1;
        ArrayList<LigneEnagenda> rdvs = new ArrayList<>();
        LigneEnagenda element = new LigneEnagenda();
        char c;
        try {
            while (resultSet.next()) {
                resultSet1 = DB.query("SELECT * from AGENDA where ID = " +
                        resultSet.getInt("ID_RDV") + " and DATE= \'"+jour.toString()+"\'");
                try {
                    if (resultSet1.next()) {
                        element= new LigneEnagenda();
                        element.setId(resultSet1.getInt("id"));
                        element.setDate(resultSet1.getString("DATE"));
                        element.setHeure(resultSet1.getString("TIME"));
                        element.setPatient(resultSet1.getString("Patient"));
                        element.setRemarques(resultSet1.getString("DESCRIPTION"));
                        element.setType_de_RDV(resultSet1.getString("TYPE").charAt(0));
                        rdvs.add(element);
                        element.setId(resultSet1.getInt("id"));

                    }
                } catch (SQLException e) {
                    System.out.println("Agenda: getRdv 2: " + e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println("Agenda: getRdv: " + e.getMessage());
        }
        return rdvs;

    }



    public int getId() {
        return id;
    }

    public void supprimer(int id){
    DB.query("DELETE from AGENDA_MEDECIN where ID_RDV = " +id);
    DB.query("DELETE from AGENDA where ID = " +id);

    }
     }


