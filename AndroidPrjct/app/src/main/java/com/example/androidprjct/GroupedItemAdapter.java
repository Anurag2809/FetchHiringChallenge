package com.example.androidprjct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupedItemAdapter extends RecyclerView.Adapter<GroupedItemAdapter.ViewHolder> {
    private List<GroupedItem> groupedItems;

    public GroupedItemAdapter(List<GroupedItem> groupedItems) {
        this.groupedItems = groupedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grouped_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupedItem groupedItem = groupedItems.get(position);
        holder.listIdTextView.setText("List ID: " + groupedItem.getListId());

        ItemAdapter itemAdapter = new ItemAdapter(groupedItem.getItems());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return groupedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listIdTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            listIdTextView = view.findViewById(R.id.listIdTextView);
            recyclerView = view.findViewById(R.id.recyclerView);
        }
    }
}
