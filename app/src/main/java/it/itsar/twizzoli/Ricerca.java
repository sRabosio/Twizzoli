package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import it.itsar.twizzoli.adapters.AdapterUserList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.fragments.SearchBarFragment;
import it.itsar.twizzoli.models.User;

public class Ricerca extends AppCompatActivity {
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private String query = null;
    private final UserRepo search = new UserRepo();
    private RecyclerView recyclerView;
    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        loggedUser = controller.getLoggedUser();
        if(loggedUser == null) finish();

        //ricerca
        query = (String) getIntent().getSerializableExtra("ricerca");
        users = search.searchByName(query);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("appbar")
                .replace(R.id.appbar, SearchBarFragment.class, null)
                .commit();

        recyclerView = findViewById(R.id.userrecycleview);
        AdapterUserList useradapter = new AdapterUserList(users);
        recyclerView.setAdapter(useradapter);
    }
}