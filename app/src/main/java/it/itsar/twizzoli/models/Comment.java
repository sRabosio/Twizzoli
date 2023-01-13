package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;


public class Comment extends Content{
    public String parent;

    public Comment(@NotNull String text, @NotNull String creator) {
        super(text);
        this.creator = creator;
    }

    public Comment() {
    }
}
