package com.andela.android.javadevelopers;

import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DevelopersList developersList = developersLists.get(position);
        final String user_name = developersLists.get(position).getUsername();
        final String company = developersLists.get(position).getWorkplace();
        holder.username.setText(developersList.getUsername());
        holder.workplace.setText(developersList.getWorkplace());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("USER_NAME", user_name);
                intent.putExtra("COMPANY", company);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }
}
