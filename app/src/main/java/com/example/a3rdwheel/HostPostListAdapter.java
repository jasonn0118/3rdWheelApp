package com.example.a3rdwheel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This is the adapter for Host's car list displays.
 */

public class HostPostListAdapter extends RecyclerView.Adapter<HostPostListAdapter.PostViewHolder> {
    List<FirebasePost> postDisplayList;
    Context cntxt;

    //constructor
    public HostPostListAdapter(Context context, List<FirebasePost> postList){
        this.postDisplayList = postList;
        this.cntxt = context;
    }

    //viewholder onCreate
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_host_post, parent, false);
        final PostViewHolder myHolder = new PostViewHolder(itemView);				//create the holder with layout

        //assign interface elements
        myHolder.postTitle = itemView.findViewById(R.id.listitem_host_PostTitle);
        myHolder.postDesc = itemView.findViewById(R.id.listitem_host_PostDescription);
        myHolder.postDate = itemView.findViewById(R.id.listitem_host_PostDuration);
        myHolder.postType = itemView.findViewById(R.id.listitem_host_PostType);
        myHolder.postItemView = itemView;

        return myHolder;
    }

    //on item created into viewholder
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        FirebasePost targetPost = postDisplayList.get(position);
        holder.postTitle.setText(targetPost.title);
        if(targetPost.posting_type)
            holder.postType.setText("Sell");
        else
            holder.postType.setText("Rent");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.postDate.setText(sdf.format(targetPost.start_date) +"  --  "+sdf.format(targetPost.end_date));
        holder.postDesc.setText(targetPost.description);

        //attach interface onclick functions
        holder.postItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to post details - TODO: pass along primary key
                Intent newPostIntent = new Intent(cntxt, HostPostDetailActivity.class);
                newPostIntent.putExtra("TARGETPOST", targetPost.car_name);
                cntxt.startActivity(newPostIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postDisplayList.size();
    }

    //viewholder class
    public class PostViewHolder extends RecyclerView.ViewHolder{
        //interfaces of the item
        TextView postTitle;
        TextView postDesc;
        TextView postType;
        View postItemView;
        TextView postDate;

        PostViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
