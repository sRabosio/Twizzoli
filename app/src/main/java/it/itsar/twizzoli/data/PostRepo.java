package it.itsar.twizzoli.data;

import it.itsar.twizzoli.models.Post;

public class PostRepo extends Repo<Post>{
    public PostRepo() {
        super("table_post");
    }
}
