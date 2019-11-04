package com.anuj.hackandroid.hackernews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {


    long now;

    ArrayList<Post> postList;
    Context ctx;

    public PostAdapter(ArrayList<Post> postlist, Context ctx) {
        this.postList = postlist;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context ctx = parent.getContext();
        return new PostHolder(LayoutInflater.from(ctx).inflate(R.layout.item_row,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {


        Long tsLong = System.currentTimeMillis()/1000;


        Post currentPost = postList.get(position);

        if(currentPost.getTitle()!=null)
            holder.content.setText(currentPost.getTitle());

        if(currentPost.getDescendants()!=null)
            holder.comment.setText(Integer.toString(currentPost.getDescendants()));


        if(currentPost.getScore()!=null)
            holder.likes.setText(Integer.toString(currentPost.getScore()));

        if(currentPost.getTime()!=null) {
            Long hoursElapsed = (tsLong-currentPost.getTime())/(60*60);
            holder.time.setText(Long.toString(hoursElapsed) + "h");

            if(hoursElapsed==0)
            {
                hoursElapsed = (tsLong-currentPost.getTime())/60;
                holder.time.setText(Long.toString(hoursElapsed)+"m");
            }
        }
        if(currentPost.getBy()!=null) {
            String domain_name = "";
            try {
                URL url = new URL(currentPost.getUrl());
                domain_name=url.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            holder.contributer.setText(domain_name + " | " + currentPost.getBy());
        }



    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        TextView content,time,contributer;
        Button comment,likes;


        public PostHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.post);
            comment = itemView.findViewById(R.id.comments);
            likes = itemView.findViewById(R.id.likes);
            time = itemView.findViewById(R.id.time);
            contributer = itemView.findViewById(R.id.contributer);

            itemView.setOnClickListener(view -> {
                Animation aniFade = AnimationUtils.loadAnimation(itemView.getContext(),android.R.anim.fade_in);
                itemView.startAnimation(aniFade);

                if(ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                    Intent newIntent = new Intent("com.anuj.hackandroid.hackernews.openwebpage").setData(Uri.parse(postList.get(getAdapterPosition()).getUrl()));
                    itemView.getContext().startActivity(newIntent);
                }
                else
                {

                    StoryFragment frag = new StoryFragment(postList.get(getAdapterPosition()).getUrl());
                    ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction().replace(R.id.container,frag).commit();

                }
            });
        }



    }
}
