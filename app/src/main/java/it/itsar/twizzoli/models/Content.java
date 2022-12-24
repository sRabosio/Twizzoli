package it.itsar.twizzoli.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public abstract class Content extends Model{
   @NotNull
   public String text;
   @NotNull
   public User creator;

   public Content(@NotNull String text, @NotNull User creator) {
      this.text = text;
      this.creator = creator;
   }

   //TODO: mention field
}
