package it.itsar.twizzoli.controller;

import android.app.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.itsar.twizzoli.models.User;

public class AppController extends Application {
    private static AppController instance;

    public static synchronized AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public <T> void write(T object, String fileName) throws Exception{
        File file = new File(getFilesDir(),fileName);
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(object);
    }

    public Object read(String fileName) throws Exception{
        File file = new File(getFilesDir(), fileName);
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        return reader.readObject();
    }
}
