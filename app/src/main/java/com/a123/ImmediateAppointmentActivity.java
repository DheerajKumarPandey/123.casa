package com.a123;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.utills.AppConstant;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImmediateAppointmentActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private EditText edt_time_date, edt_contact_no, edt_description;
    private TextView tv_submit_appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_appointment);
        setResponseListener(this);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_submit_appointment);

        edt_time_date = (EditText) findViewById(R.id.edt_time_date);
        edt_contact_no = (EditText) findViewById(R.id.edt_contact_no);
        edt_description = (EditText) findViewById(R.id.edt_description);

        tv_submit_appointment= (TextView)findViewById(R.id.tv_submit_appointment);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_submit_appointment) {
            if (TextUtils.isEmpty(edt_time_date.getText().toString())) {
                edt_time_date.setError("Please confirm the time  ");
                return;
            } else if (TextUtils.isEmpty(edt_contact_no.getText().toString())) {
                edt_contact_no.setError("please enter your contact no");
                return;
            }
           // immediateAppointment();
        }

    }


    public void immediateAppointment(int position) {
        RequestParams p = new RequestParams();
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("propertyId", MyApp.getApplication().readUserList().get(position).getId());
        p.put("appointmentTime", "");
        p.put("phoneNo", MyApp.getApplication().readUser().get(0).getPhoneno());
        p.put("appointmentDescription", "");
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(this, AppConstant.BASE_URL + "immediateAppointment", p, "Requesting...", 1);


    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                MyApp.popMessage("Success", o.optString("message"), getContext());
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
        MyApp.popMessage("Error", error, getContext());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Context getContext() {
        return ImmediateAppointmentActivity.this;
    }

}
