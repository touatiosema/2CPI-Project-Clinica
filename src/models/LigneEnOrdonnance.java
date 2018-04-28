package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LigneEnOrdonnance {
    private Medicament medicament;
    private String dose;
    private String details;


    public Medicament getMedicament(){
        return medicament;
    }

    public String getAll(){

        StringBuilder returned = new StringBuilder(medicament.getNomCommercial());
        int x = 40 - medicament.getNomCommercial().length();
        for(int i = 0; i<x; i++)
            returned.append(" ");
        returned.append(details);
        x = 40 - details.length();
        for(int i = 0; i<x; i++)
            returned.append(" ");
        returned.append(dose);
        System.out.println(returned);
        return returned.toString();
    }

    public String getDose(){
        return dose;
    }

    public String getDetails() {
        return details;
    }

    public LigneEnOrdonnance(){
        medicament = new Medicament();
    }

    public void afficher(){
        System.out.println("le medicament:");
        medicament.afficher();
        System.out.println("la dose"+dose);
        System.out.println("details:"+details);
    }

    public LigneEnOrdonnance(Medicament medicament, String dose, String details){
        this.medicament=medicament;
        this.dose=dose;
        this.details=details;
    }

    public LigneEnOrdonnance getLigneEnOrdonnance(int idLigneEnOrdonnance){
        LigneEnOrdonnance ligneEnOrdonnance= new LigneEnOrdonnance();
        Medicament medicament = new Medicament();
        ResultSet resultSet = DB.query("select * from LIGNE_ORDONNANCE where id="
                +idLigneEnOrdonnance);
        try {
            if(resultSet.next()){
                ligneEnOrdonnance = new LigneEnOrdonnance(
                        medicament.getMedicament(resultSet.getInt("ID_MEDICAMENT")),
                        resultSet.getString("doze"),
                        resultSet.getString("detail"));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQLException while fetching Ligne en Ordonnance list");
            e.printStackTrace();
        }finally {
            return ligneEnOrdonnance;
        }
    }

    public void saveLigneEnOrdonnance(int idConsultation){

       // PreparedStatement preparedStatement;
        //Connection connection = DB.getConnection();
        ResultSet resultSet1;
        int tmplignID;
        try {
            //preparedStatement = connection.prepareStatement("insert into LIGNE_ORDONNANCE(ID_MEDICAMENT," +
            DB.query("insert into LIGNE_ORDONNANCE(ID_MEDICAMENT,DETAIL,DOZE) values(?,?,?) ", medicament.getId(), details, dose);
            resultSet1 = DB.query("select id from ligne_ordonnance order by id desc");
            if(resultSet1.next()) {
                tmplignID = resultSet1.getInt("id");
                DB.query("INSERT INTO ORDONNANCE(ID_CONSULTATION, ID_LIGNE_ORDONNANCE) VALUES (?,?)",
                        idConsultation, tmplignID);
            }
            else{
                System.out.println("LigneEnOrdonnance: saveLigneEnOrdonnance(): resultSet1 has no getInt?...");
            }
        }catch (SQLException e){
            System.out.println("LignEnOrdonnance: saveLigneEnOrdonnance(): "+e.getMessage());
        }
    }
}
