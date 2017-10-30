package com.a123;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;

public class LoginActivity extends CustomActivity {
    private EditText edt_username, edt_password;
    private TextView tv_btn_signin, tv_forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signin);
        setTouchNClick(R.id.tv_forget_password);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_forget_password=(TextView)findViewById(R.id.tv_forget_password);
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

            startActivity(new Intent(getContext(), MainActivity.class));

        }else if(v.getId() == R.id.tv_forget_password){
            Toast.makeText(getContext(), "Forget_password", Toast.LENGTH_SHORT).show();
        }

    }

    private Context getContext() {
        return LoginActivity.this;
    }
}
