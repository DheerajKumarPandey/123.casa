package com.a123;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.a123.adapter.AppointmentListAdapter;
import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.model.AppointmentData;
import com.a123.model.Appoitment;
import com.a123.model.Client;
import com.a123.model.Property;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentUserActivity extends CustomActivity {
    private Toolbar toolbar;
    private RecyclerView recy_my_appointment;
    private AppointmentListAdapter appointmentListAdapter;
    private List<AppointmentData.Info> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_user);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        setupUiElement();
    }

    private void setupUiElement() {

        recy_my_appointment=(RecyclerView)findViewById(R.id.recy_my_appointment);


        listData = MyApp.getApplication().readAppointmentData();

        recy_my_appointment.setLayoutManager(new LinearLayoutManager(this));
        appointmentListAdapter = new AppointmentListAdapter(listData ,this);
        recy_my_appointment.setAdapter(appointmentListAdapter);


    }



    private Context getContext(){return MyAppointmentUserActivity.this;}


}
