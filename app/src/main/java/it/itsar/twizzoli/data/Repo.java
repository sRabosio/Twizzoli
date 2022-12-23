package it.itsar.twizzoli.data;

import android.util.Log;

import androidx.databinding.ObservableArrayList;

import java.util.Arrays;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.models.Model;

public abstract class Repo<T extends Model> {
    protected String fileName;
    protected final ObservableArrayList<T> data = new ObservableArrayList<>();
    protected final AppController controller = AppController.getInstance();

    public Repo(String fileName) {
        this.fileName = fileName;
    }

    public boolean write(T element){
        if(data.contains(element))
            return true;

        try{
            controller.write(element, fileName);
            data.add(element);
            return true;
        }catch (Exception e){
            logException("WRITING ERROR", e);
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
            T[] result = (T[]) controller.read(fileName);
            data.addAll(
                    Arrays.asList(result));
            return true;
        }catch (Exception e){
            logException("FETCHING ERROR", e);
            return false;
        }
    }

    //bho, da fare meglio
    //si spera
    public T getElementById(int id){
        boolean hasNotFetched = true;

        while(true){
            for(int i = data.size()-1; i >= 0; i--){
                T result = data.get(i);
                if(result.id == id) return result;
            }
            if(hasNotFetched){
                fetch();
                hasNotFetched = false;
            } else return null;
        }
    }
}
