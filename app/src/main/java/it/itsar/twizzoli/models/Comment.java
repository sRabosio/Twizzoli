package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;

public class Comment extends Content{
    public Content father;

    public Comment(@NotNull String text, @NotNull int creator) {
        super(text, creator);
    }
}
