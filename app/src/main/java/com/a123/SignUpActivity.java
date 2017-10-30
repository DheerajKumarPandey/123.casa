package com.a123;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;

import static java.sql.Types.NULL;

public class SignUpActivity extends CustomActivity {
    private TextView tv_signup_as, tv_btn_signup;
    private EditText edt_name, edt_email,edt_password, edt_dob, edt_phone, edt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUiElement();

    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_signup_as);
        setTouchNClick(R.id.tv_btn_signup);


        tv_signup_as = (TextView) findViewById(R.id.tv_signup_as);
        tv_btn_signup = (TextView) findViewById(R.id.tv_btn_signup);
        tv_btn_signup.setVisibility(View.GONE);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_password=(EditText)findViewById(R.id.edt_password);
        String htmlString = "<u>SIGN UP AS ? </u>";
        tv_signup_as.setText(Html.fromHtml(htmlString));


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_signup_as) {
            if (TextUtils.isEmpty(edt_name.getText().toString())) {
                edt_name.setError("Enter Your Name");
                return;
            }else if (TextUtils.isEmpty(edt_password.getText().toString())){
                edt_password.setError("Enter Password");
                return;
            }else if (TextUtils.isEmpty(edt_email.getText().toString())) {
                edt_email.setError("Enter Email");
                return;
            } else if (TextUtils.isEmpty(edt_dob.getText().toString())) {
                edt_dob.setError("Enter Date of Birth");
                return;
            } else if (TextUtils.isEmpty(edt_phone.getText().toString())) {
                edt_phone.setError("Enter Phone No");
                return;
            } else if (TextUtils.isEmpty(edt_address.getText().toString())) {
                edt_address.setError("Enter Address");
                return;
            }

            signupSelection();
            tv_btn_signup.setVisibility(View.VISIBLE);

        } else if (v.getId() == R.id.tv_btn_signup) {
            Toast.makeText(getContext(), "Successfully Signed Up", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), LoginActivity.class));

        }

    }


    private void signupSelection() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.signuptype_dialog);
        TextView tv_btn_proceed = (TextView) dialog.findViewById(R.id.tv_btn_proceed);

        ImageButton img_btn_cross = (ImageButton) dialog.findViewById(R.id.img_btn_cross);


        LinearLayout lnr_user = (LinearLayout) dialog.findViewById(R.id.lnr_user);
        LinearLayout lnr_agent = (LinearLayout) dialog.findViewById(R.id.lnr_agent);
        LinearLayout lnr_seller_buyer = (LinearLayout) dialog.findViewById(R.id.lnr_seller_buyer);

        final ImageButton img_btn_user = (ImageButton) dialog.findViewById(R.id.img_btn_user);
        final ImageButton img_btn_agent = (ImageButton) dialog.findViewById(R.id.img_btn_agent);
        final ImageButton img_btn_seller_buyer = (ImageButton) dialog.findViewById(R.id.img_btn_seller_buyer);

        final TextView tv_user = (TextView) dialog.findViewById(R.id.tv_user);
        final TextView tv_agent = (TextView) dialog.findViewById(R.id.tv_agent);
        final TextView tv_seller_buyer = (TextView) dialog.findViewById(R.id.tv_seller_buyer);

        img_btn_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lnr_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_btn_user.setSelected(true);
                img_btn_agent.setSelected(false);
                img_btn_seller_buyer.setSelected(false);
                img_btn_user.setBackgroundResource(R.drawable.image_buttong_bg);
                img_btn_agent.setBackgroundResource(NULL);
                img_btn_seller_buyer.setBackgroundResource(NULL);

                //tv_user, tv_agent, tv_seller_buyer
                tv_user.setTextColor(getResources().getColor(R.color.google_red));
                tv_agent.setTextColor(getResources().getColor(R.color.text_grey));
                tv_seller_buyer.setTextColor(getResources().getColor(R.color.text_grey));
            }
        });
        lnr_agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_btn_user.setSelected(false);
                img_btn_agent.setSelected(true);
                img_btn_seller_buyer.setSelected(false);
                img_btn_user.setBackgroundResource(NULL);
                img_btn_agent.setBackgroundResource(R.drawable.image_buttong_bg);
                img_btn_seller_buyer.setBackgroundResource(NULL);

                tv_user.setTextColor(getResources().getColor(R.color.text_grey));
                tv_agent.setTextColor(getResources().getColor(R.color.google_red));
                tv_seller_buyer.setTextColor(getResources().getColor(R.color.text_grey));
            }
        });
        lnr_seller_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_btn_user.setSelected(false);
                img_btn_agent.setSelected(false);
                img_btn_seller_buyer.setSelected(true);

                img_btn_user.setBackgroundResource(NULL);
                img_btn_agent.setBackgroundResource(NULL);
                img_btn_seller_buyer.setBackgroundResource(R.drawable.image_buttong_bg);

                //tv_user, tv_agent, tv_seller_buyer
                tv_user.setTextColor(getResources().getColor(R.color.text_grey));
                tv_agent.setTextColor(getResources().getColor(R.color.text_grey));
                tv_seller_buyer.setTextColor(getResources().getColor(R.color.google_red));
            }
        });

        tv_btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img_btn_user.isSelected()) {
                    Toast.makeText(getContext(), "User Signup", Toast.LENGTH_SHORT).show();
                } else if (img_btn_agent.isSelected()) {
                    Toast.makeText(getContext(), "Agent Signup", Toast.LENGTH_SHORT).show();
                } else if (img_btn_seller_buyer.isSelected()) {
                    Toast.makeText(getContext(), "Seller and Buyer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private Context getContext() {
        return SignUpActivity.this;
    }
}
