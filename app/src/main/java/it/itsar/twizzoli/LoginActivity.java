package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityMainBinding;
import it.itsar.twizzoli.models.User;

public class LoginActivity extends AppCompatActivity {

    private final UserRepo userRepo = new UserRepo();
    private Button login;
    private Button registrati;
    private EditText usernameinput;
    private EditText passwordinput;
    private String us;
    private String ps;
    private ActivityMainBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference users = db.collection("users");
    private final AppController appController = AppController.getInstance();

    //TODO: change text type of password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snackbar();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        login = binding.loginbutton;
        registrati = binding.registration;
        usernameinput = binding.usernameinput;
        passwordinput = binding.passwordinput;

        binding.loginbutton.setEnabled(isValid());
        initLogin();
        initRegistration();

    }

    private void snackbar() {
        String snackMessage = getIntent().getStringExtra("snackMessage");
        if (snackMessage == null || snackMessage.isEmpty()) return;
        View view = getView();
        Snackbar.make(view, snackMessage, 2000).show();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        binding.loginbutton.setEnabled(isValid());
    }

    private boolean isValid() {
        String email = binding.usernameinput.getText().toString();
        String password = binding.passwordinput.getText().toString();
        return !email.isEmpty() && !password.isEmpty();
    }

    //TODO: check 4 local logging data -> do auto-login


    private void initRegistration() {
        registrati.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, Registrazione.class);
            startActivity(intent);

        });
    }

    //TODO: make login page
    private void initLogin() {
        login.setOnClickListener(v -> {
            us = usernameinput.getText().toString();
            ps = passwordinput.getText().toString();

            users
                .whereEqualTo("email", us)
                    .whereEqualTo("password", ps)
                .get()
                .addOnCompleteListener(task->{
                    List<User> found = task.getResult().toObjects(User.class);
                    if(!task.isSuccessful() || found.size() < 1){
                        Snackbar.make(getView(), "incorrect credentials", 1000).show();
                        return;
                    }
                    User user = found.get(0);
                    appController.setLoggedUser(user);
                    Intent intent = new Intent(LoginActivity.this, Homepage.class);
                    startActivity(intent);

                });
        });
    }

    private View getView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

}


