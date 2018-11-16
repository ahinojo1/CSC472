package csc472.depaul.edu.messender;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import csc472.depaul.edu.messender.Model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    FirebaseUser fbUser;
    DatabaseReference dbRef;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("No user");


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {

        super.onStart();


        fbUser = mAuth.getCurrentUser();

        String fbUserID = fbUser.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(fbUserID);

        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);

                    username.setText(user.getUsername());

                    username.setText("USERNAME");

                    if(user.getImageURL().equals("default")){
                        //we will use the app launcher icon if the profile image is default
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    } else{
                        Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
                    }
                } else {

                    //no dataSnapShot - empty do something
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

                //

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
                return true;
        }
        return false;
    }
}
