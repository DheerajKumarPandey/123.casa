package com.a123;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.utills.AppConstant;

public class AddPropertyActivity extends CustomActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private Spinner property_type_spiner, operation_type_spiner, sub_type_spiner;
    private RelativeLayout rel_subtype;
    private EditText edt_min_price, edt_max_price;
    private EditText edt_street, edt_number, edt_appt_no, edt_area, edt_city, edt_country;
    private EditText edt_plantas, edt_recamaras, edt_description;
    private CheckBox chk_box_rec_en_plants_baja, chk_box_walk_in_closet;
    private TextView tv_btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        toolbar = (Toolbar) findViewById(R.id.property_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_property_toolbar);
        mTitle.setText("1/3");
        actionBar.setTitle("");
        setupUiElement();
        // addListenerOnSpinnerItemSelection();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_submit);

        property_type_spiner = (Spinner) findViewById(R.id.property_type_spiner);
        operation_type_spiner = (Spinner) findViewById(R.id.property_type_spiner);
        sub_type_spiner = (Spinner) findViewById(R.id.sub_type_spiner);
        property_type_spiner.setOnItemSelectedListener(this);
        rel_subtype = (RelativeLayout) findViewById(R.id.rel_subtype);
        rel_subtype.setVisibility(View.GONE);
        edt_min_price = (EditText) findViewById(R.id.edt_min_price);
        edt_max_price = (EditText) findViewById(R.id.edt_max_price);

        edt_street = (EditText) findViewById(R.id.edt_street);
        edt_number = (EditText) findViewById(R.id.edt_number);
        edt_appt_no = (EditText) findViewById(R.id.edt_appt_no);
        edt_area = (EditText) findViewById(R.id.edt_area);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_country = (EditText) findViewById(R.id.edt_country);

        edt_plantas = (EditText) findViewById(R.id.edt_plantas);
        edt_recamaras = (EditText) findViewById(R.id.edt_recamaras);
        edt_description = (EditText) findViewById(R.id.edt_description);

        chk_box_rec_en_plants_baja = (CheckBox) findViewById(R.id.chk_box_rec_en_plants_baja);
        chk_box_walk_in_closet = (CheckBox) findViewById(R.id.chk_box_rec_en_plants_baja);

        tv_btn_submit = (TextView) findViewById(R.id.tv_btn_submit);
    }

    /* public void addListenerOnSpinnerItemSelection() {
         property_type_spiner.setOnItemSelectedListener(new Ite);
         if(String.valueOf(property_type_spiner.getSelectedItem()).equals("Terreno")){
             rel_subtype.setVisibility(View.VISIBLE);
         }else {
             rel_subtype.setVisibility(View.GONE);
         }
     }*/
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_submit) {
            if(MyApp.getSharedPrefString(AppConstant.PropertyType).equals("Terreno")){
                startActivity(new Intent(getContext(),TerrenoActivity.class));
            }else if(MyApp.getSharedPrefString(AppConstant.PropertyType).equals("Bodega")){
                startActivity(new Intent(getContext(), BodegaActivity.class));
            }else if(MyApp.getSharedPrefString(AppConstant.PropertyType).equals("Oficina Local comercial")){
                startActivity(new Intent(getContext(), BodegaActivity.class));
            }else{
                startActivity(new Intent(getContext(), AddPropertyActivityTwo.class));
            }

        }

    }

    private Context getContext() {
        return AddPropertyActivity.this;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(adapterView.getContext(), "Position" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
        MyApp.setSharedPrefString(AppConstant.PropertyType,adapterView.getItemAtPosition(i).toString());
        if (adapterView.getItemAtPosition(i).equals("Terreno")) {
            rel_subtype.setVisibility(View.VISIBLE);

        } else if(adapterView.getItemAtPosition(i).equals("Casa")) {
            rel_subtype.setVisibility(View.GONE);
        }else{
            rel_subtype.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
