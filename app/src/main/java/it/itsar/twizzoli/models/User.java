package it.itsar.twizzoli.models;

import java.io.Serializable;
import java.util.Objects;

public class User extends Model implements Serializable {
    public String nickname;
    public String email;
    public String password;
    public String phone;
    public int iconId;

    public User(String nickname, String email, String password, String phone) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nickname.equals(user.nickname) && email.equals(user.email) && phone.equals(user.phone);
    }
}
