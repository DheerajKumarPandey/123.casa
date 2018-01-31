package com.a123;

import android.content.Context;
import android.content.Intent;
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
import com.a123.utills.AppConstant;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentUserActivity extends CustomActivity implements CustomActivity.ResponseCallback{
    private Toolbar toolbar;
    private RecyclerView recy_my_appointment;
    private AppointmentListAdapter appointmentListAdapter;
    private List<AppointmentData.Info> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment_user);
        setResponseListener(this);
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









public void appointmentAccept(int position){
    RequestParams p = new RequestParams();
    p.put("agent_id", MyApp.getApplication().readUser().get(0).getId());
    p.put("User_id", MyApp.getApplication().readAppointmentData().get(position).getClient().getId());
    p.put("property_id", MyApp.getApplication().readAppointmentData().get(position).getProperty().getId());
    p.put("appointment_id",MyApp.getApplication().readAppointmentData().get(position).getAppoitment().getId());
    p.put("isAccept", "1");
    p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
    p.put("socialLoginType",MyApp.getApplication().readUser().get(0).getSocialLoginType() );
    p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
    p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

    postCall(getContext(), AppConstant.BASE_URL + "appointmentAccept", p, "Collecting Info...", 1);
}


    private Context getContext(){return MyAppointmentUserActivity.this;}


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {


                try {
                    MyApp.setSharedPrefString(AppConstant.USERLATITUDE, o.getJSONObject("info").getString("lat"));
                    MyApp.setSharedPrefString(AppConstant.USERLONGITUDE, o.getJSONObject("info").getString("long"));
                    MyApp.setSharedPrefString(AppConstant.USERNAME, o.getJSONObject("info").getString("name"));
                  //  MyApp.setSharedPrefString(AppConstant.DISTANCE, o.getJSONObject("ETA").getString("distance"));
                    //MyApp.setSharedPrefString(AppConstant.ETA, o.getJSONObject("ETA").getString("time"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(getContext(), MapActivity.class));
            } else {
                MyApp.popMessage("Error", o.optString("message"), getContext());
            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
