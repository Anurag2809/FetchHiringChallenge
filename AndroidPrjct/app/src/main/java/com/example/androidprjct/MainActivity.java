package com.example.androidprjct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroupedItemAdapter adapter;
    private List<GroupedItem> groupedItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupedItemAdapter(groupedItemList);
        recyclerView.setAdapter(adapter);

        new FetchDataTask(this).execute();
    }

    public void displayData(List<Item> items) {
        Map<Integer, List<Item>> groupedItemsMap = new HashMap<>();
        for (Item item : items) {
            int listId = item.getListId();
            if (!groupedItemsMap.containsKey(listId)) {
                groupedItemsMap.put(listId, new ArrayList<>());
            }
            groupedItemsMap.get(listId).add(item);
        }

        groupedItemList.clear();
        for (Map.Entry<Integer, List<Item>> entry : groupedItemsMap.entrySet()) {
            int listId = entry.getKey();
            List<Item> itemList = entry.getValue();
            // Sort items by name within each group
            Collections.sort(itemList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            groupedItemList.add(new GroupedItem(listId, itemList));
        }

        // Sort groups by listId
        Collections.sort(groupedItemList, new Comparator<GroupedItem>() {
            @Override
            public int compare(GroupedItem o1, GroupedItem o2) {
                return Integer.compare(o1.getListId(), o2.getListId());
            }
        });

        adapter.notifyDataSetChanged();
    }
}
