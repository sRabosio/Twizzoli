package it.itsar.twizzoli.data;

import java.util.ArrayList;

import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Content;
import it.itsar.twizzoli.models.User;

public class CommentRepo extends Repo<Comment>{

    public CommentRepo() {
        super("table_comments");
    }
//prova
    public ArrayList<Comment> getContentChildren(Content content){
        ArrayList<Comment> comments = new ArrayList<>(data.values());
        comments.removeIf(comment ->!comment.father.equals(content));
        return comments;
    }

    public ArrayList<Comment> searchByText(String key){
        ArrayList<Comment> comments = new ArrayList<>(data.values());
        comments.removeIf(comment -> !comment.text.contains(key));
        return comments;
    }

    public ArrayList<Comment> searchByUser(int userId){
        ArrayList<Comment> comments = new ArrayList<>(data.values());
        comments.removeIf(comment -> comment.creator != userId);
        return comments;
    }
}
