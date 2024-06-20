package com.example.androidprjct;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FetchDataTask extends AsyncTask<Void, Void, List<Item>> {
    private static final String URL_STRING = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    private MainActivity activity;

    public FetchDataTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        List<Item> items = new ArrayList<>();
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(URL_STRING);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            JSONArray jsonArray = new JSONArray(result.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int listId = jsonObject.getInt("listId");
                String name = jsonObject.getString("name");
                Log.e("TAG", "doInBackground: "+name );
                if (name != "null" && !name.isEmpty()) {
                    Item item = new Item();
                    item.setId(id);
                    item.setListId(listId);
                    item.setName(name);
                    items.add(item);
                }
            }
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    int listIdComparison = Integer.compare(o1.getListId(), o2.getListId());
                    if (listIdComparison == 0) {
                        return o1.getName().compareTo(o2.getName());
                    }
                    return listIdComparison;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        activity.displayData(items);
    }
}
