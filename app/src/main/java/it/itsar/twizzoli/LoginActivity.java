package it.itsar.twizzoli;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import it.itsar.twizzoli.controller.AppController;
import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityMainBinding;
import it.itsar.twizzoli.models.User;

public class LoginActivity extends AppCompatActivity {

    private UserRepo userRepo = new UserRepo();
    private Button login;
    private Button registrati;
    private EditText usernameinput;
    private EditText passwordinput;
    private String us;
    private String ps;
    private ActivityMainBinding binding;
    private User loggedUser = null;
    private AppController appController = AppController.getInstance();

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

        initLogin();
        initRegistration();

    }

    private void snackbar(){
        String snackMessage = getIntent().getStringExtra("snackMessage");
        if(snackMessage == null || snackMessage.isEmpty()) return;
        View view = getView();
        Snackbar.make(view, snackMessage, 2000)
                .show();
    }




    //TODO: check 4 local logging data -> do auto-login


    private void initRegistration() {
        registrati.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this,Registrazione.class);
            startActivity(intent);

        });
    }

    //TODO: make login page
    private void initLogin() {
        login.setOnClickListener(v -> {
            us = usernameinput.getText().toString();
            ps = passwordinput.getText().toString();
            userRepo.userLogin(usernameinput.getText().toString(), passwordinput.getText().toString(), new ResultHandler() {
                @Override
                public <T> void success(T result) {
                    User user = (User) result;
                    Log.d("LOGIN SUCCESS", "LOGGATOOOOOOOOOOOOOOOO");
                    appController.setLoggedUser(user);
                    Intent intent = new Intent(LoginActivity.this, Homepage.class);
                    startActivity(intent);
                }

                @Override
                public void failed(int code, String message) {
                    Snackbar.make(getView(), message, 1000).show();
                }
            });
        });
    }

    private View getView(){
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

}


