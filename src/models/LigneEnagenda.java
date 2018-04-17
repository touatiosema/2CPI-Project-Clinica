package models;

    import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class   LigneEnagenda {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String Type_de_RDV;
    private String Patient;
    private String Heure;
    private String Date;
    private String Remarques;

    public static void supprimer(int id) {
        DB.query("DELETE from AGENDA_MEDECIN where ID_RDV = " + id);
        DB.query("DELETE from AGENDA where ID = " + id);


    }

    public LigneEnagenda(){}

    public void setType_de_RDV(char c){
        if(c=='R'){
            Type_de_RDV="Personnel" ;

        }
        else
            Type_de_RDV="Professionel";
    }

    public void setType_de_RDV(String type_de_RDV) {
        Type_de_RDV = type_de_RDV;
    }

    public void setPatient(String patient) {
        Patient = patient;
    }

    public void setHeure(String heure) {
        Heure = heure;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setRemarques(String remarques) {
        Remarques = remarques;
    }

    public String getType_de_RDV() {
        return Type_de_RDV;
    }

    public String getPatient() {
        return Patient;
    }

    public String getHeure() {
        return Heure;
    }

    public String getDate() {
        return Date;
    }

    public String getRemarques() {
        return Remarques;
    }

    public LigneEnagenda(String type_de_RDV, String patient, String heure, String date, String remarques) {
        Type_de_RDV = type_de_RDV;
        Patient = patient;
        Heure = heure;
        Date = date;
        Remarques = remarques;
    }



    public LigneEnagenda getLigneEnagenda(int idagenda){
        LigneEnagenda ligneEnagenda= new LigneEnagenda();
        ResultSet resultSet = DB.query("select * from AGENDA where id="
                +idagenda);
        try {
            if(resultSet.next()){
                ligneEnagenda = new LigneEnagenda(
                        resultSet.getString("Type_de_RDV"),
                        resultSet.getString("Patient"),
                        resultSet.getString("Heure"),
                        resultSet.getString("Date"),
                        resultSet.getString("Remarques"));
            }
        }

        catch (SQLException e) {
            System.out.println("ERREUR");
        }finally {
            return ligneEnagenda;
        }
    }

    /*public void saveLigneEnagenda(int idagenda){

        PreparedStatement preparedStatement;
        ResultSet resultSet1;
        try {
            DB.query("insert into AGENDA(id, date, time, titre, description, type) values(?,?,?,?,?,?)",
                    idagenda, Date, Heure, Patient, Remarques, Type_de_RDV) ;
            resultSet1= DB.query("select id from agenda order by id desc");
            if(resultSet1.next()) {

                DB.query("INSERT INTO AGENDA(ID) VALUES (?)", idagenda);
            }
            else{
                System.out.println("ERREUR");
            }
        }catch (SQLException e){
            System.out.println("ERREUR "+e.getMessage());
        }
    }*/
}
