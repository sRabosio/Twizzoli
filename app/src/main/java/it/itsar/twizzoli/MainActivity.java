package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {


    private String username ="federico";
    private String password="111";
    private Button login;
    private Button registrati;
    private EditText usernameinput;
    private TextInputEditText passwordinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginbutton);
        registrati = findViewById(R.id.button);
        usernameinput.setText(getText(R.id.usernameinput).toString().trim());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username=="federico"){
                    if(password=="111"){
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


