package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class Ordonnance {

    private ArrayList<LigneEnOrdonnance> ordonnances;

    public void setOrdonnances(ArrayList<LigneEnOrdonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public static ArrayList<String > getMedList() {
        String medicament ;
        ArrayList<String > M = new ArrayList<String>();
        ResultSet resultSet = DB.query("select * from MEDICAMENT ");
        System.out.println(resultSet);

        try {
            while (resultSet.next()) {
                medicament=((String)resultSet.getString("NOM_COMMERCIAL"));
                medicament.toUpperCase();
                System.out.println(medicament);
                M.add(medicament);

                medicament=(resultSet.getString("NOM_SCIENTIFIQUE"));
                M.add(medicament);

                medicament.toUpperCase();
                System.out.println(medicament);

            }
        } catch (SQLException e) {
            System.out.println("[ERROR]: MEDICAMENT.getMedicament: " + e.getMessage());
        } finally {
            Iterator<String> it=M.iterator();
            while (it.hasNext()){
                String p1=it.next();

                //  System.out.println(p1);
            }
            return M;
        }
    }

    public Ordonnance(){
        ordonnances = new ArrayList<LigneEnOrdonnance>();
    }

    public void addLignEnOrdonnance(LigneEnOrdonnance ligneEnOrdonnance){
        ordonnances.add(ligneEnOrdonnance);
    }

    public void afficher(){
        System.out.println("=======================================" +'\n'+
                "affichage de l'ordonnance: " +'\n'+
                "=======================================");
        for(LigneEnOrdonnance ligneEnOrdonnance : ordonnances){
            ligneEnOrdonnance.afficher();
        }
    }

    public ArrayList<LigneEnOrdonnance> getOrdonnances() {
        return ordonnances;
    }


    //return an ordonnace en utilisant l'id du consultation

    public Ordonnance getOrdonnance(int idConsultation){
        Ordonnance ordonnance = new Ordonnance();
        LigneEnOrdonnance ligneEnOrdonnance = new LigneEnOrdonnance();

        ResultSet resultSet = DB.query("select * from ORDONNANCE where ID_CONSULTATION = "
                                        +idConsultation);
        try {
            while (resultSet.next()) {
               ordonnance.addLignEnOrdonnance(
                       ligneEnOrdonnance.getLigneEnOrdonnance(
                               resultSet.getInt("ID_LIGNE_ORDONNANCE")));

            }

        }

        catch (SQLException e) {
            System.out.println("[ERROR] SQLException while fetching ordonnance list");
        }
        finally {
            return ordonnance;
        }
    }

    public void saveOrdonnance(ArrayList<LigneEnOrdonnance> ordonnances, int idConsultation){

        int tmpMedId;
        Medicament medicament ;
        for(LigneEnOrdonnance ligne : ordonnances){
            tmpMedId=ligne.getMedicament().saveMed();
            ligne.saveLigneEnOrdonnance(idConsultation);
        }
    }


}
