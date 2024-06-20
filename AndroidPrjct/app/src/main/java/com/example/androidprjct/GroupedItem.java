package com.example.androidprjct;

import java.util.List;

public class GroupedItem {
    private int listId;
    private List<Item> items;

    public GroupedItem(int listId, List<Item> items) {
        this.listId = listId;
        this.items = items;
    }

    public int getListId() {
        return listId;
    }

    public List<Item> getItems() {
        return items;
    }
}
