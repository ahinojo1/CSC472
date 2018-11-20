package csc472.depaul.edu.messender.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import csc472.depaul.edu.messender.MessageActivity;
import csc472.depaul.edu.messender.Model.User;
import csc472.depaul.edu.messender.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> userList;
    private boolean isChat;

    public UserAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    public UserAdapter(Context context, List<User> userList, boolean isChat){
        this.context = context;
        this.userList = userList;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user = userList.get(i);
        viewHolder.username.setText(user.getUsername());
        if(user.getImageURL().equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else{
            Glide.with(context).load(user.getImageURL()).into(viewHolder.profile_image);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

}
