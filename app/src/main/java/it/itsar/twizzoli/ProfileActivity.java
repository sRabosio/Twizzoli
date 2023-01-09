package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.databinding.ActivityProfile2Binding;
import it.itsar.twizzoli.fragments.Profilo;
import it.itsar.twizzoli.fragments.SearchBarFragment;
import it.itsar.twizzoli.models.User;

public class ProfileActivity extends AppCompatActivity {

    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private ActivityProfile2Binding binding;
    private User profileUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile2);
        loggedUser = controller.getLoggedUser();
        if(loggedUser == null) finish();

        profileUser = (User) getIntent().getSerializableExtra("profileUser");

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("appbar")
                .replace(R.id.appbar, SearchBarFragment.class, null)
                .commit();

        setFragment();
    }

    private void setFragment() {
        Bundle args = new Bundle();
        if(profileUser == null) return;
        args.putSerializable("user", profileUser);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_profile, Profilo.class, args)
                .commit();
    }


}