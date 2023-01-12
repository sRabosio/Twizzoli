package it.itsar.twizzoli.data;

import java.util.ArrayList;
import java.util.Locale;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.User;

public class UserRepo extends Repo<User>{
    public UserRepo() {
        super("table_user");
    }

    public ArrayList<User> getAll(){
        return new ArrayList<>(data.values());
    }

    public ArrayList<User> searchByName(String searchKey){
        ArrayList<User> result = new ArrayList<>(data.values());
        final String lowerKey = searchKey.toLowerCase(Locale.ROOT);
        result.removeIf(e->!e.username.toLowerCase(Locale.ROOT).contains(lowerKey));
        return result;
    }


    //salva un nuovo utente nel db
    public void userRegistration(User toRegister, ResultHandler handler){
        fetch();
        ArrayList<User> users = new ArrayList<>(data.values());

        for (User user : users) {
            if (user.email.equals(toRegister.email)) {
                handler.failed(0, "email already in use");
                return;
            }
            if (user.phone.equals(toRegister.phone)) {
                handler.failed(0, "phone number already in use");
                return;
            }
            if (user.username.equals(toRegister.username)) {
                handler.failed(0, "username already in use");
                return;
            }
        }

        if(write(toRegister))
            handler.success(toRegister);
        else
            handler.failed(0, "registration failed");
    }


    @Override
    public boolean write(User toWrite) {
        return true;
    }

    //TODO: handle error codes
    //Validates user using email & password
    public void userLogin(String email, String password, ResultHandler handler){
        ArrayList<User> dataArray = new ArrayList<>(data.values());
        User fromData = null;
        //looking 4 user
        for(User u:dataArray){
            if(u.email.equals(email)){
                fromData = u;
                break;
            }
        }

        //callbacks
        if(fromData == null){
            handler.failed(0, "User doesn't exists");
            return;
        }
        if(fromData.password.equals(password)){
            handler.success(fromData);
            controller.setLoggedUser(fromData);
            return;
        }

        handler.failed(0, "Wrong password");
    }

}
