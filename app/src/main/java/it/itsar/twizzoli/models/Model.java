package it.itsar.twizzoli.models;

import androidx.databinding.BaseObservable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

//Mi serve una classe comune con id per gestire le funzioni in repo
public abstract class Model extends BaseObservable implements Serializable{
    @DocumentId
    public String id;
}
