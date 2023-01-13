package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public abstract class Content extends Model{
   public String text;
   public String creator;
   public Date creationDate;


   public Content(@NotNull String text) {
      this.text = text;
   }



   public Content() {
   }

   //TODO: mention field
}
