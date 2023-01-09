package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import it.itsar.twizzoli.adapters.AdapterUserList;
import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityProfile2Binding;
import it.itsar.twizzoli.models.User;

public class Ricerca extends AppCompatActivity {
    private final AppController controller = AppController.getInstance();
    private User loggedUser = null;
    private User profileUser = null;
    private UserRepo search = new UserRepo();
    private RecyclerView recyclerView;
    Context context;
    ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        loggedUser = controller.getLoggedUser();
        if(loggedUser == null) finish();
        profileUser = (User) getIntent().getSerializableExtra("ricerca");
        users = search.searchByName(String.valueOf(profileUser));
        recyclerView = findViewById(R.id.userrecycleview);
        AdapterUserList useradapter = new AdapterUserList(context,users);
        recyclerView.setAdapter(useradapter);

    }
}