package it.itsar.twizzoli.models;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

//Mi serve una classe comune con id per gestire le funzioni in repo
public abstract class Model extends BaseObservable implements Serializable{
    public Integer id = null;
}
