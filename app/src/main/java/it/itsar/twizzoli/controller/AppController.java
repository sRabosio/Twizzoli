package it.itsar.twizzoli.controller;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import it.itsar.twizzoli.models.User;

public class AppController extends Application {
    private static AppController instance;

    public static AppController getInstance() {
        if(instance == null)
            instance = new AppController();

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public <T> void write(T object, String fileName) throws Exception{
        File file = new File(getFilesDir(), fileName);
        file.createNewFile();
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
            writer.writeObject(object);
    }
}
