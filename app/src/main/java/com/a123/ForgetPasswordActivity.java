package com.a123;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;

public class ForgetPasswordActivity extends CustomActivity {
    private TextView tv_btn_submit;
    private EditText edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setupUiElement();
    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_submit);


        edt_email = (EditText) findViewById(R.id.edt_email);


        tv_btn_submit=(TextView)findViewById(R.id.tv_btn_submit);

    }
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_submit) {
            if (TextUtils.isEmpty(edt_email.getText().toString())) {
                edt_email.setError("Enter UserName");
                return;
            }

            Toast.makeText(getContext(), "SuccessFull", Toast.LENGTH_SHORT).show();

        }

    }

    private Context getContext() {
        return ForgetPasswordActivity.this;
    }

}
