package com.example.teamup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Model.Chats;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_TITLE_LEFT=0;
    public static final int MSG_TITLE_RIGHT=1;

    private final Context mContext;
    private final List<Chats> mChats;
    private final String imgurl;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext,List<Chats>mChat,String imgurl){
        this.mChats=mChat;
        this.mContext=mContext;
        this.imgurl=imgurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if(viewType==MSG_TITLE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Chats chat =mChats.get(position);
     //   System.out.println("qww "+chat.getSender());
        holder.show_meg.setText(chat.getMessage());
     //   if(imgurl.equals("default")){
            holder.pro_img.setImageResource(R.mipmap.ic_launcher);
     //   }else{
         //   Picasso.get().load(R.mipmap.ic_launcher).error(R.drawable.loginback).into(pro_img);
     //   }

    }

    @Override
    public int getItemCount(){
        System.out.println("qee "+mChats.size());
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_meg;
        public ImageView pro_img;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            show_meg=itemView.findViewById(R.id.showmsg);
            pro_img=itemView.findViewById(R.id.chat_img);
        }
    }

    @Override
    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
      //  if(mChats.get(position).getSender().equals(fuser.getUid())){
      //      return MSG_TITLE_RIGHT;
    //    }else{
            return MSG_TITLE_LEFT;
     //   }

    }
}
