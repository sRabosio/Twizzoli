package it.itsar.twizzoli.data;

import java.util.ArrayList;

import it.itsar.twizzoli.models.User;

public class UserRepo extends Repo<User>{
    public UserRepo() {
        super("table_comments");
    }


    public ArrayList<User> searchByName(String searchKey){
        ArrayList<User> result = (ArrayList<User>) data.values();
        result.removeIf(e->e.nickname.contains(searchKey));
        return result;
    }


    //salva un nuovo utente nel db
    public void userRegistration(User toRegister, ResultHandler handler){
        ArrayList<User> users = (ArrayList<User>) data.values();
        users.forEach(user -> {
            if(user.email.equals(toRegister.email)){
                handler.failed(0, "email already in use");
                return;
            }
            if(user.phone.equals(toRegister.phone)){
                handler.failed(0, "phone number already in use");
                return;
            }
            if(user.nickname.equals(toRegister.nickname)){
                handler.failed(0, "nickname already in use");
                //warning inutile al cazzo
                return;
            }
            write(toRegister);
        });

        if(write(toRegister))
            handler.success(null);
        else
            handler.failed(0, "registration failed");
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
