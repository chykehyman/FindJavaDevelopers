package com.andela.android.javadevelopers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public TextView username;
        public TextView workplace;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            workplace = itemView.findViewById(R.id.company);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DevelopersList developersList = developersLists.get(position);
        holder.username.setText(developersList.getUsername());
        holder.workplace.setText(developersList.getWorkplace());

    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }
}
