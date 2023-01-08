package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;


public class Comment extends Content{
    @NotNull
    public int father;
    public Comment(@NotNull String text, @NotNull int creator, @NotNull int father) {
        super(text, creator);
    }
}
