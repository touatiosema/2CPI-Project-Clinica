package controllers;

import core.App;
import core.Auth;

import java.util.HashMap;

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
        App.setView("Medicaments");
    }

    public void settings() {
        App.newWindow("EditAccount", new HashMap() {{
            put("id", Auth.getUserID());
        }});
    }

}
