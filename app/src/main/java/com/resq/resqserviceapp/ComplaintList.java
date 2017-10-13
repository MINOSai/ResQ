package com.resq.resqserviceapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

public class ComplaintList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> idValuesList = new ArrayList<String>();
    ArrayList<String> dateValuesList = new ArrayList<String>();
    ArrayList<String> typeValuesList = new ArrayList<String>();
    ArrayList<String> modelValuesList = new ArrayList<String>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complaint history");

        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("ComplaintData", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ComplaintDetails (ID VARCHAR, date VARCHAR, type VARCHAR, model VARCHAR)");

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ComplaintDetails", null);

            int idIndex = cursor.getColumnIndex("ID");
            int dateIndex = cursor.getColumnIndex("date");
            int modelIndex = cursor.getColumnIndex("model");
            int typeIndex = cursor.getColumnIndex("type");

            cursor.moveToFirst();
            while (cursor != null) {

                idValuesList.add(cursor.getString(idIndex));
                dateValuesList.add(cursor.getString(dateIndex));
                modelValuesList.add(cursor.getString(modelIndex));
                typeValuesList.add(cursor.getString(typeIndex));

                cursor.moveToNext();
            }
        }catch (Exception e){
                e.printStackTrace();
        }

        Collections.reverse(idValuesList);
        Collections.reverse(dateValuesList);
        Collections.reverse(typeValuesList);
        Collections.reverse(modelValuesList);

        String[] idValues = idValuesList.toArray(new String[idValuesList.size()]);
        String[] dateValues = dateValuesList.toArray(new String[dateValuesList.size()]);
        String[] typeValues = typeValuesList.toArray(new String[typeValuesList.size()]);
        String[] modelValues = modelValuesList.toArray(new String[modelValuesList.size()]);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(idValues,dateValues,typeValues,modelValues);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
