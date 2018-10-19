package com.example.rohit.gmapslolipop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {

    public static final String LOGIN_URL = "https://sdltrekker.000webhostapp.com/UserRegistration/login.php";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String LOGIN_SUCCESS = "success";
    public static final String SHARED_PREF_NAME = "tech";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String ID_SHARED_PREF = "id";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    static private String name, email, city;
    static private int age;
    EditText nameEdit;
    EditText ageEdit;
    EditText nearestCityEdit;
    EditText emailEdit;
    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        loadProfile();

        nameEdit = rootView.findViewById(R.id.nameEditText);
        ageEdit = rootView.findViewById(R.id.ageEditText);
        nearestCityEdit = rootView.findViewById(R.id.homeEditText);
        emailEdit = rootView.findViewById(R.id.emailEditText);
        return rootView;
    }

    private void loadProfile() {
        Log.i("loadProfile", "Function entered");
        String URL_profile = "https://sdltrekker.000webhostapp.com/getProfile.php?id=" + LoginActivity.UID_public;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            JSONObject obj = object.getJSONObject(0);
                            Log.i("loadProfile", "SUCCESS");
                            name = obj.getString("username");
                            city = obj.getString("city");
                            age = obj.getInt("age");
                            email = obj.getString("email");
                            nameEdit.setText(name);
                            ageEdit.setText(String.valueOf(age));
                            nearestCityEdit.setText(city);
                            emailEdit.setText(email);
                            Log.i("loadProfile", "SUCCESS 2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
