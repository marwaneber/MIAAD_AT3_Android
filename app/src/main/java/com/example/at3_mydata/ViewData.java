package com.example.at3_mydata;

import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    String TAG = "ViewData";
    MyDataHelper mdh;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        mListView = (ListView) findViewById(R.id.listView);

        mdh = new MyDataHelper(this);

        populateListView();

    }

    private void populateListView() {
        Log.d(TAG, "readData: Displaying data in ListView");

        Cursor data = mdh.getData();

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1) + " - " + data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);

        mListView.setAdapter(adapter);

    }
}
