package models;

import core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Examen implements Comparable<Examen>{
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;
    private String name;
    private int id;


    public int getId() {
        return id;
    }

    public Examen(int id) {
        ResultSet q = DB.query("SELECT * FROM EXAMS WHERE id = ?", id);

        try {
            q.next();
            this.id = q.getInt("id");
            this.name = q.getString("examen");
        }

        catch(SQLException e) {
            System.out.println("[ERROR] While getting the exam by id");
        }
    }

    public Examen(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void afficher(){
        System.out.println("type: "+type+"name: "+name);
    }


    @Override
    public int compareTo(Examen o) {
        return  type.compareTo(o.getType());
    }


    public void save() {
        if (id > 0) {
            DB.query("UPDATE Exams SET examen = ? WHERE id = ?", name, id);
        }

        else {
            DB.query("INSERT INTO Exams(examen) VALUES(?)", name);
        }
    }

    public void saveToConsultation(int consultationId) {
        DB.query("INSERT INTO EXAMS_CONSULTATION(ID_EXAMEN, ID_CONSULTATION) VALUES(?, ?)", id, consultationId);
    }

    public static ArrayList<Examen> search(String s) {
        ResultSet q = DB.query("SELECT * FROM EXAMS WHERE lower(EXAMEN) LIKE '"+ s.toLowerCase() +"%'");

        ArrayList<Examen> exams = new ArrayList<Examen>();

        try {
            while (q.next()) {
                exams.add(new Examen(q.getInt("id"), q.getString("examen")));
            }
        }

        catch (SQLException e) {
            System.out.println("[ERROR] While searching for exams");
        }

        return exams;
    }
}
