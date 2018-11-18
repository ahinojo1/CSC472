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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import csc472.depaul.edu.messender.MessageActivity;
import csc472.depaul.edu.messender.Model.Chat;
import csc472.depaul.edu.messender.Model.User;
import csc472.depaul.edu.messender.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_Type_Right = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fbUser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == MSG_Type_Right) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }


        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
        return new MessageAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int position) {

        Chat chat = mChat.get(position);

        viewHolder.show_message.setText(chat.getMessage());

        if(imageurl.equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }

        else{
            Glide.with(mContext).load(imageurl).into(viewHolder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }


    @Override
    public int getItemViewType(int position) {

        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mChat.get(position).getSender().equals(fbUser.getUid())){
            return MSG_Type_Right;
        }

        return MSG_TYPE_LEFT;
    }
}