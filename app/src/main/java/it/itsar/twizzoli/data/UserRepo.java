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

}
