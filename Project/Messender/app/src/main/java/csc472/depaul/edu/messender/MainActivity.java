package csc472.depaul.edu.messender;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {

        super.onStart();

        //check if user is signed in and update UI
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODO
    }
}
