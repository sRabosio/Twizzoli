package it.itsar.twizzoli;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

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

    //TODO: change text type of password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSample();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        login = binding.loginbutton;
        registrati = binding.button;
        usernameinput = binding.usernameinput;
        passwordinput = binding.passwordinput;

        if(loggedUser == null){
            initLogin();
            return;
        }

    }

    public void userSample(){
        User u = new User(
                "user",
                "email@email.com",
                "pass",
                "a"
        );
        userRepo.write(u);
    }


    //TODO: check 4 local logging data -> do auto-login


    private void initRegistration() {
        registrati.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this,Registrazione.class);
            intent.putExtra("nome","lorenzo");
            startActivity(intent);

        });
    }

    //TODO: make login page
    private void initLogin() {
        login.setOnClickListener(v -> {
            us = usernameinput.getText().toString();
            ps = passwordinput.getText().toString();
            Log.d(TAG, us);
            Log.d(TAG, ps);
            userRepo.userLogin(usernameinput.getText().toString(), passwordinput.getText().toString(), new ResultHandler() {
                @Override
                public <T> void success(T result) {
                    User user = (User) result;

                    Log.d("LOGIN SUCCESS", "LOGGATOOOOOOOOOOOOOOOO");

                    //a homepage
                }

                @Override
                public void failed(int code, String message) {

                }
            });
        });
    }

}


