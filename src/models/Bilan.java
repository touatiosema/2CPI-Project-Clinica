package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Bilan {

    private ArrayList<Examen> ficheBilan;
    private int consultationId;

    public void setConsultationId(int id) {
        consultationId = id;
    }

    public Bilan(){
        ficheBilan = new ArrayList<Examen>();
    }

    public Bilan(ArrayList<Examen> exams) { ficheBilan = exams; }

    public void afficher(){
        for(Examen str: ficheBilan){
            str.afficher();
        }
    }

    public ArrayList<Examen> getFicheBilan(){
        return ficheBilan;
    }

    public void addBilan(Examen exam){
        ficheBilan.add(exam);
    }

    public static Bilan getBilanById(int id) {
        Bilan b = new Bilan();
        return b.getBilan(id);
    }

    public Bilan getBilan(int idConsultation){

        Bilan bilan = new Bilan();
        ResultSet resul1 = DB.query("SELECT * FROM EXAMS_CONSULTATION WHERE ID_CONSULTATION = "+idConsultation);
        ResultSet resultSet2;
        try{
            while(resul1.next()){
                resultSet2 = DB.query("SELECT * FROM EXAMS WHERE ID = "
                                        +resul1.getInt("ID_EXAMEN"));


                if(resultSet2.next()){
                    Examen examen = new Examen(resultSet2.getInt("id"), resultSet2.getString("examen"));
                    bilan.addBilan(examen);
                }
            }
        }catch (SQLException e ){
            System.out.println("[error]: bilan: getBilan: "+e.getMessage());
        }finally {
            return bilan;
        }
    }

    public void save() {
        for (Examen exam: ficheBilan) {
            DB.query("INSERT INTO EXAMS_CONSULTATION(ID_EXAMEN, ID_CONSULTATION) VALUES(?, ?)", exam.getId(), consultationId);
        }
    }


    // PLEASE DO NOT USE THIS METHOD
    public void saveBilan(int idConsultation, ArrayList<Examen> bilan) throws NullPointerException {


        ResultSet resultSet;
        //Connection connection = DB.getConnection();
        ResultSet resultSet1;
        int examenId;
        if(bilan==null) {
            throw new NullPointerException();
        }else{
            for(Examen str: bilan) {
                resultSet = DB.query("SELECT * FROM EXAMS WHERE EXAMEN = '"+str+"'");
                try{

                        if(resultSet.next()){
                        examenId=resultSet.getInt("ID");
                        DB.query("INSERT INTO EXAMS_CONSULTATION(ID_CONSULTATION,ID_EXAMEN) VALUES(?,?  )",
                                idConsultation, examenId);
                        }else{
                        try {
                            DB.query("INSERT INTO EXAMS(EXAMEN, typeExamen) values(?,?)",str.getName(), str.getType());
                            resultSet1 = DB.query("select id from exams order by id desc ");
                            if(resultSet1.next())
                                DB.query("INSERT INTO EXAMS_CONSULTATION(ID_CONSULTATION,ID_EXAMEN) VALUES(?,?)",
                                        idConsultation,resultSet1.getInt("id"));
                            else{
                                System.out.println("bilan: saveBilan(): resultSet1 has no next?");
                            }
                        }catch (SQLException e ){
                            System.out.println("second: "+e.getMessage());
                        }

                    }
                }catch (SQLException e){
                    System.out.println("Bilan: saveBilan(): "+e.getMessage());
                }

            }

        }
    }

}
