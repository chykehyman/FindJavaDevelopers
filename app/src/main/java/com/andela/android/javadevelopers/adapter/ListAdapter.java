package com.andela.android.javadevelopers.adapter;

import android.content.Context;
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

import java.util.List;

/**
 * Created by chike on 07/03/2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<DevelopersList> developersLists;
    private Context context;


    public ListAdapter(List<DevelopersList> developersLists, Context context) {
        this.developersLists = developersLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;
        public TextView username;
        public TextView githubLink;

        public ViewHolder(View itemView) {
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
        final DevelopersList developersList = developersLists.get(position);
        final String profileImage = developersLists.get(position).getProfileImage();
        final String userName = developersLists.get(position).getUsername();
        final String githubLink = developersLists.get(position).getGithubLink();

        holder.username.setText(developersList.getUsername());
        Picasso.with(context)
                .load(profileImage)
                .placeholder(R.drawable.no_profile)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("PROFILE_IMAGE", profileImage);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("GITHUB_LINK", githubLink);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }
}
