package controllers;

import core.App;

public class AcceuilController extends Controller {

    public AcceuilController() {
        title = " Acceuil";
        width = 560;
        height = 400;
    }

    public void users() {
        App.setView("ManageAccounts");
    }

    public void patients() {
        App.setView("Patients");
    }

    public void agenda() {
        App.newWindow("Agenda_2");
    }

    public void meds() {
//        App.setView("");
    }

    public void settings() {
//        App.setView()
    }

}
