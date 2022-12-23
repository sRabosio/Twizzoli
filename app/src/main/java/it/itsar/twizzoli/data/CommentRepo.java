package it.itsar.twizzoli.data;

import it.itsar.twizzoli.models.Comment;

public class CommentRepo extends Repo<Comment>{

    public CommentRepo() {
        super("table_comments");
    }
}
