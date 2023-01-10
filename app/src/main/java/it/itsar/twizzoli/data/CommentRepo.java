package it.itsar.twizzoli.data;

import java.util.ArrayList;

import it.itsar.twizzoli.models.Comment;

public class CommentRepo extends Repo<Comment>{

    public CommentRepo() {
        super("table_comments");
    }
//prova
    public ArrayList<Comment> getCommentChildren(Integer contentId){
        if(contentId == null) return new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>(data.values());
        comments.removeIf(comment -> comment.commentFather == null || !comment.commentFather.equals(contentId));
        return comments;
    }

    public ArrayList<Comment> getPostChildren(Integer contentId){
        if(contentId == null) return new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>(data.values());
        comments.removeIf(comment -> comment.postfather == null || !comment.postfather.equals(contentId));
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
