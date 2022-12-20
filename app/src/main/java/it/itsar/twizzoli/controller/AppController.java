package it.itsar.twizzoli.controller;

import android.app.Application;

public class AppController extends Application {
    private AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
