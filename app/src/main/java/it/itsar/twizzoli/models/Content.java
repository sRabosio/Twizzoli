package it.itsar.twizzoli.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class Content extends Model{
   @NotNull
   public String text;
   @NotNull
   public int creator;

   public Content(@NotNull String text, @NotNull int creator) {
      this.text = text;
      this.creator = creator;
   }

   public Content() {
   }

   //TODO: mention field
}
