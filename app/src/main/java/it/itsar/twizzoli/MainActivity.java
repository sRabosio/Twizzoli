package it.itsar.twizzoli;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private String username ="user";
    private String password="pass";
    private Button login;
    private Button registrati;
    private EditText usernameinput;
    private EditText passwordinput;
    private String us;
    private String ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginbutton);
        registrati = findViewById(R.id.button);
        usernameinput = findViewById(R.id.usernameinput);
        passwordinput = findViewById(R.id.passwordinput);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Registrazione.class);
                intent.putExtra("nome","lorenzo");
                startActivity(intent);

            }
        });

    }

}


