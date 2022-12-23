package it.itsar.twizzoli.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.Model;

public abstract class Repo<T extends Model> {
    protected String fileName;
    protected final HashMap<Integer, T> data = new HashMap<>();
    protected final AppController controller = AppController.getInstance();

    public Repo(String fileName) {
        this.fileName = fileName;
    }

    public boolean write(T toWrite){
        if(data.containsKey(toWrite.id)) {
            data.replace(toWrite.id, toWrite);
            return true;
        }

        try{
            controller.write(toWrite, fileName);
            data.put(toWrite.id, toWrite);
            return true;
        }catch (Exception exception){
            logException("WRITING ERROR", exception);
            return false;
        }
    }

    protected void logException(String message, Exception e){
        Log.d(message + " IN " +this.getClass(), e.getMessage());
    }

    //recupera i dati dal db
    protected boolean fetch(){
        try{
            //TODO: get data from server
            Set<T> result = (Set<T>) controller.read(fileName);
            result.forEach(e->data.put(e.id, e));
            return true;
        }catch (Exception e){
            logException("FETCHING ERROR", e);
            return false;
        }
    }

    //non lo so
    public T getElementById(int id){
        boolean hasNotFetched = true;

        while(true){
            T result = data.get(id);
            if(result != null) return result;

            if(hasNotFetched){
                fetch();
                hasNotFetched = false;
            } else return null;
        }
    }
}
