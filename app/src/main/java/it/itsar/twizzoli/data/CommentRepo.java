package it.itsar.twizzoli.data;

import java.util.ArrayList;

import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Content;

public class CommentRepo extends Repo<Comment>{

    public CommentRepo() {
        super("table_comments");
    }

    public ArrayList<Comment> getContentChildren(Content content){
        ArrayList<Comment> comments = (ArrayList<Comment>) data.values();
        comments.removeIf(comment ->!comment.father.equals(content));
        return comments;
    }

    public ArrayList<Comment> searchByText(String key){
        ArrayList<Comment> comments = (ArrayList<Comment>) data.values();
        comments.removeIf(comment -> !comment.text.contains(key));
        return comments;
    }
}
