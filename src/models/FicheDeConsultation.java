package models;

import core.DB;
import models.LigneEnOrdonnance;
import models.Ordonnance;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FicheDeConsultation implements Comparable<FicheDeConsultation>  {

    private int id;
    private int idPatient;
    private int idMedecin;
    private int poids;
    private String motif;
    private String examenClinique;
    private String examenSuppl;
    private String diagnostic;
    private String lettreDOrientation="";
    private String certificat="";
    private Date date;
    private Time time;
    private Ordonnance ordonnance;
    private Bilan ficheBilan;


    public void setPoids(int poids) {
        this.poids = poids;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setExamenClinique(String examenClinique) {
        this.examenClinique = examenClinique;
    }

    public void setExamenSuppl(String examenSuppl) {
        this.examenSuppl = examenSuppl;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setLettreDOrientation(String lettreDOrientation) {
        this.lettreDOrientation = lettreDOrientation;
    }

    public void setCertificat(String certificat) {
        this.certificat = certificat;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    public void setFicheBilan(Bilan ficheBilan) {
        this.ficheBilan = ficheBilan;
    }

    public Bilan getFicheBilan() {
        return ficheBilan;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public String getLettreDOrientation(){
        return lettreDOrientation;
    }
    public String getExamenClinique() {
        return examenClinique;
    }

    public String getMotif() {
        return motif;
    }
    public int getPoids() {
        return poids;
    }

    public String getCertificat() {
        return certificat;
    }

    public String getExamenSuppl() {
        return examenSuppl;
    }

    public String getDiagnostic() {
        return diagnostic;
    }



    public FicheDeConsultation(){}

    public FicheDeConsultation(int id) {
        ResultSet q = DB.query("select * from CONSULTATIONS where id = ?", id);

        try {
            q.next();

            this.id = q.getInt("id");
            this.poids = q.getInt("poids");
            this.motif = q.getString("motif");
            this.examenClinique = q.getString("exam_clin");
            this.examenSuppl = q.getString("exam_supl");
            this.diagnostic = q.getString("diagnostic");
            this.lettreDOrientation = q.getString("LETTRE_DORIENTATION");
            this.certificat = q.getString("certificat");
            this.date = q.getDate("date");
            this.time = q.getTime("time");

            q = DB.query("SELECT * FROM CONSULTAIONS_MEDECIN_PATIENT WHERE ID_CONSULTATION = ?", id);

            q.next();

            this.idPatient = q.getInt("id_patient");
            this.idMedecin = q.getInt("id_medecin");


        }

        catch (SQLException e) {
            System.out.println("[ERROR] While getting FicheDeConsultation by id");
        }
    }

    public FicheDeConsultation(int idPatient, int idMedecin, int poids, String motif, String examenClinique, String examenSuppl, String diagnostic, String lettreDOrientation, String certificat, Date date, Time time, Ordonnance ordonnance, Bilan ficheBilan) {
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.poids = poids;
        this.motif = motif;
        this.examenClinique = examenClinique;
        this.examenSuppl = examenSuppl;
        this.diagnostic = diagnostic;
        this.lettreDOrientation = lettreDOrientation;
        this.certificat = certificat;
        this.date = date;
        this.time = time;
        this.ordonnance = ordonnance;
        this.ficheBilan = ficheBilan;
    }

    public FicheDeConsultation(int id, int idPatient, int idMedecin,
                               int poids, String motif, String examenClinique, String examenSuppl,
                               String diagnostic, String lettreDOrientation,
                               String certificat, Date date, Time time, Ordonnance ordonnance,
                               Bilan ficheBilan){
        this.id=id;
        this.idPatient=idPatient;
        this.idMedecin=idMedecin;
        this.poids=poids;
        this.motif=motif;
        this.examenClinique=examenClinique;
        this.examenSuppl=examenSuppl;
        this.diagnostic=diagnostic;
        this.lettreDOrientation=lettreDOrientation;
        this.certificat=certificat;
        this.date=date;
        this.time=time;
        this.ordonnance=ordonnance;
        this.ficheBilan=ficheBilan;

    }


    public void afficher(){
        System.out.println("==============================================="+'\n'+
                            "affichage de la fiche de consultation: ");
        System.out.println("id is "+id);
        System.out.println("id patient is : "+idPatient);
        System.out.println("id medecin is :"+idMedecin);
        System.out.println("poids is :"+poids);
        System.out.println("motif is :"+motif);
        System.out.println("examen cli: "+examenClinique);
        System.out.println("supplemen: "+examenSuppl);
        System.out.println("diagnotistque: "+diagnostic);
        System.out.println("lettre d'orien : "+lettreDOrientation);
        System.out.println("certificat :"+certificat);
        System.out.println("date :"+date.toString());
        System.out.println("time: "+time.toString());
        if(ordonnance!=null) ordonnance.afficher();
        if(ficheBilan!=null) ficheBilan.afficher();
    }

    static public ArrayList<FicheDeConsultation> getConsultations(int idPatient){
        ArrayList<FicheDeConsultation> array = new ArrayList<>();
        FicheDeConsultation element;
        Ordonnance ordonnance= new Ordonnance();
        Bilan bilan = new Bilan();

        ResultSet result2 ;
        ResultSet result = DB.query("SELECT * from CONSULTAIONS_MEDECIN_PATIENT where id_patient ="+
                idPatient );

        try {
            while(result.next()) {
                result2 = DB.query("select * from CONSULTATIONS where id = "
                        +result.getInt("ID_CONSULTATION"));
                try{
                    while(result2.next()) {
                        element = new FicheDeConsultation(
                            result2.getInt("id"),
                            result.getInt("id_patient"),
                            result.getInt("id_medecin"),
                            result2.getInt("poids"),
                            result2.getString("motif"),
                            result2.getString("exam_clin"),
                            result2.getString("exam_supl"),
                            result2.getString("diagnostic"),
                            result2.getString("LETTRE_DORIENTATION"),
                            result2.getString("certificat"),
                            result2.getDate("date"),
                            result2.getTime("time"),
                            ordonnance.getOrdonnance(result2.getInt("id")),
                            bilan.getBilan(result2.getInt("id"))
                        );
                        array.add(element);
                    }
                }catch (SQLException ep){
                    System.out.println("SQL ERROR: FicheDeConsultation: getConsultations2() : "+ep.getMessage());
                }
            }
        }catch (SQLException e){
            System.out.println("SQL ERROR: FicheDeConsultation: getConsultations(): "+e.getMessage());
        }finally {
            return array;
        }


    }

    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }

    public int getIdMedecin() {
        return idMedecin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public Date getDate(){
        return  date;
    }

    public Time getTime(){
        return time;
    }

    public Medecin getMedecin(){
        Medecin medecin =new Medecin();
        medecin.getMedecin(idMedecin);
        return  medecin;
    }

    @Override
    public int compareTo(FicheDeConsultation o) {
        if(this.date.compareTo(o.date)!=0){
            return this.date.compareTo(o.date);
        }
        else if(this.time.compareTo(o.time)!=0){
                return this.time.compareTo(o.time);
        }
        else
            return 0;

    }

    public void saveConsultation(){

        if (id > 0) {

            ResultSet q = DB.query("UPDATE CONSULTATIONS SET POIDS = ?, MOTIF = ?, EXAM_CLIN = ?, EXAM_SUPL = ?, DIAGNOSTIC = ?, LETTRE_DORIENTATION = ?, CERTIFICAT = ?, DATE = '"+date+"', TIME = '"+time+"' WHERE id = ?",
                    poids,
                    motif,
                    examenClinique,
                    examenSuppl,
                    diagnostic,
                    lettreDOrientation,
                    certificat,
                    id);

            return;
        }

        //PreparedStatement preparedStatement;
        //Connection connection = DB.getConnection();
        java.sql.Date date = new java.sql.Date(this.date.getTime());
        ResultSet resultSet1;
        try {

            DB.query("INSERT INTO CONSULTATIONS(POIDS, MOTIF, EXAM_CLIN, EXAM_SUPL, DIAGNOSTIC, LETTRE_DORIENTATION, CERTIFICAT, DATE, TIME) values(?,?,?,?,?,?,?,?,?) ",
                    poids,
                    motif,
                    examenClinique,
                    examenSuppl,
                    diagnostic,
                    lettreDOrientation,
                    certificat,
                    date.toString(),
                    time.toString());

            resultSet1= DB.query("select id from Consultations order by id desc");

            if(resultSet1.next()) {
                this.id = resultSet1.getInt("id");
                DB.query("INSERT INTO CONSULTAIONS_MEDECIN_PATIENT (ID_MEDECIN, ID_CONSULTATION, ID_PATIENT) VALUES ("+idMedecin+","+this.id+","+idPatient+")");
                if(this.ordonnance!=null){
                    ordonnance.saveOrdonnance(ordonnance.getOrdonnances(), id);
                }
                if(this.ficheBilan!=null){
                    ficheBilan.saveBilan(id, ficheBilan.getFicheBilan());
                }

            }
            else{
                System.out.println("LigneEnOrdonnance: saveLigneEnOrdonnance(): resultSet1 has no getInt?...");
            }
        }catch (SQLException e){
            System.out.println("LignEnOrdonnance: saveLigneEnOrdonnance(): "+e.getMessage());
        }

    }

}
