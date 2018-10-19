package com.example.rohit.gmapslolipop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private static final String register_url = "https://sdltrekker.000webhostapp.com/UserRegistration/register.php";
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText favorite;
    private EditText hometown;
    private EditText age;
    private String user;
    private String em;
    private String pass;
    private String fav;
    private String a;
    private AlertDialog.Builder builder;
    private String TAG = "RegisterActivity";

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        username = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passEditText);
        Button btn_signup = findViewById(R.id.registerButton);
        hometown = findViewById(R.id.homeTownEdit);
        age = findViewById(R.id.ageEdit);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        user = username.getText().toString().trim().toLowerCase();
        em = email.getText().toString().trim().toLowerCase();
        pass = password.getText().toString().trim().toLowerCase();
        fav = hometown.getText().toString().trim().toLowerCase();
        a = age.getText().toString().trim().toLowerCase();
        register(user, em, pass, fav, a);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private void register(String user, String em, String pass, String fav, String a) {
        String url = "?user=" + user + "&em=" + em + "&pass=" + pass + "&fav=" + fav + "&a=" + a;
        @SuppressLint("StaticFieldLeak")
        class RegisterUser extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Error !");
                loading = ProgressDialog.show(RegisterActivity.this, "Please wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.cancel();
            }

            @Override
            protected String doInBackground(String... strings) {
                String s = strings[0];
                BufferedReader bufferedReader;
                try {
                    URL url1 = new URL(register_url + s);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String result;
                    result = bufferedReader.readLine();
                    return result;
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Error Signing up", Toast.LENGTH_SHORT).show();
                    builder.setMessage(e.getMessage());
                    builder.create();
                    builder.show();
                }
                return null;
            }
        }
        RegisterUser ur = new RegisterUser();
        ur.execute(url);
    }
}
