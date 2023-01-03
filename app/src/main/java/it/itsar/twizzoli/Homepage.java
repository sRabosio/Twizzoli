package it.itsar.twizzoli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.itsar.twizzoli.data.UserRepo;
import it.itsar.twizzoli.models.User;

public class Homepage extends AppCompatActivity {

    private User loggedUser = null;

    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        loggedUserChecks();

        userSample();

        String nome = getIntent().getStringExtra("nome");
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> finish());
    }

    private void loggedUserChecks() {
        if(loggedUser != null) return;
        Intent intent = new Intent(Homepage.this, LoginActivity.class);
        startActivity(intent);
    }

    public void userSample(){
        User u = new User(
                "user",
                "email@email.com",
                "pass",
                "a"
        );
        u.id = 0;
        new UserRepo().write(u);
    }

}