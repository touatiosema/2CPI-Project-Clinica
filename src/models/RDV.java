package models;
import core.Auth;
import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


public class RDV {
    private String patient;
    private Date date;
    private String type;
    private String description;
    private Time heure;
    private int id;

    public RDV(){}

    public RDV (String patient, Date date, String type, String description, Time heure) {
        this.patient = patient;
        this.date = date;
        this.type = type;
        this.description = description;
        this.heure = heure;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void afficher(){
        System.out.println(patient+date+type+description+heure);
    }

    public String getPatient() {
        return patient;
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

    public String getType() {
        return type;
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

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public void save() {
        if (id > 0)
            DB.query(
                    "UPDATE agenda SET date = ?, time = ?,Patient = ?, description = ?, type = ? ",
                    this.date,
                    this.heure,
                    this.patient,
                    this.description,
                    this.type,
                    id


            );

        else {
            DB.query("INSERT INTO agenda(date, time,patient,description,type) VALUES(\'"
                    + this.date + "\',\'" + this.heure + "\',\'" + this.patient + "\',\'" + this.description + "\',\'" + this.type + "\')");


        }

        try {
            ResultSet idt = DB.query("SELECT ID FROM AGENDA ORDER BY ID DESC");
            idt.next();
            id = idt.getInt("id");


            DB.query("INSERT INTO AGENDA_MEDECIN(ID_RDV, ID_MEDECIN) VALUES(?, ?)", id, Auth.getUserID());

        }

        catch(SQLException e) {
            System.out.println("[ERROR] Could not get last  id after insertion");
        }




    }

    public  ArrayList<String> patientsName() {
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

   }