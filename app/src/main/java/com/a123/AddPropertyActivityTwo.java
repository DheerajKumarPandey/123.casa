package com.a123;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPropertyActivityTwo extends CustomActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private EditText edt_surface, edt_front_meters, edt_depth_meters;
    private EditText edt_general_descriptions;
    //   private Spinner equipment_spiner;
    private EditText edt_aires, edt_calefaccion, edt_cochera_electricae, edt_abanicos;
    // private Spinner included_services_spiner;
    private EditText edt_maintenance_fee;
    private TextView tv_btn_submit_two;
    private MultiSpinnerSearch searchMultiSpinner_distribution, searchMultiSpinner_equipment, searchMultiSpinner_included_services;
    private static final String TAG = "AddPropertyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property_two);
        toolbar = (Toolbar) findViewById(R.id.property_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_property_toolbar);
        mTitle.setText("2/3");
        actionBar.setTitle("");
        setupUiElement();


        searchMultiSpinner_distribution = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_distribution);
        searchMultiSpinner_equipment = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_equipment);
        searchMultiSpinner_included_services = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_included_services);

        final List<String> list_muros = Arrays.asList(getResources().getStringArray(R.array.muros_array));
        final List<KeyPairBoolData> listArray_muros = new ArrayList<>();
        for (int i = 0; i < list_muros.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_muros.get(i));
            h.setSelected(false);
            listArray_muros.add(h);
        }
        final List<String> list_pisos = Arrays.asList(getResources().getStringArray(R.array.pisos_array));
        final List<KeyPairBoolData> listArray_pisos = new ArrayList<>();
        for (int i = 0; i < list_pisos.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_pisos.get(i));
            h.setSelected(false);
            listArray_pisos.add(h);
        }
        final List<String> list_pinturas = Arrays.asList(getResources().getStringArray(R.array.pinturas_array));
        final List<KeyPairBoolData> listArray_pinturas = new ArrayList<>();
        for (int i = 0; i < list_pinturas.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_pinturas.get(i));
            h.setSelected(false);
            listArray_pinturas.add(h);
        }


        searchMultiSpinner_distribution.setItems(listArray_muros, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchMultiSpinner_equipment.setItems(listArray_pisos, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchMultiSpinner_included_services.setItems(listArray_pinturas, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


    }

    private void setupUiElement() {
        setTouchNClick(R.id.tv_btn_submit_two);

        //   equipment_spiner = (Spinner) findViewById(R.id.equipment_spiner);
        // included_services_spiner = (Spinner) findViewById(R.id.included_services_spiner);

        //   equipment_spiner.setOnItemSelectedListener(this);
        // included_services_spiner.setOnItemSelectedListener(this);

        edt_surface = (EditText) findViewById(R.id.edt_surface);
        edt_front_meters = (EditText) findViewById(R.id.edt_front_meters);
        edt_depth_meters = (EditText) findViewById(R.id.edt_depth_meters);

        edt_general_descriptions = (EditText) findViewById(R.id.edt_general_descriptions);

        edt_aires = (EditText) findViewById(R.id.edt_aires);
        edt_calefaccion = (EditText) findViewById(R.id.edt_calefaccion);
        edt_cochera_electricae = (EditText) findViewById(R.id.edt_cochera_electricae);
        edt_abanicos = (EditText) findViewById(R.id.edt_abanicos);

        edt_maintenance_fee = (EditText) findViewById(R.id.edt_maintenance_fee);

        tv_btn_submit_two = (TextView) findViewById(R.id.tv_btn_submit_two);
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_submit_two) {
            startActivity(new Intent(getContext(), AddPropertyActivityThree.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Context getContext() {
        return AddPropertyActivityTwo.this;
    }
}
