package it.itsar.twizzoli.controller;

import android.app.Application;
import android.database.Observable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.itsar.twizzoli.models.User;

public class AppController extends Application implements Serializable {
    private static AppController instance;
    private User loggedUser = null;
    private String session_file = "table_session";


    public static synchronized AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public <T> void write(T object, String fileName) throws Exception{
        File file = new File(getFilesDir(),fileName);
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(object);
        writer.close();
    }

    public Object read(String fileName) throws Exception{
        File file = new File(getFilesDir(), fileName);
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        Object result = reader.readObject();
        reader.close();
        return result;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
