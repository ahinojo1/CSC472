package csc472.depaul.edu.messender.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import csc472.depaul.edu.messender.Adapter.UserAdapter;
import csc472.depaul.edu.messender.Model.Chat;
import csc472.depaul.edu.messender.Model.User;
import csc472.depaul.edu.messender.Notifications.Token;
import csc472.depaul.edu.messender.R;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fbUser;
    DatabaseReference reference;

    private List<String> userList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById((R.id.recycler_view));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        //reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fbUser.getUid());

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getSender().equals((fbUser.getUid())) && !userList.contains(chat.getReceiver())){
                        userList.add(chat.getReceiver());
                    }
                    else if(chat.getReceiver().equals(fbUser.getUid()) && !userList.contains(chat.getSender())){
                        userList.add(chat.getSender());
                    }
                }
                loadChatsList();
                //updateToken(FirebaseInstanceId.getInstance().getToken());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());



        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fbUser.getUid()).setValue(token1);


    }

    private void loadChatsList(){
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        for (String id : userList) {
                            if (user.getId().equals(id)) {
                                if (mUsers.size() != 0) {
                                    for (User userTemp : mUsers) {
                                        if (!user.getId().equals(userTemp.getId())) {
                                            mUsers.add(user);
                                        }
                                    }
                                } else {
                                    mUsers.add(user);
                                }
                            }
                        }
                    }
                }
                catch (Exception e) {
                    //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
