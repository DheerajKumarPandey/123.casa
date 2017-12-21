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
import com.a123.utills.AppConstant;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PropertyDetailActivity extends CustomActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private SliderLayout slider_home_frag;
    private String position;
    private TextView tv_min_price;
    private TextView tv_max_price;
    private TextView tv_date;
    private TextView tv_for;
    private TextView tv_prop_type;
    private TextView tv_city;
    private TextView tv_bedrooms;
    private TextView tv_bedroom_desc;
    private TextView tv_general_desc;
    private TextView tv_equipment;
    private TextView tv_included_services;
    private TextView tv_maintenance_fee;
    private TextView tv_material_muros;
    private TextView tv_material_pisos;
    private TextView tv_material_pinturas;
    private TextView tv_material_carpenteria;
    private TextView tv_material_ventaneria;
    private TextView tv_material_detalles;
    private TextView tv_material_muebles;
    private TextView tv_techos;
    private TextView tv_impermeabilizacion;

    private TextView tv_llaves;
    private TextView tv_sanitarios;
    private TextView tv_azulejos;
    private TextView tv_estado;
    private TextView tv_discripion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        position = getIntent().getStringExtra("position");
        Toast.makeText(this, "Position : " + position, Toast.LENGTH_SHORT).show();
        setResponseListener(this);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        slider_home_frag = (SliderLayout) findViewById(R.id.slider_home_frag);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("", R.drawable.hotel);
        file_maps.put("", R.drawable.hotel);
        file_maps.put("", R.drawable.hotel);
        file_maps.put("", R.drawable.hotel);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            slider_home_frag.addSlider(textSliderView);
        }
        slider_home_frag.setPresetTransformer(SliderLayout.Transformer.Default);
        slider_home_frag.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider_home_frag.setCustomAnimation(new DescriptionAnimation());
        slider_home_frag.setDuration(4000);
        slider_home_frag.addOnPageChangeListener(this);
        propertyDetail();
        setupUiElement();
    }

    private void setupUiElement() {

        //  setTouchNClick(R.id.tv_btn_signin);

        tv_min_price = (TextView) findViewById(R.id.tv_min_price);
        tv_max_price = (TextView) findViewById(R.id.tv_max_price);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_for = (TextView) findViewById(R.id.tv_for);
        tv_prop_type = (TextView) findViewById(R.id.tv_prop_type);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_bedrooms = (TextView) findViewById(R.id.tv_bedrooms);
        tv_bedroom_desc = (TextView) findViewById(R.id.tv_bedroom_desc);
        tv_general_desc = (TextView) findViewById(R.id.tv_general_desc);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment);
        tv_included_services = (TextView) findViewById(R.id.tv_included_services);
        tv_maintenance_fee = (TextView) findViewById(R.id.tv_maintenance_fee);
        tv_material_muros = (TextView) findViewById(R.id.tv_material_muros);
        tv_material_pisos = (TextView) findViewById(R.id.tv_material_pisos);
        tv_material_pinturas = (TextView) findViewById(R.id.tv_material_pinturas);
        tv_material_carpenteria = (TextView) findViewById(R.id.tv_material_carpenteria);
        tv_material_ventaneria = (TextView) findViewById(R.id.tv_material_ventaneria);
        tv_material_detalles = (TextView) findViewById(R.id.tv_material_detalles);
        tv_material_muebles = (TextView) findViewById(R.id.tv_material_muebles);
        tv_techos = (TextView) findViewById(R.id.tv_techos);
        tv_impermeabilizacion = (TextView) findViewById(R.id.tv_impermeabilizacion);

        tv_llaves = (TextView) findViewById(R.id.tv_llaves);
        tv_sanitarios = (TextView) findViewById(R.id.tv_sanitarios);
        tv_azulejos = (TextView) findViewById(R.id.tv_azulejos);
        tv_estado = (TextView) findViewById(R.id.tv_estado);
        tv_discripion = (TextView) findViewById(R.id.tv_discripion);


    }

    private void propertyDetail() {
        RequestParams p = new RequestParams();
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());
        p.put("propertyId", MyApp.getApplication().readUserList().get(Integer.parseInt(position)).getId());
        postCall(getContext(), AppConstant.BASE_URL + "propertyDetail", p, "Getting Details..", 1);
    }


/*    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            *//*if (TextUtils.isEmpty(edt_username.getText().toString())) {
                edt_username.setError("Enter UserName");
                return;
            } else if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter Password");
                return;
            }
            userLogin();*//*

            //startActivity(new Intent(getContext(), MainActivity.class));

        }

    }*/


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                try {
//                    JSONArray arr = new JSONObject(o.optString("message")).getJSONArray("info");
                    JSONObject oo = o.getJSONObject("info");
                    tv_min_price.setText(oo.optString("min_price"));
                    tv_max_price.setText(oo.optString("max_price"));
                    tv_date.setText(oo.optString("created_at"));
                    tv_for.setText("For :"+oo.optString("type_of_operation").toString());
                    tv_prop_type.setText("Type Of Property :"+oo.optString("type_of_property"));
                    tv_city.setText("City :"+oo.optString("city"));
                    tv_bedrooms.setText("Bedrooms :"+oo.optString("bedroom"));
                    tv_bedroom_desc.setText("Bedroom Description :"+oo.optString("bedroom_discription"));
                    tv_general_desc.setText("General Description :"+oo.optString("general_discription"));
                    tv_equipment.setText("Equipment :"+oo.optString("equipment"));
                    tv_included_services.setText("Included Services :"+oo.optString("included_services"));
                    tv_maintenance_fee.setText("Maintenance Fee :"+oo.optString("maintenance_fee"));
                    tv_material_muros.setText("Material-Muros :"+oo.optString("material_muros"));
                    tv_material_pisos.setText("Material-Pisos :"+oo.optString("material_pisos"));
                    tv_material_pinturas.setText("Material-Pinturas :"+oo.optString("material_pinturas"));
                    tv_material_carpenteria.setText("Material-Carpenteria :"+oo.optString("material_carpinteria"));
                    tv_material_ventaneria.setText("Material-Ventaneria :"+oo.optString("material_ventaneria"));
                    tv_material_detalles.setText("Material-Detalles De Construccion:"+oo.optString("material_detalles"));
                    tv_material_muebles.setText("Material-Muebles De Bano :"+oo.optString("material_muebles"));
                    tv_techos.setText("Techos :"+oo.optString("techos"));
                    tv_impermeabilizacion.setText("Impermeabilizacion :"+oo.optString("impermeabilizacion").toString());

                    tv_llaves.setText("Llaves Banos:"+oo.optString("llaves_banos"));
                    tv_sanitarios.setText("Sanitarios :"+oo.optString("sanitarios"));
                    tv_azulejos.setText("Azulejos :"+oo.optString("azulejos"));
                    tv_estado.setText("Estado de conservacion :"+oo.optString("estado_de_conservacion"));
                    tv_discripion.setText("Discripion Para Anuncio :"+oo.optString("discripcion_para_anuncio"));

                    JSONObject ooo = oo.getJSONObject("property_images");
                    Toast.makeText(this, ""+ooo.optString("0"), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    private Context getContext() {
        return PropertyDetailActivity.this;
    }

}
