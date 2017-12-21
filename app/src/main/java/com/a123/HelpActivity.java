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

public class HelpActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private EditText edt_buy_rent, edt_property_type, edt_no_bedrooms, edt_min_price, edt_max_price, edt_location, edt_description;
    private TextView tv_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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

        setTouchNClick(R.id.tv_submit);


        edt_buy_rent = (EditText) findViewById(R.id.edt_buy_rent);
        edt_property_type = (EditText) findViewById(R.id.edt_property_type);
        edt_no_bedrooms = (EditText) findViewById(R.id.edt_no_bedrooms);
        edt_min_price = (EditText) findViewById(R.id.edt_min_price);
        edt_max_price = (EditText) findViewById(R.id.edt_max_price);
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_description = (EditText) findViewById(R.id.edt_description);

        tv_submit = (TextView) findViewById(R.id.tv_submit);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_submit) {
            if (TextUtils.isEmpty(edt_buy_rent.getText().toString())) {
                edt_buy_rent.setError("Please confirm ");
                return;
            } else if (TextUtils.isEmpty(edt_property_type.getText().toString())) {
                edt_property_type.setError("Enter the type of property");
                return;
            } else if (TextUtils.isEmpty(edt_min_price.getText().toString())) {
                edt_min_price.setError("Enter the minimum limit");
                return;
            } else if (TextUtils.isEmpty(edt_max_price.getText().toString())) {
                edt_max_price.setError("Enter the max limit");
                return;
            } else if (TextUtils.isEmpty(edt_location.getText().toString())) {
                edt_location.setError("Enter the location");
                return;
            } else if (TextUtils.isEmpty(edt_description.getText().toString())) {
                edt_description.setError("Enter the description");
                return;
            }
            requestProperty();
        }

    }

    private void requestProperty() {
        RequestParams p = new RequestParams();
        p.put("client_id", MyApp.getApplication().readUser().get(0).getId());
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("buyOrRent", edt_buy_rent.getText().toString());
        p.put("propertyType", edt_property_type.getText().toString());
        p.put("noOfBedroom", edt_no_bedrooms.getText().toString());
        p.put("minPrice", edt_min_price.getText().toString());
        p.put("maxPrice", edt_max_price.getText().toString());
        p.put("description", edt_description.getText().toString());
        p.put("userLat", MyApp.getSharedPrefString(AppConstant.LAT));
        p.put("userLong", MyApp.getSharedPrefString(AppConstant.LONG));
        p.put("intrestedLocation", edt_location.getText().toString());
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(getContext(), AppConstant.BASE_URL + "help", p, "Requesting..", 1);

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")){
                MyApp.popMessage("Request",o.optString("message"),getContext());
                startActivity(new Intent(getContext(), MainActivity.class));
                finish();
            }else {
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
        return HelpActivity.this;
    }

}
