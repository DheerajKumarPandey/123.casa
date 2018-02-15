package com.a123;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.adapter.DummyPropertyData;
import com.a123.adapter.DummySliderData;
import com.a123.adapter.PropertyListAdapter;
import com.a123.adapter.SliderListAdapter;
import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.fragment.FragmentDrawer;
import com.a123.model.AppointmentData;
import com.a123.model.UserList;
import com.a123.utills.AppConstant;
import com.baoyz.actionsheet.ActionSheet;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SellerHomeActivity extends CustomActivity implements CustomActivity.ResponseCallback, FragmentDrawer.FragmentDrawerListener, ActionSheet.ActionSheetListener {
    private ImageButton nav_drawer_btn, img_btn_notification, btn_camera;
    private FragmentDrawer fragmentDrawer;
    private DrawerLayout drawerLayout;
    private RecyclerView recycler_slider;
    private RecyclerView recycler_property_list;
    private PropertyListAdapter adapter;
    private ArrayList listdata;
    private TextView tv_news_txt;

    private SliderListAdapter sliderListAdapter;
    private ArrayList arrayList;
    private FloatingActionMenu menu_red;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionButton fab4;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        setResponseListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        fragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), null);
        fragmentDrawer.setDrawerListener(this);
        setupUiElements();
        checkSubmitId();
    }

    private void setupUiElements() {
        setTouchNClick(R.id.nav_drawer_btn);
        setTouchNClick(R.id.img_btn_notification);
        setTouchNClick(R.id.btn_camera);
        setTouchNClick(R.id.menu_red);


        tv_news_txt = (TextView) findViewById(R.id.tv_news_txt);
        tv_news_txt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_news_txt.setText("General Information... general information... General Information");
        tv_news_txt.setSelected(true);
        tv_news_txt.setSingleLine(true);
        nav_drawer_btn = (ImageButton) findViewById(R.id.nav_drawer_btn);
        img_btn_notification = (ImageButton) findViewById(R.id.img_btn_notification);
        btn_camera = (ImageButton) findViewById(R.id.btn_camera);

        recycler_slider = (RecyclerView) findViewById(R.id.recycler_slider);
        arrayList = (ArrayList) DummySliderData.getListData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_slider.setLayoutManager(layoutManager);
        sliderListAdapter = new SliderListAdapter(arrayList, this);
        recycler_slider.setAdapter(sliderListAdapter);

        recycler_property_list = (RecyclerView) findViewById(R.id.recycler_property_list);
        listdata = (ArrayList) DummyPropertyData.getListData();
        recycler_property_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PropertyListAdapter(listdata, this);
        recycler_property_list.setAdapter(adapter);

        menu_red = (FloatingActionMenu) findViewById(R.id.menu_red);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

       /* fab1.setEnabled(false);
        menu_red.setClosedOnTouchOutside(true);
        menu_red.hideMenuButton(false);
        menus.add(menu_red);*/

        setTouchNClick(R.id.fab1);
        setTouchNClick(R.id.fab2);
        setTouchNClick(R.id.fab3);
        setTouchNClick(R.id.fab4);


        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.nav_drawer_btn) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else if (v.getId() == R.id.img_btn_notification) {
            myAppointment();
           // startActivity(new Intent(getContext(), NotificationActivity.class));
        } else if (v.getId() == R.id.btn_camera) {
            startActivity(new Intent(getContext(), PictureActivity.class));
        } else if (v.getId() == R.id.menu_red) {
            if (menu_red.isOpened()) {
                Toast.makeText(getContext(), menu_red.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
            }

            menu_red.toggle(true);
        } else if (v.getId() == R.id.fab1) {
            menu_red.close(true);

        } else if (v.getId() == R.id.fab2) {
            menu_red.close(true);
            fab2.setVisibility(View.GONE);
        } else if (v.getId() == R.id.fab3) {
            menu_red.close(true);
            fab2.setVisibility(View.VISIBLE);
            startActivity(new Intent(getContext(), PictureActivity.class));
        } else if (v.getId() == R.id.fab4) {
            menu_red.close(true);
            setTheme(R.style.ActionSheetStyleiOS7);
            showActionSheet();
        }
    }

    private void myAppointment(){
        RequestParams p = new RequestParams();
        p.put("person_id", MyApp.getApplication().readUser().get(0).getId());
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("socialLoginType",MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(getContext(), AppConstant.BASE_URL + "myAppointment", p, "Collecting Info...", 1);
    }

    private void checkSubmitId(){
        RequestParams p = new RequestParams();
        p.put("client_id", MyApp.getApplication().readUser().get(0).getId());
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("socialLoginType",MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(getContext(), AppConstant.BASE_URL + "checkSubmitId", p, "Checking...", 2);
    }

    public void showActionSheet() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("", "Add Listing", "Show Listing")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Context getContext() {
        return SellerHomeActivity.this;
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
        // Toast.makeText(getApplicationContext(), "dismissed isCancle = " + isCancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (index == 1) {
            startActivity(new Intent(getContext(), AddPropertyActivity.class));
        } else if (index == 2) {
            Toast.makeText(getContext(), "will direct to list Of property", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                Type listType = new TypeToken<List<AppointmentData.Info>>() {
                }.getType();

                try {
                    List<AppointmentData.Info> u = new Gson().fromJson(o.getJSONArray("info").toString(), listType);

                    MyApp.getApplication().writeAppointmentData(u);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(getContext(), MyAppointmentUserActivity.class));

            } else {
                MyApp.popMessage("Error", o.optString("message"), getContext());
            }
        }

        if(callNumber == 2){
            if(o.optString("status").equals("1")){
                MyApp.popMessage("Successful", o.optString("message"), getContext());
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

    }
}
