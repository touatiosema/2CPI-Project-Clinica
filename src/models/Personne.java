package models;

import core.DB;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String address;
    private String telephone;
    private char genre;
    private Date dateDeNaissance;

    public Personne() {}
    public Personne(int id, String nom, String prenom, String address, String telephone, char genre, Date dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.address = address;
        this.telephone = telephone;
        this.genre = genre;
        this.dateDeNaissance = dateDeNaissance;
    }

    public void save() {
        // if the element exists in the database update
        if (id > 0)
            DB.query("UPDATE personne SET nom = '"+nom+"', prenom = '"+prenom+"', address = '"+address+"', telephone = '"+telephone+"', genre = '"+genre+"', dateDeNaissance = '"+dateDeNaissance+"' WHERE id = "+id+"");

        // else insert
        else {

            DB.query("INSERT INTO personne(nom, prenom, address, telephone, genre, dateDeNaissance) VALUES('"+this.nom+"', '"+this.prenom+"', '"+this.address+"', '"+this.telephone+"', '"+this.genre+"', '"+this.dateDeNaissance+"')");

            try {
                ResultSet rest = DB.query("SELECT id FROM Personne ORDER BY ID DESC");

                rest.next();

                id = rest.getInt("id");
            } catch (SQLException e) {
                System.out.println("[ERROR] Could not get result personne id");
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void getPersonne(int id){
        ResultSet resultSet = DB.query("select * from PERSONNE where id ="+id);
        try{
            if (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.nom = resultSet.getString("nom");
                this.prenom = resultSet.getString("prenom");
                this.address = resultSet.getString("address");
                this.telephone = resultSet.getString("telephone");
                this.genre = resultSet.getString("genre").charAt(0);
                this.dateDeNaissance = Date.valueOf(resultSet.getString("dateDeNaissance"));
            }
        }

        catch (SQLException e ){
        	System.out.println("[ERROR] Getting person with oussama's fuzzy logic");
        }
    }

    public int getAge(){
        LocalDate now = LocalDateTime.now().toLocalDate();
        LocalDate naissnce = dateDeNaissance.toLocalDate();
        return Period.between(naissnce, now).getYears();
    }

    public String getFullName() {
        return nom.toUpperCase() + " " + prenom.substring(0, 1).toUpperCase() + prenom.toLowerCase().substring(1);
    }
}
