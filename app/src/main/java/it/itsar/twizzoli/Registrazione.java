package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import it.itsar.twizzoli.data.ResultHandler;
import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.databinding.ActivityRegistrazioneBinding;
import it.itsar.twizzoli.models.User;

public class Registrazione extends AppCompatActivity {

    private UserRepo userRepo = new UserRepo();
    private ActivityRegistrazioneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registrazione);
        binding.loginbutton.setOnClickListener(v -> finish());
        regButton();
    }

    private void regButton(){
        binding.buttonReg.setOnClickListener(v -> {
            //TODO: get params from form & create user

            userRepo.userRegistration(null, new ResultHandler() {
                @Override
                public <T> void success(T result) {

                   Snackbar.make(v, "Registrato con successo", 1000)
                           .show();
                    Intent intent = new Intent(Registrazione.this, LoginActivity.class);
                    startActivity(intent);

                }

                @Override
                public void failed(int code, String message) {
                    Snackbar.make(v, message, 1000).show();
                }
            });

        });
    }




}
