package com.andela.android.javadevelopers.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.home.model.GitHubUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * The type List adapter.
 */
public class GitHubUsersAdapter extends RecyclerView.Adapter<GitHubUsersAdapter.ViewHolder> {

    private final ArrayList<GitHubUser> gitHubUsers;
    private final HomeContract.RecyclerItemClickListener recyclerItemClickListener;


    /**
     * Instantiates a new List adapter.
     *
     * @param recyclerItemClickListener the recycler item click listener
     */
    @Inject
    public GitHubUsersAdapter(HomeContract.RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.gitHubUsers = new ArrayList<>();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Profile image.
         */
        @BindView(R.id.profile_image) ImageView profileImage;
        /**
         * The Username.
         */
        @BindView(R.id.username) TextView username;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String profileImage = gitHubUsers.get(position).getProfileImage();
        final String userName = gitHubUsers.get(position).getUsername();

        holder.username.setText(userName);

        Picasso.with(holder.itemView.getContext())
                .load(profileImage)
                .placeholder(R.drawable.no_profile)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(view -> recyclerItemClickListener
                .onItemClick(gitHubUsers.get(holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return gitHubUsers.size();
    }

    public void setData(ArrayList<GitHubUser> gitHubUsers) {
        this.gitHubUsers.addAll(gitHubUsers);
        notifyDataSetChanged();
    }

}
