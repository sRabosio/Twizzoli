package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

        binding.buttonReg.setEnabled(isInputOk());

        passwordCheck();
        regButton();
        logButton();
    }

    private void logButton(){
        binding.loginbutton.setOnClickListener(v->{
            Intent intent = new Intent(Registrazione.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        binding.buttonReg.setEnabled(isInputOk());
    }

    private void regButton(){
        binding.buttonReg.setOnClickListener(v -> {
            String password = binding.password.getText().toString();
            String phone = binding.phone.getText().toString();

            if(password.length() < 5) {
                Snackbar.make(v, "password must be at least 5 characthers long", 2000)
                        .show();
                return;
            }

            if(phone.length() < 9) {
                Snackbar.make(v, "invalid phone number", 2000)
                        .show();
                return;
            }



            User toRegister = new User(
                    binding.username.getText().toString(),
                    binding.email.getText().toString(),
                    password,
                    phone
            );


            userRepo.userRegistration(toRegister, new ResultHandler() {
                @Override
                public <T> void success(T result) {

                    Intent intent = new Intent(Registrazione.this, LoginActivity.class);
                    intent.putExtra("snackMessage", "registration completed");
                    startActivity(intent);

                }

                @Override
                public void failed(int code, String message) {
                    Snackbar.make(v, message, 3000).show();
                }
            });
        });
    }


    private void passwordCheck(){
        binding.passwordConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passConfText = binding.passwordConf.toString();

                if(passConfText.isEmpty() || isPasswordOK()){
                    binding.passwordConfLabel.setTextColor(getColor(android.R.color.black));
                    return;
                }
                binding.passwordConfLabel.setTextColor(getColor(android.R.color.holo_red_light));
            }
        });
    }

    private boolean isPasswordOK(){
        String passwordText = binding.password.getText().toString();
        String passConfText = binding.passwordConf.getText().toString();
        return passConfText.equals(passwordText) && !(passConfText.isEmpty() || passwordText.isEmpty());
    }

    private boolean isInputOk(){
        return isPasswordOK() && !isInputEmpty();
    }

    private boolean isInputEmpty(){
        String email = binding.email.getText().toString();
        String passwordConf = binding.passwordConf.getText().toString();
        String password = binding.password.getText().toString();
        String phone = binding.phone.getText().toString();
        String username = binding.username.getText().toString();

        return email.isEmpty() || passwordConf.isEmpty() || password.isEmpty() || phone.isEmpty() || username.isEmpty();
    }


}
