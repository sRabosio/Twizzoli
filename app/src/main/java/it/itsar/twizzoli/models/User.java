package it.itsar.twizzoli.models;

import java.io.Serializable;

public class User extends Model implements Serializable {
    public String nickname;
    public String email;
    public String password;
    public String phone;

    public User(String nickname, String email, String password, String phone) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
