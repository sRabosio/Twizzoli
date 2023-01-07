package it.itsar.twizzoli.models;

import java.io.Serializable;

//Mi serve una classe comune con id per gestire le funzioni in repo
public abstract class Model implements Serializable {
    public Integer id = null;
}
