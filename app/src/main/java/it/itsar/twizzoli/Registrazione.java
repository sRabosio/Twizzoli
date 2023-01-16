package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.itsar.twizzoli.databinding.ActivityRegistrazioneBinding;
import it.itsar.twizzoli.models.User;

public class Registrazione extends AppCompatActivity {

    private ActivityRegistrazioneBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userRef = db.collection("users");


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

    private void logButton() {
        binding.loginbutton.setOnClickListener(v -> {
            Intent intent = new Intent(Registrazione.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        binding.buttonReg.setEnabled(isInputOk());
    }

    private void regButton() {
        binding.buttonReg.setOnClickListener(v -> {
            String password = binding.password.getText().toString();
            String username = binding.username.getText().toString();
            String email = binding.email.getText().toString();

            if (password.length() < 5) {
                Snackbar.make(v, "password must be at least 5 characthers long", 2000)
                        .show();
                return;
            }

            Map<String, Object> toRegister = new HashMap<>();
            toRegister.put("username", username);
            toRegister.put("email", email);
            toRegister.put("password", password);
            toRegister.put("follower", new ArrayList<>());
            toRegister.put("following", new ArrayList<>());

            final User user = new User();
            user.username = username;
            user.email = email;
            user.password = password;

            userRef.get().addOnSuccessListener(snap->{
                ArrayList<User> users = new ArrayList<>(snap.toObjects(User.class));
                if(users.contains(user)){
                    Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                setUser(user);
            });
        });
    }

        private void setUser (User toRegister){
            userRef
                    .document(toRegister.username)
                    .set(toRegister)
                    .addOnSuccessListener(documentReference -> {
                        Intent intent = new Intent(Registrazione.this, LoginActivity.class);
                        intent.putExtra("snackMessage", "registration completed");
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Snackbar.make(v, "" + e.getMessage(), 3000).show());
        }


        private void passwordCheck () {
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

                    if (passConfText.isEmpty() || isPasswordOK()) {
                        binding.passwordConfLabel.setTextColor(getColor(android.R.color.black));
                        return;
                    }
                    binding.passwordConfLabel.setTextColor(getColor(android.R.color.holo_red_light));
                }
            });
        }

        private boolean isPasswordOK () {
            String passwordText = binding.password.getText().toString();
            String passConfText = binding.passwordConf.getText().toString();
            return passConfText.equals(passwordText) && !(passConfText.isEmpty() || passwordText.isEmpty());
        }

        private boolean isInputOk () {
            return isPasswordOK() && !isInputEmpty();
        }

        private boolean isInputEmpty () {
            String email = binding.email.getText().toString();
            String passwordConf = binding.passwordConf.getText().toString();
            String password = binding.password.getText().toString();
            String username = binding.username.getText().toString();

            return email.isEmpty() || passwordConf.isEmpty() || password.isEmpty() || username.isEmpty();
        }


    }
