package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;


public class Comment extends Content{
    public Integer postfather;

    public Integer commentFather;
    public Comment(@NotNull String text, @NotNull String creator) {
        super(text);
        this.creatorPath = creator;
    }

    public Comment() {
    }
}
