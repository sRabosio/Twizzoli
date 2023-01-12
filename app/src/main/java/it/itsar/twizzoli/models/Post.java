package it.itsar.twizzoli.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Post extends Content{
    @NotNull
    public String title;
    public int[] images;

    public Post(@NotNull String title, @NotNull String text, @NotNull String creator) {
        super(text);
        this.creatorPath = creator;
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
