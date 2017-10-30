package com.a123;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.a123.custome.CustomActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends CustomActivity {
    private static final int SPLASH_DURATION_MS = 2000;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getHashKey();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
               /* if (MyApp.getStatus(AppConstant.IS_LOGIN)) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {*/
                Intent i = new Intent(SplashActivity.this, SocialLoginActivity.class);
                startActivity(i);
                finish();
                // }

            }
        }, SPLASH_DURATION_MS);
    }

    private void getHashKey() {

try {

 PackageInfo info = getPackageManager().getPackageInfo(

 "com.a123",

 PackageManager.GET_SIGNATURES);

 for (Signature signature : info.signatures) {

 MessageDigest md = MessageDigest.getInstance("SHA");

 md.update(signature.toByteArray());

 Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

 }

 } catch (PackageManager.NameNotFoundException e) {



 } catch (NoSuchAlgorithmException e) {



}

}
}
