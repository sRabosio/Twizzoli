package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Locale;

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
    private final CollectionReference userRef = FirebaseFirestore.getInstance().collection("users");
    private RecyclerView recyclerView;
    private final AdapterUserList adapterUserList = new AdapterUserList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);
        loggedUser = controller.getLoggedUser();
        if(loggedUser == null) finish();

        //ricerca
        query = (String) getIntent().getSerializableExtra("ricerca");
        if(query == null) return;
        query = query.toLowerCase(Locale.ROOT);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("appbar")
                .replace(R.id.appbar, SearchBarFragment.class, null)
                .commit();

        recyclerView = findViewById(R.id.userrecycleview);
        recyclerView.setAdapter(adapterUserList);

        getUsers();
    }

    private void getUsers() {
        userRef.get().addOnCompleteListener(task ->{
            if(!task.isSuccessful()) return;
            List<User> result = task.getResult().toObjects(User.class);
            result.removeIf(u->!u.username.toLowerCase(Locale.ROOT).contains(query));
            List<User> adapterList = adapterUserList.getUserList();
            adapterList.clear();
            adapterList.addAll(result);
            adapterUserList.notifyDataSetChanged();
        });
    }
}