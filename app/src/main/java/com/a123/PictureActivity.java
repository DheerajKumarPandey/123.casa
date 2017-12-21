package com.a123;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.a123.custome.CustomActivity;
import com.baoyz.actionsheet.ActionSheet;

public class PictureActivity extends CustomActivity implements ActionSheet.ActionSheetListener{
    private Toolbar toolbar;
    private ImageView img_property_image;
    private TextView tv_capture_pic, tv_submit;
    private EditText edt_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
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
        setTouchNClick(R.id.tv_capture_pic);
        setTouchNClick(R.id.tv_submit);

        img_property_image = (ImageView) findViewById(R.id.img_property_image);

        tv_capture_pic = (TextView) findViewById(R.id.tv_capture_pic);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        edt_description = (EditText) findViewById(R.id.edt_description);

    }
    public void onClick(View v){
        super.onClick(v);
        if(v.getId()==R.id.tv_capture_pic){
            Toast.makeText(getContext(), "open Gallery", Toast.LENGTH_SHORT).show();
        }else if(v.getId()== R.id.tv_submit){
            setTheme(R.style.ActionSheetStyleiOS7);
            showActionSheet();
        }

    }

    public void showActionSheet() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles( "Take Photo", "Choose Photo")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }
private Context getContext(){return PictureActivity.this;}

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if(index==0){
            Toast.makeText(getContext(), "Open up the Camera", Toast.LENGTH_SHORT).show();
        }else if(index == 1){
            Toast.makeText(getContext(), "Opens up the gallery", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
