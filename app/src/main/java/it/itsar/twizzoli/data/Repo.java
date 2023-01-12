package it.itsar.twizzoli.data;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.Model;

public abstract class Repo<T extends Model> implements Serializable {
    protected String fileName;
    protected final HashMap<Integer, T> data = new HashMap<>();
    protected final AppController controller = AppController.getInstance();

    public Repo(String fileName) {
        this.fileName = fileName;
        fetch();
    }

    public boolean write(T toWrite){
        return true;
    }

    protected void logException(String message, Exception e){
        Log.d(message + " IN " +this.getClass(), e.getMessage());
    }

    //recupera i dati dal db
    @SuppressWarnings("unchecked")
    protected boolean fetch(){
        try{
            //TODO: get data from server
            HashMap<Integer, T> result = (HashMap<Integer, T>) controller.read(fileName);
            data.putAll(result);
            return true;
        }catch (Exception e){
            logException("FETCHING ERROR", e);
            return false;
        }
    }

    //returns copy instead of pointer
    public HashMap<Integer, T> getData() {
        return new HashMap<>(data);
    }

    //non lo so
    public void getElementById(int id, ResultHandler callback){
        boolean hasNotFetched = true;

        while(true){
            T result = data.get(id);
            if(result != null) {
                callback.success(result);
                return;
            }

            if(hasNotFetched){
                fetch();
                hasNotFetched = false;
            } else {
                callback.failed(1, null);
                return;
            }
        }
    }
}
