package it.itsar.twizzoli.data;

import android.util.Log;

import androidx.databinding.ObservableArrayList;

import java.util.Arrays;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.User;

public class UserRepo extends Repo<User>{
    public UserRepo() {
        super("user_list");
    }
}
