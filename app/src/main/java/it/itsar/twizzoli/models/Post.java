package it.itsar.twizzoli.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Post extends Content{
    public String title;
    public int[] images;
    public boolean parent = false;

    public Post(@NotNull String title, @NotNull String text, @NotNull String creator) {
        super(text);
        this.creator = creator;
        this.title = title;
    }

    public Post() {
        super();
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
