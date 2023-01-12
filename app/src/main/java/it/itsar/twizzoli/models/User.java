package it.itsar.twizzoli.models;


import androidx.databinding.Bindable;

import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import it.itsar.twizzoli.R;

public class User extends Model implements Serializable{
    public String username;
    public String email;
    public String password;
    public String phone;

    @NotNull
    private final ArrayList<String> followers = new ArrayList<>();
    @NotNull
    private final ArrayList<String> following = new ArrayList<>();
    public int iconId = R.drawable.ic_launcher_background;

    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User() {
    }

    @NotNull
    @Bindable
    public ArrayList<String> getFollowers() {
        return followers;
    }

    @NotNull
    @Bindable
    public ArrayList<String> getFollowing() {
        return following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && email.equals(user.email) && phone.equals(user.phone);
    }
}
