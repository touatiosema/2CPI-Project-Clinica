package models;

import core.DB;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Patient extends Personne {
    private int id;
    private int id_personne;
    private String synopsis;
    private String profession;
    private String lieuDeTravail;
    private String groupage;
    private int taille;

    public Patient() {}
    public Patient(int id, int id_personne, String synopsis, String profession, String lieuDeTravail, String groupage, int taille) {
        this.id = id;
        this.id_personne = id_personne;
        this.synopsis = synopsis;
        this.profession = profession;
        this.lieuDeTravail = lieuDeTravail;
        this.groupage = groupage;
        this.taille = taille;
    }

    public Patient(int id, int id_personne,String synopsis, String profession, String lieuDeTravail, String groupage, int taille,
                   String nom, String prenom, String address, String telephone, char genre, Date dateDeNaissance ) {
        super(id_personne, nom, prenom, address, telephone, genre, dateDeNaissance);
        this.id = id;
        this.id_personne = id_personne;
        this.synopsis = synopsis;
        this.profession = profession;
        this.lieuDeTravail = lieuDeTravail;
        this.groupage = groupage;
        this.taille = taille;
    }

    public void save() {
        if (id > 0)
            DB.query(
                    "UPDATE patients SET id_personne = ?, synopsis = ?, profession = ?, lieuDeTravail = ?, groupage = ?, taille = ? WHERE  id = ?",
                    id_personne,
                    synopsis,
                    profession,
                    lieuDeTravail,
                    groupage,
                    taille,
                    id
            );

        else
            DB.query(
                    "INSERT INTO patients(id_personne, synopsis, profession, lieuDeTravail, groupage, taille) VALUES (?, ?, ?, ?, ?, ?) ",
                    id_personne,
                    synopsis,
                    profession,
                    lieuDeTravail,
                    groupage,
                    taille
            );

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLieuDeTravail() {
        return lieuDeTravail;
    }

    public void setLieuDeTravail(String lieuDeTravail) {
        this.lieuDeTravail = lieuDeTravail;
    }

    public String getGroupage() {
        return groupage;
    }

    public void setGroupage(String groupage) {
        this.groupage = groupage;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public static ArrayList<Patient> search(String nom, String prenom, String genre, String telephone) {
        String sql = "";
        if (nom.length() > 0) sql += "nom LIKE \'" + nom + "%\' ";

        if (prenom.length() > 0) {
            if (sql.length() > 0) sql += " AND ";
            sql += "prenom LIKE \'" + prenom + "%\' ";
        }

        if (genre.length() > 0) {
            if (sql.length() > 0) sql += " AND ";
            sql += "genre =  \'" + genre + "\' ";
        }

        if (telephone.length() > 0) {
            if (sql.length() > 0) sql += " AND ";
            sql += "telephone LIKE \'" + telephone + "%\' ";
        }

        if (!nom.equals("") || !prenom.equals("") || !genre.equals("") || !telephone.equals("")) sql = " WHERE " + sql;

        ArrayList<Patient> list = new ArrayList<Patient>();

        ResultSet result = DB.query("SELECT * FROM PATIENTS JOIN PERSONNE ON PATIENTS.ID_PERSONNE = PERSONNE.ID" + sql);

        try {
            while (result.next()) {
                list.add(new Patient(
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
                ));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQLException while fetching patients list");
        }

        return list;
    }

    public static ArrayList<Patient> all() {
        ArrayList<Patient> list = new ArrayList<Patient>();

        ResultSet result = DB.query("SELECT * FROM PATIENTS JOIN PERSONNE ON PATIENTS.ID_PERSONNE = PERSONNE.ID");

        try {
            while (result.next()) {
                list.add(new Patient(
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
                ));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQLException while fetching patients list");
        }

        return list;
    }

    public void getPatient(int idPatient){
        ResultSet result = DB.query("SELECT * FROM patients where id ="+idPatient);
        try {
            if(result.next()) {
                this.id = result.getInt("id");
                this.id_personne = result.getInt("id_personne");
                super.getPersonne(id_personne);
                this.synopsis= result.getString("synopsis");
                this.profession=result.getString("profession");
                this.lieuDeTravail=result.getString("lieuDeTravail");
                this.groupage=result.getString("groupage");
                this.taille=result.getInt("taille");
            }
        }catch (SQLException e){
            System.out.println("can't access database here: "+e.getMessage());
        }
    }
}
