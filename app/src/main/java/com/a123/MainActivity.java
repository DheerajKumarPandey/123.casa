package com.a123;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.adapter.DummyHotelData;
import com.a123.adapter.HotelListAdapter;
import com.a123.application.MyApp;
import com.a123.application.SingleInstance;
import com.a123.custome.CustomActivity;
import com.a123.fragment.FragmentDrawer;
import com.a123.utills.AppConstant;
import com.a123.utills.LocationProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;
import static java.sql.Types.NULL;

public class MainActivity extends CustomActivity implements FragmentDrawer.FragmentDrawerListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, LocationProvider.LocationCallback, LocationProvider.PermissionCallback, GoogleMap.OnCameraIdleListener,
        ResultCallback<LocationSettingsResult> {
    private boolean isFirstSet = false;
    private GoogleApiClient googleApiClient;
    private LatLng sourceLocation = null;
    private LocationProvider locationProvider;
    private FragmentDrawer drawerFragment;
    private TextView txt_location;
    private TextView tv_filter, tv_show_open_house, tv_select_type, tv_select_subtype, tv_help, tv_chat;
    private CheckBox chek_box_appointment;
    //  private TextView txt_address;
    private FrameLayout map_view;
    private GoogleMap mMap;
    private DrawerLayout drawer;
    private SupportMapFragment mapFragment;
    private RecyclerView recycler_list_view;
    private ArrayList listdata;
    private HotelListAdapter adapter;
    protected GoogleApiClient mGoogleApiClient;
    protected static final String TAG = "MainActivity";
    private TextView Tv_search, Tv_service, Tv_notification, Tv_account;
    private ImageButton navBtn, btn_search, img_btn_notification;
    int count= 0;
    FloatingActionButton Show_all, Domestic, Construction, Events;
    String[] SpinnerText = {"Wallet", "Cash"};
    // int SpinnerIcons[] = {R.drawable.wallet_white, R.drawable.cash_white};
    private Spinner wallet_cash_spiner;
    private TextView tv_book_now;

    private static final int CAMERA_PIC_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  if (Build.VERSION.SDK_INT >= 21) {

            // Set the status bar to dark-semi-transparentish
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            RelativeLayout v = (RelativeLayout) findViewById(R.id.rl_top);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, getStatusBarHeight(), 0, -getStatusBarHeight());
//            v.setPadding(getStatusBarHeight(), getStatusBarHeight(), getStatusBarHeight(), 0);
        }*/
        map_view=(FrameLayout)findViewById(R.id.map_view);
        recycler_list_view=(RecyclerView)findViewById(R.id.recycler_list_view);
        listdata = (ArrayList) DummyHotelData.getListData();
        recycler_list_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HotelListAdapter(listdata, this);
        recycler_list_view.setAdapter(adapter);
        txt_location = (TextView) findViewById(R.id.txt_location);
        setupUiElements();
        locationProvider = new LocationProvider(this, this, this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        DrawerLayout.LayoutParams lll = (DrawerLayout.LayoutParams) drawer.getLayoutParams();

//        lll.set
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        drawerFragment = (FragmentDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), null);
        drawerFragment.setDrawerListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sourceLocation == null) {
                    locationProvider = new LocationProvider(MainActivity.this, MainActivity.this, MainActivity.this);
                    locationProvider.connect();
                }
            }
        }, (1000 * 10));

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationProvider.connect();
    }

    private boolean isLocationManual = false;
    private boolean isDialogShown = false;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!MyApp.isLocationEnabled(this) && !isLocationManual && !isDialogShown) {
            isDialogShown = true;
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.location_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button btn_locate = (Button) dialog.findViewById(R.id.btn_locate);
            TextView txt_enter_manually = (TextView) dialog.findViewById(R.id.txt_enter_manually);
            txt_enter_manually.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogShown = false;
                    isLocationManual = true;
                    /*Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra(AppConstant.EXTRA_1, "Enter your location");
                    MainActivity.this.startActivityForResult(intent, 122);*/
                    dialog.dismiss();
                }
            });

            btn_locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableGPS();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sourceLocation != null && isFirstSet) {
//            currentLocation.distanceTo()
            // getNearbyDrivers(sourceLocation.latitude + "", sourceLocation.longitude + "");
        }


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
                                            MainActivity.this,
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
                                status.startResolutionForResult(MainActivity.this,
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

                                status.startResolutionForResult(MainActivity.this,
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")


    public void onClick(View v) {
        super.onClick(v);
        if (v == navBtn) {
            drawer.openDrawer(GravityCompat.START);

        } else if (v == btn_search) {
            if(count== 0) {
                count = 1;
                map_view.setVisibility(View.GONE);
                btn_search.setImageResource(R.drawable.ic_map);
                recycler_list_view.setVisibility(View.VISIBLE);
            }
            else {
                count=0;
                btn_search.setImageResource(R.drawable.ic_view_list);
                map_view.setVisibility(View.VISIBLE);
                recycler_list_view.setVisibility(View.GONE);
            }
        }else if(v == tv_select_type){
            typeSelection();

        }else if(v ==tv_select_subtype){
            subtypeSelection();
        }else if(v== img_btn_notification){
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        }else if(v== tv_help){

            startActivity(new Intent(MainActivity.this, HelpActivity.class));

        }else if(v == tv_filter){
            startActivity(new Intent(MainActivity.this, FilterActivity.class));
        }else if(v == tv_show_open_house){
            startActivity(new Intent( MainActivity.this, OpenHouseActivity.class));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Place place;
        switch (requestCode) {
            case 122:
                if (resultCode == -1) {
                    place = SingleInstance.getInstance().getSelectedPlace();
                    this.sourceLocation = place.getLatLng();
                    Log.i("", "Place: " + place.getName());
                    txt_location.setText(place.getAddress().toString().replace("\n", " "));
                    this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(place.getLatLng()).zoom(15.5f).tilt(0.0f).build()));
                    return;
                } else if (resultCode != 2) {
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }


    private void setupUiElements() {

        navBtn = (ImageButton) findViewById(R.id.nav_drawer_btn);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        img_btn_notification = (ImageButton) findViewById(R.id.img_btn_notification);


        tv_filter = (TextView) findViewById(R.id.tv_filter);
        tv_show_open_house = (TextView) findViewById(R.id.tv_show_open_house);
        tv_select_type = (TextView) findViewById(R.id.tv_select_type);
        tv_select_subtype = (TextView) findViewById(R.id.tv_select_subtype);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_chat = (TextView) findViewById(R.id.tv_chat);

        chek_box_appointment = (CheckBox) findViewById(R.id.chek_box_appointment);


        setClick(R.id.nav_drawer_btn);
        setClick(R.id.btn_search);
        setClick(R.id.img_btn_notification);

        setClick(R.id.tv_filter);
        setClick(R.id.tv_show_open_house);
        setClick(R.id.tv_select_type);
        setClick(R.id.tv_select_subtype);
        setClick(R.id.tv_help);
        setClick(R.id.tv_chat);


    }

  /*  public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_select_type) {
            typeSelection();
        }

    }*/

    private void typeSelection() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.buy_sell_dialog);
        Button diag_btn_buy = (Button) dialog.findViewById(R.id.diag_btn_buy);
        Button diag_btn_sell = (Button) dialog.findViewById(R.id.diag_btn_sell);

        diag_btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        diag_btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void subtypeSelection() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sub_type_dialog);

        CheckBox chek_casas = (CheckBox) dialog.findViewById(R.id.chek_casas);
        CheckBox chek_locals = (CheckBox) dialog.findViewById(R.id.chek_locals);
        CheckBox chek_oficinas = (CheckBox) dialog.findViewById(R.id.chek_oficinas);
        CheckBox chek_terrenos = (CheckBox) dialog.findViewById(R.id.chek_terrenos);
        CheckBox chek_departmentos = (CheckBox) dialog.findViewById(R.id.chek_departmentos);
        TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);


        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
//            changeMap(mLastLocation);
            Log.d(TAG, "ON connected");

        } else
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        MyApp.showMassage(this, "GoogleApi Connection failed...");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnCameraIdleListener(this);

        View mapView = mapFragment.getView();
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 300);
        }
    }

    private void changeMap(Location location) {

        Log.d(TAG, "Reaching map" + mMap);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;


            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(15.5f).tilt(0).build();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            getCompleteAddressString(location.getLatitude(), location.getLongitude());

        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private List<Marker> markers = new ArrayList<>();

    private LatLngBounds adjustBoundsForMaxZoomLevel(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;
        double deltaLat = Math.abs((sw.latitude - this.sourceLocation.latitude) - (ne.latitude - this.sourceLocation.latitude));
        double deltaLon = Math.abs((sw.longitude - this.sourceLocation.longitude) - (ne.longitude - this.sourceLocation.longitude));
        LatLng latLng;
        LatLng ne2;
        LatLngBounds latLngBounds;
        if (deltaLat < 0.005d) {
            latLng = new LatLng(sw.latitude - (0.005d - (deltaLat / 2.0d)), sw.longitude);
            ne2 = new LatLng(ne.latitude + (0.005d - (deltaLat / 2.0d)), ne.longitude);
            latLngBounds = new LatLngBounds(latLng, ne2);
            ne = ne2;
            sw = latLng;
        } else if (deltaLon < 0.005d) {
            latLng = new LatLng(sw.latitude, sw.longitude - (0.005d - (deltaLon / 2.0d)));
            ne2 = new LatLng(ne.latitude, ne.longitude + (0.005d - (deltaLon / 2.0d)));
            latLngBounds = new LatLngBounds(latLng, ne2);
            ne = ne2;
            sw = latLng;
        }
        LatLngBounds.Builder displayBuilder = new LatLngBounds.Builder();
        displayBuilder.include(new LatLng(this.sourceLocation.latitude, this.sourceLocation.longitude));
        displayBuilder.include(new LatLng(this.sourceLocation.latitude + deltaLat, this.sourceLocation.longitude + deltaLon));
        displayBuilder.include(new LatLng(this.sourceLocation.latitude - deltaLat, this.sourceLocation.longitude - deltaLon));
        this.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(displayBuilder.build(), 100));
        this.mMap.setMaxZoomPreference(15.5f);
        return bounds;
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(this.sourceLocation.latitude, this.sourceLocation.longitude));

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(adjustBoundsForMaxZoomLevel(builder.build()), 50);

        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                txt_location.setText(addresses.get(0).getSubLocality());
                if (txt_location.getText().toString().isEmpty()) {
                    txt_location.setText(addresses.get(0).getLocality());
                    if (txt_location.getText().toString().isEmpty()) {
                        txt_location.setText(strAdd.replace("\n", " "));
                    }
                }
                // txt_address.setText(strReturnedAddress.toString().replace("\n", " "));
                Log.w("address", "" + strReturnedAddress.toString());
            } else {
                Log.w("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Canont get Address!");
        }

        return strAdd;
    }

    @Override
    public void handleNewLocation(Location location) {
        sourceLocation = new LatLng(location.getLatitude(), location.getLongitude());
        try {
            if (location != null && !isFirstSet) {
                sourceLocation = new LatLng(location.getLatitude(), location.getLongitude());
                changeMap(location);
                isFirstSet = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleManualPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1010);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        if (position == 0) {
            //  MyApp.showMassage(getContext(), "will go to my service requests");

        } else if (position == 1) {

        } else if (position == 2) {

        } else if (position == 3) {

        } else if (position == 4) {

        } else if (position == 5) {

        } else if (position == 6) {

        } else if (position == 7) {


            //  MyApp.showMassage(getContext(), "will invite your friends");
        }
    }

    private LatLng mCenterLatLong;

    @Override
    public void onCameraIdle() {
        Log.d("Camera position change", this.mMap.getCameraPosition() + "");
        this.mCenterLatLong = this.mMap.getCameraPosition().target;
        this.sourceLocation = this.mCenterLatLong;
        try {
            Location mLocation = new Location("");
            mLocation.setLatitude(this.mCenterLatLong.latitude);
            mLocation.setLongitude(this.mCenterLatLong.longitude);
            getCompleteAddressString(this.mCenterLatLong.latitude, this.mCenterLatLong.longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
