package com.a123;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.model.User;
import com.a123.utills.AppConstant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static java.sql.Types.NULL;

public class SignUpActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_signup_as, tv_btn_signup;
    private EditText edt_name, edt_email, edt_password, edt_dob, edt_phone, edt_address;
    private Toolbar toolbar;
    private ImageButton img_btn_show_hide;
    private boolean showPassword = false;
    private String loginType = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setResponseListener(this);
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

        setTouchNClick(R.id.tv_signup_as);
        setTouchNClick(R.id.tv_btn_signup);
        setTouchNClick(R.id.img_btn_show_hide);

        img_btn_show_hide = (ImageButton) findViewById(R.id.img_btn_show_hide);
        tv_signup_as = (TextView) findViewById(R.id.tv_signup_as);
        tv_btn_signup = (TextView) findViewById(R.id.tv_btn_signup);
        tv_btn_signup.setVisibility(View.GONE);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_password = (EditText) findViewById(R.id.edt_password);
        String htmlString = "<u>SIGN UP AS ? </u>";
        tv_signup_as.setText(Html.fromHtml(htmlString));


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_signup_as) {
            if (TextUtils.isEmpty(edt_name.getText().toString())) {
                edt_name.setError("Enter Your Name");
                return;
            } else if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter Password");
                return;
            } else if (TextUtils.isEmpty(edt_email.getText().toString())) {
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

        } else if (v.getId() == R.id.img_btn_show_hide) {
            if (showPassword == false) {
                img_btn_show_hide.setImageResource(R.drawable.eye_hidden);
                edt_password.setTransformationMethod(null);
                showPassword = true;
            } else {
                img_btn_show_hide.setImageResource(R.drawable.eye_open);
                edt_password.setTransformationMethod(new PasswordTransformationMethod());
                showPassword = false;
            }

        }

    }


    private void userSignup() {
        RequestParams p = new RequestParams();
        p.put("name", edt_name.getText().toString());
        p.put("email", edt_email.getText().toString());
        p.put("password", edt_password.getText().toString());
        p.put("dob", edt_dob.getText().toString());
        p.put("PhoneNo", edt_phone.getText().toString());
        p.put("Address", edt_address.getText().toString());
        p.put("loginType", loginType);
        p.put("socialLoginType", "0");
        p.put("appVersion", "1.0");
        p.put("deviceType", "Android");
        p.put("deviceToken", MyApp.getSharedPrefString(AppConstant.DEVICE_TOKEN).toString());
        postCall(getContext(), AppConstant.BASE_URL + "SignUp", p, "Registering User", 1);


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
                    loginType = "1";

                } else if (img_btn_agent.isSelected()) {
                    loginType = "2";
                    Toast.makeText(getContext(), "Agent Signup", Toast.LENGTH_SHORT).show();
                } else if (img_btn_seller_buyer.isSelected()) {
                    loginType = "3";
                    Toast.makeText(getContext(), "Seller and Buyer", Toast.LENGTH_SHORT).show();
                } else {
                    loginType = "1";
                    Toast.makeText(getContext(), "User Signup", Toast.LENGTH_SHORT).show();
                }
                userSignup();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private Context getContext() {
        return SignUpActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                Type listType= new TypeToken<List<User.Info>>(){
                }.getType();

                try {
                  /*  List<User.Info>u= new Gson().fromJson(o.getJSONArray("info").toString(),listType);
//                    User ur =new Gson().fromJson(o.toString(),User.class);
                    MyApp.getApplication().writeUser(u);
                   *//* User u = new Gson().fromJson(o.getJSONObject("info").toString(), User.class);
                    MyApp.getApplication().writeUser(u);*/

                    List<User.Info> u = new Gson().fromJson(o.getJSONArray("info").toString(), listType);
                    MyApp.getApplication().writeUser(u);
                    MyApp.setStatus(AppConstant.IS_LOGIN,true);
                    finishAffinity();
                }catch (JSONException e){
                    e.printStackTrace();
                    MyApp.popMessage("Alert!","Parsing error.", getContext());
                }catch (JsonSyntaxException ee){

                }
                if(loginType.equals("1")) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                }else if(loginType.equals("2")){
                    startActivity(new Intent(getContext(), SellerHomeActivity.class));
                }else {
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }else {
                MyApp.popMessage("Error",o.optString("message"),getContext());
            }
        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {
        MyApp.popMessage("Error", error, getContext());
    }
}
