package it.itsar.twizzoli.models;

import org.jetbrains.annotations.NotNull;


public class Comment extends Content{
    public Integer postfather;

    public Integer commentFather;
    public Comment(@NotNull String text, @NotNull int creator, Integer postfather, Integer commentFather) {
        super(text, creator);
        this.postfather = postfather;
        this.commentFather = commentFather;
    }

    public Comment() {
    }
}
