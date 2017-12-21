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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;


public class ChangePasswordActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private EditText edt_current_password, edt_new_password, edt_confirm_password;
    private ImageButton img_btn_show_hide, img_btn_show_hide_new, img_btn_show_hide_cnf;
    private Button btn_submit;
    private boolean showPassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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

        setTouchNClick(R.id.img_btn_show_hide);
        setTouchNClick(R.id.img_btn_show_hide_new);
        setTouchNClick(R.id.img_btn_show_hide_cnf);
        setTouchNClick(R.id.btn_submit);


        edt_current_password = (EditText) findViewById(R.id.edt_current_password);
        edt_new_password = (EditText) findViewById(R.id.edt_new_password);
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);

        img_btn_show_hide = (ImageButton) findViewById(R.id.img_btn_show_hide);
        img_btn_show_hide_new = (ImageButton) findViewById(R.id.img_btn_show_hide_new);
        img_btn_show_hide_cnf = (ImageButton) findViewById(R.id.img_btn_show_hide_cnf);


        btn_submit = (Button) findViewById(R.id.btn_submit);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.img_btn_show_hide) {
            if (showPassword == false) {
                img_btn_show_hide.setImageResource(R.drawable.eye_hidden);
                edt_current_password.setTransformationMethod(null);
                showPassword = true;
            } else {
                img_btn_show_hide.setImageResource(R.drawable.eye_open);
                edt_current_password.setTransformationMethod(new PasswordTransformationMethod());
                showPassword = false;
            }

        } else if (v.getId() == R.id.img_btn_show_hide_new) {

            if (showPassword == false) {
                img_btn_show_hide_new.setImageResource(R.drawable.eye_hidden);
                edt_new_password.setTransformationMethod(null);
                showPassword = true;
            } else {
                img_btn_show_hide_new.setImageResource(R.drawable.eye_open);
                edt_new_password.setTransformationMethod(new PasswordTransformationMethod());
                showPassword = false;
            }
        } else if (v.getId() == R.id.img_btn_show_hide_cnf) {

            if (showPassword == false) {
                img_btn_show_hide_cnf.setImageResource(R.drawable.eye_hidden);
                edt_confirm_password.setTransformationMethod(null);
                showPassword = true;
            } else {
                img_btn_show_hide_cnf.setImageResource(R.drawable.eye_open);
                edt_confirm_password.setTransformationMethod(new PasswordTransformationMethod());
                showPassword = false;
            }
        } else if (v.getId() == R.id.btn_submit) {
            if (TextUtils.isEmpty(edt_current_password.getText().toString())) {
                edt_current_password.setError("Enter Your Existing Password");
                return;
            } else if (TextUtils.isEmpty(edt_new_password.getText().toString())) {
                edt_new_password.setError("Enter the New Password");
                return;
            } else if (TextUtils.isEmpty(edt_confirm_password.getText().toString())) {
                edt_confirm_password.setError("Please confirm the password");
                return;
            } else if (!edt_new_password.getText().toString().equals(edt_confirm_password.getText().toString())) {
                edt_confirm_password.setError("The password mismatch");
                return;
            }
            changePassword();
        }

    }


    private void changePassword() {
        RequestParams p = new RequestParams();
        p.put("id", MyApp.getApplication().readUser().get(0).getId());
        p.put("password", edt_current_password.getText().toString());
        p.put("newPassword", edt_new_password.getText().toString());
        p.put("socialLoginType", "0");
        p.put("appVersion", "1.0");
        p.put("deviceType", "Android");
        postCall(getContext(), AppConstant.BASE_URL + "ChangePassword", p, "Changing Password...", 1);

    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {

            if (o.optString("status").equals("1")) {

                MyApp.popMessage("Message", o.optString("message"), getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
                finishAffinity();

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
        return ChangePasswordActivity.this;
    }
}
