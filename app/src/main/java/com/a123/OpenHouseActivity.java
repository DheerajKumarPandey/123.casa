package com.a123;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.a123.adapter.DummyHotelData;
import com.a123.adapter.HotelListAdapter;
import com.a123.custome.CustomActivity;

import java.util.ArrayList;

public class OpenHouseActivity extends CustomActivity {
    private Toolbar toolbar;
    private RecyclerView home_list_recycler;
    private ArrayList listdata;
    private HotelListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_house);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        home_list_recycler = (RecyclerView) findViewById(R.id.home_list_recycler);
        listdata = (ArrayList) DummyHotelData.getListData();
        home_list_recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HotelListAdapter(listdata, this);
        home_list_recycler.setAdapter(adapter);
    }
}
