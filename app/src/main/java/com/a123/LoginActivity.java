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
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.model.User;
import com.a123.utills.AppConstant;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private EditText edt_username, edt_password;
    private TextView tv_btn_signin, tv_forget_password;
    private Toolbar toolbar;
    private boolean showPassword = false;
    private ImageButton img_btn_show_hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("");
        actionBar.setTitle("");
        setResponseListener(this);
        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signin);
        setTouchNClick(R.id.tv_forget_password);
        setTouchNClick(R.id.img_btn_show_hide);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        img_btn_show_hide = (ImageButton) findViewById(R.id.img_btn_show_hide);

        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        String htmlString = "<u>Forget Password </u>";
        tv_forget_password.setText(Html.fromHtml(htmlString));
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            if (TextUtils.isEmpty(edt_username.getText().toString())) {
                edt_username.setError("Enter UserName");
                return;
            } else if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter Password");
                return;
            }
            userLogin();

            //startActivity(new Intent(getContext(), MainActivity.class));

        } else if (v.getId() == R.id.tv_forget_password) {
            startActivity(new Intent(getContext(), ForgetPasswordActivity.class));
        } else if (v.getId() == R.id.img_btn_show_hide) {
            if (showPassword == false) {
                img_btn_show_hide.setImageResource(R.drawable.eye_hidden);
                edt_password.setTransformationMethod(null);
                showPassword = true;
            } else {
                img_btn_show_hide.setImageResource(R.drawable.eye_open);
                edt_password.setTransformationMethod(new PasswordTransformationMethod());
                showPassword = false;
            }
        }

    }

    private void userLogin() {
        RequestParams p = new RequestParams();
        p.put("deviceToken", AppConstant.DEVICE_TOKEN);
        p.put("password", edt_password.getText().toString());
        p.put("email", edt_username.getText().toString());

        postCall(getContext(), AppConstant.BASE_URL + "SignIn", p, "Logging in...", 1);

    }


    private Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {

            if (o.optString("status").equals("1")) {
                try {
                    User u = new Gson().fromJson(o.getJSONObject("info").toString(), User.class);
                    MyApp.getApplication().writeUser(u);
                }catch (JSONException e){
                    e.printStackTrace();
                }


                startActivity(new Intent(getContext(), MainActivity.class));

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
}
