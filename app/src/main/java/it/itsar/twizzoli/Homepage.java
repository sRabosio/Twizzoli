package it.itsar.twizzoli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
    private BottomNavigationView bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        if (!loggedUserChecks()) return;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);
        bottomAppBar = binding.bottombar;
        switchFragment(Feed.class);
        bottomAppBar();
    }

    private void bottomAppBar(){
        bottomAppBar.setOnItemSelectedListener(item -> {
            final int home = R.id.item_home;
            final int newpost = R.id.item_newpost;
            final int user = R.id.item_profile;
            switch (item.getItemId()){
                case home:
                    switchFragment(Feed.class);
                    break;

                case user:
                    break;
                default:
                    Log.d("ERROR IN BOTTOM NAV", "ITEM DOESN'T EXISTS");
                    return false;
            }

            return true;
        });
    }

    private <T extends Fragment> void switchFragment(Class<T> fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getName())
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