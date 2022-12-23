package it.itsar.twizzoli.controller;

import android.app.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        writer.close();
    }

    public Object read(String fileName) throws Exception{
        File file = new File(getFilesDir(), fileName);
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
        Object result = reader.readObject();
        reader.close();
        return result;
    }
}
