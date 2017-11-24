package com.a123;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.utills.AppConstant;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForgetPasswordActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_btn_submit;
    private EditText edt_email;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
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

        setTouchNClick(R.id.tv_btn_submit);


        edt_email = (EditText) findViewById(R.id.edt_email);


        tv_btn_submit = (TextView) findViewById(R.id.tv_btn_submit);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_submit) {
            if (TextUtils.isEmpty(edt_email.getText().toString())) {
                edt_email.setError("Enter UserName");
                return;
            }

            forgetPassword();

        }

    }

    private void forgetPassword() {
        RequestParams p = new RequestParams();
        p.put("email", edt_email.getText().toString());
        p.put("socialLoginType", "0");
        p.put("appVersion", "1.0");
        p.put("deviceType", "Android");

        postCall(getContext(), AppConstant.BASE_URL + "ForgotPassword", p, "Getting...", 1);
    }

    private Context getContext() {
        return ForgetPasswordActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                MyApp.popMessage("Successful","Password Send to your Email ID",getContext());
            }else {
                MyApp.popMessage("Error",o.optString("message"),getContext());
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
}
