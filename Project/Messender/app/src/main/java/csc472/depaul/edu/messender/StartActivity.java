package csc472.depaul.edu.messender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button register, login;
    FirebaseUser fbUser;

    @Override
    protected void onStart() {

        super.onStart();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if fbUser is null
        if(fbUser != null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });


    }

    public void onClick(View view){
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
    }

}

