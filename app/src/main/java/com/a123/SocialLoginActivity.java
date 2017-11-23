package com.a123;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a123.custome.CustomActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import static java.sql.Types.NULL;

public class SocialLoginActivity extends CustomActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    private TextView tv_btn_fb_signup, tv_btn_gmail_signup, tv_btn_email_signup, tv_already_acc;

    private LoginButton fb_loginButton;
    private SignInButton gmail_signInButton;
    private CallbackManager callbackManager;
    private TextView newUserTextView, signUpTextView;
    private TextView forgetpassword;
    private EditText userNameEditText, loginPasswordEditText;
    private Button Login_btn;
    private ImageButton see_password;
    private boolean isMainLobbyStarted = false;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private boolean showPassword = false;
    private static final String TAG = SocialLoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);
        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_fb_signup);
        setTouchNClick(R.id.tv_btn_gmail_signup);
        setTouchNClick(R.id.tv_btn_email_signup);
        setTouchNClick(R.id.tv_already_acc);


        tv_btn_fb_signup = (TextView) findViewById(R.id.tv_btn_fb_signup);
        tv_btn_gmail_signup = (TextView) findViewById(R.id.tv_btn_gmail_signup);
        tv_btn_email_signup = (TextView) findViewById(R.id.tv_btn_email_signup);
        tv_already_acc = (TextView) findViewById(R.id.tv_already_acc);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_fb_signup) {
            Toast.makeText(getContext(), "Facebook Signup", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_btn_gmail_signup) {
            signIn();
        } else if (v.getId() == R.id.tv_btn_email_signup) {
            startActivity(new Intent(getContext(), SignUpActivity.class));
        } else if (v.getId() == R.id.tv_already_acc) {

           startActivity(new Intent(getContext(), LoginActivity.class));
        }

    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /*private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }*/
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }*/
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            //GoogleSignInAccount acct = result.getSignInAccount();
            // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            Intent googleIntent = new Intent(getContext(), MainActivity.class);
            startActivity(googleIntent);
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Fail to sign in", Toast.LENGTH_SHORT).show();
            /*mStatusTextView.setText(R.string.signed_out);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*/
        }
    }

    private Context getContext() {
        return SocialLoginActivity.this;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
