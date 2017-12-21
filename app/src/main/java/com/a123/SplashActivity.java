package com.a123;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.model.User;
import com.a123.model.UserList;
import com.a123.utills.AppConstant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SplashActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private static final int SPLASH_DURATION_MS = 2000;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setResponseListener(this);
        getUserList();

    }

    private void getUserList() {
        RequestParams p = new RequestParams();
        p.put("email", "");
        p.put("buyOrRent", "");
        p.put("propertyType", "");
        p.put("minPrice", "");
        p.put("maxPrice", "");
        p.put("searchedLocation", "");
        p.put("userLat", "");
        p.put("userLong", "");
        p.put("searchedLocationLat", "");
        p.put("searchedLocationLong", "");
        p.put("socialLoginType", "");
        p.put("appVersion", "");
        p.put("deviceType", "");

        postCall(getContext(), AppConstant.BASE_URL + "Userlisting", p, "Getting...", 1);
    }



    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                Type listType = new TypeToken<List<UserList.Info>>() {
                }.getType();
                try {
                    List<UserList.Info> u = new Gson().fromJson(o.getJSONArray("info").toString(), listType);
                    MyApp.getApplication().writeUserList(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyApp.popMessage("Alert!", "Parsing error.", getContext());
                    return;
                } catch (JsonSyntaxException ee) {
                }
          if(MyApp.getStatus(AppConstant.IS_LOGIN)){
              startActivity(new Intent(getContext(), MainActivity.class));
              finish();
          }else {
              startActivity(new Intent(getContext(), SocialLoginActivity.class));
              finish();
          }

            } else {
                MyApp.popMessageWithFinish("Alert!", o.optString("message"), SplashActivity.this);
                return;
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
        return SplashActivity.this;
    }
}
