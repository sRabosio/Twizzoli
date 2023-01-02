package it.itsar.twizzoli.data;

import java.util.ArrayList;
import it.itsar.twizzoli.models.Post;

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
}
