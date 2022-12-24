package it.itsar.twizzoli.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Post extends Content{
    @NotNull
    public String title;
    public int[] images;

    public Post(@NotNull String title, @NotNull String text, @NotNull User creator) {
        super(text, creator);
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
