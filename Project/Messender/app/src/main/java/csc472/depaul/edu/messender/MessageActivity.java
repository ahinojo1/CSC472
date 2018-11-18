package csc472.depaul.edu.messender;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;

import csc472.depaul.edu.messender.Model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView username;
    FirebaseUser fbUser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userId = intent.getStringExtra("userid");

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("") && fbUser != null){
                    sendMessage(fbUser.getUid(), userId, msg);
                }
                else {
                    Toast.makeText(MessageActivity.this, "Type a message to send", Toast.LENGTH_SHORT).show();
                }

                text_send.setText("");
            }
        });






        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if(user !=null) {
                    try {
                        username.setText(user.getUsername());

                        if (user.getImageURL().equals("default")) {
                            profileImage.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            Glide.with(MessageActivity.this).load(user.getImageURL()).into(profileImage);
                        }
                    } catch (Exception e) {
                        Toast.makeText(MessageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put("sender", sender);
        hashmap.put("receiver", receiver);
        hashmap.put("message", message);

        reference.child("Chats").push().setValue(hashmap);
    }
}
