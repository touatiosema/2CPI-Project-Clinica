package models;

import core.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bilan {
    private ArrayList<String> ficheBilan;

    public Bilan(){
        ficheBilan = new ArrayList<String>();
    }


    public void afficher(){
        for(String str: ficheBilan){
            System.out.println(str);
        }
    }

    public ArrayList<String> getFicheBilan(){
        return ficheBilan;
    }

    public void addBilan(String str){
        ficheBilan.add(str);
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
                    String str = resultSet2.getString("examen");
                    bilan.addBilan(str);
                }
            }
        }catch (SQLException e ){
            System.out.println("[error]: bilan: getBilan: "+e.getMessage());
        }finally {
            return bilan;
        }
    }

    public void saveBilan(int idConsultation, ArrayList<String> bilan) throws NullPointerException {
        ResultSet resultSet;
        //Connection connection = DB.getConnection();
        ResultSet resultSet1;
        int examenId;
        if(bilan==null) {
            throw new NullPointerException();
        }else{
            for(String str: bilan) {
                resultSet = DB.query("SELECT * FROM EXAMS WHERE EXAMEN = '"+str+"'");
                try{

                        if(resultSet.next()){
                        examenId=resultSet.getInt("ID");
                        DB.query("INSERT INTO EXAMS_CONSULTATION(ID_CONSULTATION,ID_EXAMEN) VALUES(?,?  )",
                                idConsultation, examenId);
                        }else{
                        try {
                            DB.query("INSERT INTO EXAMS(EXAMEN) values(?)",str);
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
