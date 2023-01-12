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
}
