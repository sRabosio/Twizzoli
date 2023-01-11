package it.itsar.twizzoli.models;


import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import it.itsar.twizzoli.BR;
import it.itsar.twizzoli.R;

public class User extends Model implements Serializable{
    public String nickname;
    public String email;
    public String password;
    public String phone;
    @NotNull
    private final ArrayList<Integer> followers = new ArrayList<>();
    @NotNull
    private final ArrayList<Integer> following = new ArrayList<>();
    public int iconId = R.drawable.ic_launcher_background;

    public User(String nickname, String email, String password, String phone) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    @NotNull
    @Bindable
    public ArrayList<Integer> getFollowers() {
        return followers;
    }

    @NotNull
    @Bindable
    public ArrayList<Integer> getFollowing() {
        return following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nickname.equals(user.nickname) && email.equals(user.email) && phone.equals(user.phone);
    }
}
