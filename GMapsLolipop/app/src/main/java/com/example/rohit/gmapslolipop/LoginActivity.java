package com.example.rohit.gmapslolipop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.rohit.gmapslolipop.profile.EMAIL_SHARED_PREF;
import static com.example.rohit.gmapslolipop.profile.ID_SHARED_PREF;
import static com.example.rohit.gmapslolipop.profile.KEY_EMAIL;
import static com.example.rohit.gmapslolipop.profile.KEY_PASSWORD;
import static com.example.rohit.gmapslolipop.profile.LOGGEDIN_SHARED_PREF;
import static com.example.rohit.gmapslolipop.profile.LOGIN_URL;
import static com.example.rohit.gmapslolipop.profile.SHARED_PREF_NAME;


public class LoginActivity extends AppCompatActivity {

    public static String UID_public;
    Button mRegButton;
    Button mLogin;
    private EditText mEmailView;
    private EditText mPasswordView;
    private AlertDialog.Builder builder;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mRegButton = findViewById(R.id.registerButton);
        mLogin = findViewById(R.id.email_sign_in_button);
         builder = new AlertDialog.Builder(this);
        builder.setTitle("Error !");
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mRegButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login() {
        loading = ProgressDialog.show(LoginActivity.this, "Please wait", null, true, true);
        if (isOnline()) {
            final String email = mEmailView.getText().toString().trim();
            final String password = mPasswordView.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equalsIgnoreCase("-1")) {
                                loading.dismiss();
                                Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_LONG).show();
                                builder.setTitle("Error");
                                builder.setMessage("Incorrect User Name / Password ");
                                builder.create();
                                builder.show();
                            } else {
                                UID_public = response.trim();
//                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
//                                editor.putString(EMAIL_SHARED_PREF, email);
//                                editor.putString(ID_SHARED_PREF, UID_public);
                               // editor.apply();
                                loading.dismiss();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Error Login", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> prams = new HashMap<>();
                    prams.put(KEY_EMAIL, email);
                    prams.put(KEY_PASSWORD, password);
                    return prams;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            //Create Dialog
            builder.setMessage("Please Check Your Internet ");
            builder.create();
            builder.show();
        }
    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

