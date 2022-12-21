package it.itsar.twizzoli.data;

import android.util.ArraySet;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.User;

public class UserRepo {
    private final String fileName = "users_table";
    private ArraySet<User> userList = new ArraySet<>();

    public void write(User user, AsyncResult result){
        if(userList.contains(user))
            result.success(userList);

        try {
            AppController.getInstance().write(user, fileName);
            userList.add(user);
            result.success(userList);
        } catch (Exception e) {
            result.failed(e);
        }
    }
}
