package models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DossierMedical {
    private Patient patient;
    private ArrayList<FicheDeConsultation> ficheDeConsultation;

    public ArrayList<FicheDeConsultation> getFicheDeConsultation() {
        return ficheDeConsultation;
    }

    public void setFicheDeConsultation(ArrayList<FicheDeConsultation> ficheDeConsultation) {
        this.ficheDeConsultation = ficheDeConsultation;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(models.Patient patient) {
        this.patient = patient;
    }

//    static public DossierMedical getDossierMedical(int idPatient){
//
//    }
}
