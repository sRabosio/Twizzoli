package it.itsar.twizzoli.models;

import java.io.Serializable;

public class User extends Model implements Serializable {
    public String nome;
    public String cognome;
    public String email;
    public String password;
    public String phone;
}
