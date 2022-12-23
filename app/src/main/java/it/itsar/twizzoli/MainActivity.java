package it.itsar.twizzoli;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import it.itsar.twizzoli.data.AsyncResult;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityMainBinding;
import it.itsar.twizzoli.models.User;

public class MainActivity extends AppCompatActivity {

    private String username = "user";
    private String password = "pass";
    private Button login;
    private Button registrati;
    private EditText usernameinput;
    private EditText passwordinput;
    private String us;
    private String ps;
    private ActivityMainBinding binding;

    //TODO: change text type of password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        login = binding.loginbutton;
        registrati = binding.button;
        usernameinput = binding.usernameinput;
        passwordinput = binding.passwordinput;


        initLogin();
        initRegistration();


        writeReadTest();
    }

    private void initRegistration() {
        registrati.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,Registrazione.class);
            intent.putExtra("nome","lorenzo");
            startActivity(intent);

        });
    }

    private void writeReadTest() {
        UserRepo userRepo = new UserRepo();
        User user = new User();
        user.id = 0;
        user.nome = "Marco";
        userRepo.write(user);
        user.nome = "Pollo";
        userRepo.write(user);
        user = userRepo.getElementById(user.id);
        user = userRepo.getElementById(2);
    }

    private void initLogin() {
        login.setOnClickListener(v -> {
            us = usernameinput.getText().toString();
            ps = passwordinput.getText().toString();
            Log.d(TAG, us);
            Log.d(TAG, ps);
            if(Objects.equals(us, "user")){
                if(Objects.equals(ps, "pass")){
                    Intent intent = new Intent(MainActivity.this,Homepage.class);
                    intent.putExtra("nome","lorenzo");
                    startActivity(intent);
                }
            }
            else{
                //messaggio di errore
            }
        });
    }

}


