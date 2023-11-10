package com.sova.example_recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> ids, names, emails, phones, images;
    DbHelper dbHelper;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        dbHelper = new DbHelper(this);
        recyclerView = findViewById(R.id.recycleView);
        ids = new ArrayList<>();
        names = new ArrayList<>();
        emails = new ArrayList<>();
        phones = new ArrayList<>();
        images = new ArrayList<>();
        myAdapter = new MyAdapter(this, ids, names, emails, phones, images);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayData();

    }

    private void displayData() {
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
            names.add(cursor.getString(1));
            emails.add(cursor.getString(2));
            phones.add(cursor.getString(3));
            images.add(cursor.getString(4));
        }


    }
}