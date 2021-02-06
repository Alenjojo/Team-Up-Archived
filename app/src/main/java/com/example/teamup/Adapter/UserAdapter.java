package com.example.teamup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Chats.ChatsActivity;
import com.example.teamup.Model.User;
import com.example.teamup.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context mContext;
    private final List<User> mUsers;

    public UserAdapter(Context mContext, List<User>mUser){
        this.mUsers=mUser;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        final User user=mUsers.get(position);
        holder.username.setText(user.getUsername());
//        if(user.getProfile_image().equals("default")){
//            holder.pro_img.setImageResource(R.mipmap.ic_launcher);
//       }else{
//            Picasso.get().load(user.getProfile_image()).error(R.drawable.ic_launcher_background).into(holder.pro_img);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent =new Intent(mContext, ChatsActivity.class);
                intent.putExtra("userid",user.getUid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView pro_img;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            username=itemView.findViewById(R.id.username);
            pro_img=itemView.findViewById(R.id.profile_img);
        }
    }
}
