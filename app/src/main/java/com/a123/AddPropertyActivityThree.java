package com.a123;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPropertyActivityThree extends CustomActivity {
    private static final String TAG = "AddPropertyActivity";
    private Toolbar toolbar;
    private MultiSpinnerSearch searchMultiSpinner_muros, searchMultiSpinner_pisos, searchMultiSpinner_pinturas, searchMultiSpinner_carpintería,
            searchMultiSpinner_ventanería_y_elementos_térmicos, searchMultiSpinner_detalles_de_construcción, searchMultiSpinner_muebles_de_bano;



    private CheckBox chk_box_alfombras, chk_box_falso_plafon;
    private EditText edt_techos, edt_impermeabilizacion, edt_llaves_banos,
            edt_sanitarios, edt_azulejos, edt_estado_de_conservacion;
    private EditText edt_general_descriptions;
    private TextView tv_btn_submit_three;
    private ListView lst_upload_pic;
    private Button btn_add, btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property_three);
        toolbar = (Toolbar) findViewById(R.id.property_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_property_toolbar);
        mTitle.setText("3/3");
        actionBar.setTitle("");
        setupUiElement();

        lst_upload_pic=(ListView)findViewById(R.id.lst_upload_pic);
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        final  List<String> list_muros = Arrays.asList(getResources().getStringArray(R.array.muros_array));
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
        final List<String> list_carpintería = Arrays.asList(getResources().getStringArray(R.array.carpintería_array));
        final List<KeyPairBoolData> listArray_carpintería = new ArrayList<>();
        for (int i = 0; i < list_carpintería.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_carpintería.get(i));
            h.setSelected(false);
            listArray_carpintería.add(h);
        }
        final List<String> list_ventanería_y_elementos_térmicos = Arrays.asList(getResources().getStringArray(R.array.ventanería_y_elementos_térmicos_array));
        final List<KeyPairBoolData> listArray_ventanería_y_elementos_térmicos = new ArrayList<>();
        for (int i = 0; i < list_ventanería_y_elementos_térmicos.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_ventanería_y_elementos_térmicos.get(i));
            h.setSelected(false);
            listArray_ventanería_y_elementos_térmicos.add(h);
        }
        final List<String> list_detalles_de_construcción = Arrays.asList(getResources().getStringArray(R.array.detalles_de_construcción_array));
        final List<KeyPairBoolData> listArray_detalles_de_construcción = new ArrayList<>();
        for (int i = 0; i < list_detalles_de_construcción.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_detalles_de_construcción.get(i));
            h.setSelected(false);
            listArray_detalles_de_construcción.add(h);
        }
        final List<String> list_muebles_de_bano = Arrays.asList(getResources().getStringArray(R.array.muebles_de_bano_array));
        final List<KeyPairBoolData> listArray_muebles_de_bano = new ArrayList<>();
        for (int i = 0; i < list_muebles_de_bano.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list_muebles_de_bano.get(i));
            h.setSelected(false);
            listArray_muebles_de_bano.add(h);
        }

        searchMultiSpinner_muros.setItems(listArray_muros, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchMultiSpinner_pisos.setItems(listArray_pisos, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        searchMultiSpinner_pinturas.setItems(listArray_pinturas, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchMultiSpinner_carpintería.setItems(listArray_carpintería, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        searchMultiSpinner_ventanería_y_elementos_térmicos.setItems(listArray_ventanería_y_elementos_térmicos, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        searchMultiSpinner_detalles_de_construcción.setItems(listArray_detalles_de_construcción, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        searchMultiSpinner_muebles_de_bano.setItems(listArray_muebles_de_bano, -1, new SpinnerListener() {

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
        setTouchNClick(R.id.tv_btn_submit_three);

        searchMultiSpinner_muros = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_muros);
        searchMultiSpinner_pisos = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_pisos);
        searchMultiSpinner_pinturas = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_pinturas);
        searchMultiSpinner_carpintería = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_carpintería);
        searchMultiSpinner_ventanería_y_elementos_térmicos = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_ventanería_y_elementos_térmicos);
        searchMultiSpinner_detalles_de_construcción = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_detalles_de_construcción);
        searchMultiSpinner_muebles_de_bano = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner_muebles_de_bano);

        chk_box_alfombras = (CheckBox) findViewById(R.id.chk_box_alfombras);
        chk_box_falso_plafon = (CheckBox) findViewById(R.id.chk_box_falso_plafon);

        edt_techos = (EditText) findViewById(R.id.edt_techos);
        edt_impermeabilizacion = (EditText) findViewById(R.id.edt_impermeabilizacion);
        edt_llaves_banos = (EditText) findViewById(R.id.edt_llaves_banos);
        edt_sanitarios = (EditText) findViewById(R.id.edt_sanitarios);
        edt_azulejos = (EditText) findViewById(R.id.edt_azulejos);
        edt_estado_de_conservacion = (EditText) findViewById(R.id.edt_estado_de_conservacion);

        edt_general_descriptions = (EditText) findViewById(R.id.edt_general_descriptions);

        tv_btn_submit_three = (TextView) findViewById(R.id.tv_btn_submit_three);


    }

    public void onClick(View v){
        super.onClick(v);
        if(v.getId()==R.id.tv_btn_submit_three){
            //startActivity(new Intent(getContext(), ));
            Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
        }
    }


    private Context getContext(){return AddPropertyActivityThree.this;}
}
