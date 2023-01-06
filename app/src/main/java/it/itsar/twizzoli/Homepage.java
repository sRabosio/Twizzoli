package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import it.itsar.twizzoli.adapters.PostListAdapter;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.ActivityHomepageBinding;
import it.itsar.twizzoli.fragments.Feed;
import it.itsar.twizzoli.models.Post;
import it.itsar.twizzoli.models.User;

//TODO: implement new post fragment
//TODO: implement bottom bar

public class Homepage extends AppCompatActivity {

    private AppController controller = AppController.getInstance();
    private ActivityHomepageBinding binding;
    private PostRepo postRepo = new PostRepo();
    private User loggedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        if (!loggedUserChecks()) return;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);

        switchFragment(Feed.class);

    }


    private <T extends Fragment> void switchFragment(Class<T> fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }


    private boolean loggedUserChecks(){
        loggedUser = controller.getLoggedUser();
        if(loggedUser != null) return true;
        Intent intent = new Intent(Homepage.this, LoginActivity.class);
        startActivity(intent);
        return false;
    }


}