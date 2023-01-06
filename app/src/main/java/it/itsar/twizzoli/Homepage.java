package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.PostRepo;
import it.itsar.twizzoli.databinding.ActivityHomepageBinding;
import it.itsar.twizzoli.fragments.Feed;
import it.itsar.twizzoli.fragments.NewPost;
import it.itsar.twizzoli.models.User;

//TODO: implement new post fragment
//TODO: implement bottom bar

public class Homepage extends AppCompatActivity {

    private AppController controller = AppController.getInstance();
    private ActivityHomepageBinding binding;
    private PostRepo postRepo = new PostRepo();
    private User loggedUser = null;
    private BottomNavigationView bottomAppBar;
    private float newpostTranslationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        if (!loggedUserChecks()) return;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homepage);
        bottomAppBar = binding.bottombar;
        newpostTranslationValue = binding.homepageNewpostCard.getTranslationY();
        switchNewPostFragment();
        switchFragment(Feed.class);
        containerMain();
        bottomAppBar();
    }

    private void containerMain(){
        final View container = binding.containerMain;
        container.setOnClickListener(view->{
            if(binding.homepageNewpostCard.getTranslationY() == 0)
                moveNewPost();
        });
    }

    private void switchNewPostFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.homepage_newpost, NewPost.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("new post")
                .commit();
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

                case newpost:
                    moveNewPost();
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



    public void moveNewPost(){
        CardView newpostCard = binding.homepageNewpostCard;
        float translationYValue = 0;

        if(newpostCard.getTranslationY() == 0)
            translationYValue = newpostTranslationValue;

        newpostCard.animate()
                .setDuration(500)
                .translationY(translationYValue)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
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