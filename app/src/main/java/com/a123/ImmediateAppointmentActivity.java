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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.utills.AppConstant;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ImmediateAppointmentActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private EditText  edt_contact_no, edt_description;
    private TextView tv_submit_appointment, tv_time_date;
    private Integer position;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String TAG = "Sample";
    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_appointment);
        setResponseListener(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    getString(R.string.clean) // Optional
            );
        }
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-d  HH:mm:ss", java.util.Locale.getDefault());
        // Assign unmodifiable values
        Calendar calendar = Calendar.getInstance();
        dateTimeFragment.set24HoursMode(true);
        int MMM= calendar.get(Calendar.MONTH);

    //    dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
       // dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MM-dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
               // textView.setText(myDateFormat.format(date));
                //Toast.makeText(ImmediateAppointmentActivity.this, "Date: "+myDateFormat.format(date), Toast.LENGTH_SHORT).show();
                tv_time_date.setText(myDateFormat.format(date));


            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                //textView.setText("");
            }
        });
        setupUiElement();

    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_submit_appointment);
        setTouchNClick(R.id.tv_time_date );

        tv_time_date = (TextView) findViewById(R.id.tv_time_date);

        edt_contact_no = (EditText) findViewById(R.id.edt_contact_no);
        edt_description = (EditText) findViewById(R.id.edt_description);

        tv_submit_appointment= (TextView)findViewById(R.id.tv_submit_appointment);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_submit_appointment) {
            if (TextUtils.isEmpty(edt_contact_no.getText().toString())) {
                edt_contact_no.setError("please enter your contact no");
                return;
            }
            immediateAppointment();
        }else if(v.getId() == R.id.tv_time_date){
            dateTimeFragment.startAtCalendarView();
            dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20,00).getTime());
            dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
        }

    }


    public void immediateAppointment() {
        RequestParams p = new RequestParams();
        p.put("client_id", MyApp.getApplication().readUser().get(0).getId());
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        p.put("property_id", MyApp.getApplication().readUserList().get(position).getId());
        p.put("appointment_time",tv_time_date.getText().toString());
        p.put("phone_no",edt_contact_no.getText().toString());
        p.put("lat", MyApp.getApplication().readUser().get(0).getLat());
        p.put("long",MyApp.getApplication().readUser().get(0).getLng());
        p.put("appointment_description", edt_description.getText());
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(getContext(), AppConstant.BASE_URL + "immediateAppointment", p, "Requesting...", 1);


    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {
                MyApp.popMessage("Success", o.optString("message"), getContext());
            } else {
                MyApp.popMessage("Error", o.optString("message"), getContext());
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Context getContext() {
        return ImmediateAppointmentActivity.this;
    }

}
