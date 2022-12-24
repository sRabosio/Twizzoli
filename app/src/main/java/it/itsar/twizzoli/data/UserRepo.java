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

    //TODO: handle error codes
    //Validates user using email & password
    public void validateUser(String email, String password, LoginHandler login){
        ArrayList<User> dataArray = (ArrayList<User>) data.values();
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
            login.failed(0, "User doesn't exists");
            return;
        }
        if(fromData.password.equals(password)){
            login.success(fromData);
            return;
        }

        login.failed(0, "Wrong password");
    }

}
