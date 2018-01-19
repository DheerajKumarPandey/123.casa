package com.a123;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
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
import com.a123.utills.LocationProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends CustomActivity implements CustomActivity.ResponseCallback, LocationProvider.LocationCallback, LocationProvider.PermissionCallback {


    protected static final String TAG = "LoginActivity";
    private EditText edt_username, edt_password;
    private TextView tv_btn_signin, tv_forget_password;
    private Toolbar toolbar;
    private LocationManager locationManager;
    private LocationProvider locationProvider;
    private LatLng mCenterLatLong, sourceLocation;
    private GoogleApiClient googleApiClient;
    protected GoogleApiClient mGoogleApiClient;
    private boolean showPassword = false;
    private Double Lat, Long;
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
        locationProvider = new LocationProvider(this, this, this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sourceLocation == null) {
                    locationProvider = new LocationProvider(LoginActivity.this, LoginActivity.this, LoginActivity.this);
                    locationProvider.connect();
                }
            }
        }, (1000 * 10));
        enableGPS();
        setupUiElement();
    }


    @Override
    protected void onStart() {
        super.onStart();
        locationProvider.connect();
    }


    public void enableGPS() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addOnConnectionFailedListener(
                            new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(
                                        ConnectionResult connectionResult) {

                                    MyApp.showMassage(
                                            LoginActivity.this,
                                            "Location error "
                                                    + connectionResult
                                                    .getErrorCode());
                                }
                            }).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(LoginActivity.this,
                                        44);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });
        } else {
            LocationRequest locationRequest = LocationRequest.create();
            // locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {

                                status.startResolutionForResult(LoginActivity.this,
                                        44);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });
        }
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
        p.put("deviceToken", MyApp.getSharedPrefString(AppConstant.DEVICE_TOKEN).toString());
        p.put("password", edt_password.getText().toString());
        p.put("email", edt_username.getText().toString());
        p.put("lat", String.valueOf(Lat));
        p.put("long", String.valueOf(Long));

        postCall(getContext(), AppConstant.BASE_URL + "SignIn", p, "Logging in...", 1);

    }


    private Context getContext() {
        return LoginActivity.this;
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

                if (MyApp.getApplication().readUser().get(0).getLoginType().toString().equals("2")) {
                    startActivity(new Intent(getContext(), SellerHomeActivity.class));
                    MyApp.setStatus(AppConstant.IS_LOGIN, true);
                    finishAffinity();
                } else {

                    startActivity(new Intent(getContext(), MainActivity.class));
                    MyApp.setStatus(AppConstant.IS_LOGIN, true);
                    finishAffinity();
                }

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
    public void handleNewLocation(Location location) {
        Lat = location.getLatitude();
        Long = location.getLongitude();
    }

    @Override
    public void handleManualPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1010);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationProvider = new LocationProvider(this, this, this);
        locationProvider.connect();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
