package it.itsar.twizzoli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import it.itsar.twizzoli.adapters.PostListAdapter;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.ActivityHomepageBinding;
import it.itsar.twizzoli.models.Post;


public class Homepage extends AppCompatActivity {

    private AppController controller = AppController.getInstance();
    private ActivityHomepageBinding binding;
    private PostRepo postRepo = new PostRepo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);

        if (!loggedUserChecks()) return;
        binding.postList.setAdapter(new PostListAdapter(getFeed()));
    }

    private Post[] getFeed() {
        return postRepo.userFeed(controller.getLoggedUser()).toArray(new Post[0]);
    }

    private boolean loggedUserChecks() {
        if(controller.getLoggedUser() != null) return true;
        Intent intent = new Intent(Homepage.this, LoginActivity.class);
        startActivity(intent);
        return false;
    }


}