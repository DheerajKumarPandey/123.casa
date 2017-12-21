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
import com.a123.model.User;
import com.a123.utills.AppConstant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class UpdateProfileActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private EditText edt_name, edt_email, edt_dob, edt_phone, edt_address;
    private TextView tv_btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setResponseListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("");
        actionBar.setTitle("");
        setupUiElement();
    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_update);


        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_address = (EditText) findViewById(R.id.edt_address);


        edt_name.setText(MyApp.getApplication().readUser().get(0).getName());
        edt_email.setText(MyApp.getApplication().readUser().get(0).getEmail());
        edt_email.setEnabled(false);
        edt_dob.setText(MyApp.getApplication().readUser().get(0).getDob());
        edt_phone.setText(MyApp.getApplication().readUser().get(0).getPhoneno());
        edt_address.setText(MyApp.getApplication().readUser().get(0).getAddress());

        tv_btn_update = (TextView) findViewById(R.id.tv_btn_update);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_update) {
            if (TextUtils.isEmpty(edt_name.getText().toString())) {
                edt_name.setError("Enter The Name");
                return;
            } else if (TextUtils.isEmpty(edt_email.getText().toString())) {
                edt_email.setError("Enter your Email");
                return;
            } else if (TextUtils.isEmpty(edt_dob.getText().toString())) {
                edt_dob.setError("Enter date of birth");
                return;
            } else if (TextUtils.isEmpty(edt_phone.getText().toString())) {
                edt_phone.setError("Enter mobile no");
                return;
            } else if (TextUtils.isEmpty(edt_address.getText().toString())) {
                edt_address.setError("Enter your address");
                return;
            }
            userUpdate();

            //startActivity(new Intent(getContext(), MainActivity.class));

        }

    }


    private void userUpdate() {
        RequestParams p = new RequestParams();
        p.put("id", MyApp.getApplication().readUser().get(0).getId());
        p.put("name", edt_name.getText().toString());
        p.put("deviceToken", AppConstant.DEVICE_TOKEN);
        p.put("PhoneNo", edt_phone.getText().toString());
        p.put("Address", edt_address.getText().toString());
        p.put("loginType", MyApp.getApplication().readUser().get(0).getLoginType());
        p.put("deviceType", "Android");
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("dob", edt_dob.getText().toString());

        postCall(getContext(), AppConstant.BASE_URL + "EditProfile", p, "Updating...", 1);


    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                Type listType = new TypeToken<List<User.Info>>() {
                }.getType();
                try {
                    List<User.Info> u = new Gson().fromJson(o.getJSONArray("info").toString(), listType);
                    MyApp.getApplication().writeUser(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyApp.popMessage("Alert!", "Parsing error.", getContext());
                } catch (JsonSyntaxException ee) {

                }
                startActivity(new Intent(getContext(),MainActivity.class));
                finish();
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
        return UpdateProfileActivity.this;
    }

}
