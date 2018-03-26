package models;

import core.DB;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Medecin extends Personne{
    private int id;
    private int id_personne;
    private String username;
    private String password;
    private boolean active;


    public Medecin() {}

    public Medecin(int id) {
        ResultSet q = DB.query("SELECT * FROM Medecin WHERE id = ?", id);

        try {
            if (!q.next()) return;

            this.id = q.getInt("id");
            this.id_personne = q.getInt("id_personne");
            this.username = q.getString("username");
            this.password = q.getString("password");
            this.active = q.getBoolean("active");

            q = DB.query("SELECT * FROM personne WHERE id = ?", id_personne);

            if (!q.next()) return;

            super.setId(id_personne);
            setNom(q.getString("nom"));
            setPrenom(q.getString("prenom"));
            setAddress(q.getString("address"));
            setTelephone(q.getString("telephone"));
            setGenre(q.getString("genre").charAt(0));
            setDateDeNaissance(q.getDate("dateDeNaissance"));
        }

        catch (SQLException e) {
            System.out.println("[ERROR] Could not get medecin with id");
        }
    }

    public Medecin(String username) {
        ResultSet q = DB.query("SELECT * FROM Medecin WHERE username = ?", username);

        if (q != null) {
            try {
                if (q.next() == false) return;
                this.id = q.getInt("id");
                this.username = q.getString("username");
                this.password = q.getString("password");
                this.active = q.getBoolean("active");
            }

            catch (SQLException e) {
                System.out.println("[ERROR] Could not get user login info");
            }
        }
    }

    public Medecin(int id, int id_personne, String username, String password, boolean active, String nom, String prenom, String address, String telephone, char genre, Date dateDeNaissance) {
        super(id_personne, nom, prenom, address, telephone, genre, dateDeNaissance);

        this.id = id;
        this.id_personne = id_personne;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public boolean isAdmin() {
        return id == 1;
    }

    public void save() {
        if (id > 0)
            DB.query(
                    "UPDATE MEDECIN SET id_personne = ?, username = ?, password = ?, active = ? WHERE  id = ?",
                    id_personne,
                    username,
                    password,
                    active,
                    id
            );

        else
            DB.query(
                    "INSERT INTO MEDECIN(id_personne, username, password, active) VALUES(?, ?, ?, ?)",
                    id_personne,
                    username,
                    password,
                    active
            );

    }



    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static ArrayList<Medecin> all() {
        return search("", "", "", true);
    }

    public static ArrayList<Medecin> search(String nom, String prenom, String username, boolean show_disabled) {
        ArrayList<Medecin> list = new ArrayList<Medecin>();

        String sql = "";
        if (nom.length() > 0) sql += "nom LIKE \'" + nom + "%\' ";

        if (prenom.length() > 0) {
            if (sql.length() > 0) sql += " AND ";
            sql += "prenom LIKE \'" + prenom + "%\' ";
        }

        if (username.length() > 0) {
            if (sql.length() > 0) sql += " AND ";
            sql += "username LIKE \'" + username + "%\' ";
        }

        if (!show_disabled) {
            if (sql.length() > 0) sql += " AND ";
            sql += "active = true";
        }

        if (!nom.equals("") || !prenom.equals("") || !username.equals("") || !show_disabled) sql = " WHERE " + sql;

        ResultSet result = DB.query("SELECT * FROM MEDECIN JOIN PERSONNE ON MEDECIN.ID_PERSONNE = PERSONNE.ID" + sql);

        try {
            while (result.next()) {
                list.add(new Medecin(
                        result.getInt("id"),
                        result.getInt("id_personne"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getBoolean("active"),
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
            System.out.println("[ERROR] SQLException while fetching doctors list");
        }

        return list;

    }

    public void getMedecin(int idMedecin){
        ResultSet result = DB.query("SELECT * FROM MEDECIN where id ="+idMedecin);
        try {
            if(result.next()) {
                this.id = result.getInt("id");
                this.id_personne = result.getInt("ID_PERSONNE");
                super.getPersonne(id_personne);
                this.username= result.getString("USERNAME");
                this.password=result.getString("PASSWORD");

            }
        }catch (SQLException e){
            System.out.println("can't access database Medecin: getMedecin(): "+e.getMessage());
        }
    }
}
