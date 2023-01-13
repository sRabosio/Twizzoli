package it.itsar.twizzoli.data;

import java.util.ArrayList;

import it.itsar.twizzoli.models.Comment;

public class CommentRepo extends Repo<Comment> {

    public CommentRepo() {
        super("table_comments");
    }
}
