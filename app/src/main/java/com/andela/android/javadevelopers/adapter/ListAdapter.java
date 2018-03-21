package com.andela.android.javadevelopers.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.model.DevelopersList;
import com.andela.android.javadevelopers.view.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chike on 07/03/2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private final ArrayList<DevelopersList> developersLists;


    public ListAdapter(ArrayList<DevelopersList> developersLists) {
        this.developersLists = developersLists;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView username;
        TextView githubLink;

        ViewHolder(View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            githubLink = itemView.findViewById(R.id.github_url);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row, parent, false);
        return new ViewHolder(v);
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String profileImage = developersLists.get(position).getProfileImage();
        final String userName = developersLists.get(position).getUsername();
        final String githubLink = developersLists.get(position).getGithubLink();

        holder.username.setText(userName);
        Picasso.with(holder.itemView.getContext())
                .load(profileImage)
                .placeholder(R.drawable.no_profile)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("PROFILE_IMAGE", profileImage);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("GITHUB_LINK", githubLink);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }
}
