package it.itsar.twizzoli.data;

import java.io.Serializable;
import java.util.ArrayList;

import it.itsar.twizzoli.models.Comment;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

public class PostRepo extends Repo<Post>{
    public PostRepo() {
        super("table_post");
    }

    public ArrayList<Post> searchByKey(String key){
        ArrayList<Post> posts = new ArrayList<>(data.values());
        posts.removeIf(post -> !(post.title.contains(key) || post.text.contains(key)));
        return posts;
    }

    public ArrayList<Post> searchByUser(int id){
        ArrayList<Post> posts = new ArrayList<>(data.values());
        posts.removeIf(post -> post.creator != id);
        return posts;
    }

    public ArrayList<Post> userFeed(User user){
        //TODO: sort by creation date
        ArrayList<Post> posts = new ArrayList<>(data.values());
        posts.removeIf(post -> !user.getFollowing().contains(post.creator) && post.creator != user.id);
        if(posts.size() > 50)
            posts = new ArrayList<>(posts.subList(0,50));
        return posts;
    }
}
