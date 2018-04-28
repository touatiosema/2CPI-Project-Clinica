package models;

import core.DB;

import javax.xml.transform.Result;
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

    public Ordonnance(int consultation_id) {
        ordonnances = new ArrayList<LigneEnOrdonnance>();
        ResultSet q = DB.query("SELECT * FROM ORDONNANCE WHERE ID_CONSULTATION = ?", consultation_id);

        try {
            while (q.next()) {
                int id_ligne = q.getInt("ID_LIGNE_ORDONNANCE");
                ResultSet q2 = DB.query("SELECT * FROM LIGNE_ORDONNANCE WHERE ID = ?", id_ligne);
                q2.next();

                String doze = q2.getString("doze");
                String detail = q2.getString("detail");
                int id_med = q2.getInt("id_medicament");

                ordonnances.add(new LigneEnOrdonnance(Medicament.getById(id_med), doze, detail));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] While getting the ordonnance");
        }

        System.out.println(ordonnances.size());
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



    public static Ordonnance getOrdonnanceById(int id) {
        Ordonnance o = new Ordonnance();
        return o.getOrdonnance(id);
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
