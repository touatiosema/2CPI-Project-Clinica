package core;

import core.exceptions.UserDeactivatedException;
import core.exceptions.UserNotFoundException;
import core.exceptions.WrongPasswordException;
import models.Medecin;

public class Auth {
    private static int user_id;

    public static int getUserID() {
        return user_id;
    }

    public static boolean isLoggedIn() {
        return user_id != 0;
    }

    public static void login(String username, String password) throws UserNotFoundException, WrongPasswordException, UserDeactivatedException {
        Medecin medecin = new Medecin(username);

        if (medecin.getId() == 0) throw new UserNotFoundException();
        if (!medecin.getPassword().equals(password)) throw new WrongPasswordException();
        if (!medecin.isActive()) throw new UserDeactivatedException();

        user_id = medecin.getId();
    }

    public static void logout() {
        user_id = 0;
    }
}
