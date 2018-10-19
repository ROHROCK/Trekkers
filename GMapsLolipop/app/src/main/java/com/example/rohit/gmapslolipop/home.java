package com.example.rohit.gmapslolipop;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String URL_Treks = "https://sdltrekker.000webhostapp.com/trekksCRUD.php";
    private RecyclerView recyclerViewBottom;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private List<Trek> trekList;

    public home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getImages();
        // Inflate the layout for this fragment
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle);
        recyclerViewBottom = rootView.findViewById(R.id.recycleVertical);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getActivity(), mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(getContext()));
        trekList = new ArrayList<>();
        loadTreks();
        TrekAdapter trekAdapter = new TrekAdapter(getContext(), trekList);
        recyclerViewBottom.setAdapter(trekAdapter);
        return rootView;
    }

    private void loadTreks() {
        String purpose = "?purpose=trekkList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Treks + purpose,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject trek = array.getJSONObject(i);
                                //adding the product to product list
                                trekList.add(new Trek(
                                        trek.getString("tname"),
                                        trek.getString("tduration"),
                                        trek.getString("tclosestcity"),
                                        trek.getString("tidealtime")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            TrekAdapter adapter = new TrekAdapter(getContext(), trekList);
                            recyclerViewBottom.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Error");
                        builder.setMessage(error.getMessage());
                        builder.create();
                        builder.show();
                    }
                });
        Volley.newRequestQueue(Objects.requireNonNull(getContext())).add(stringRequest);
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2016/Aug/8de63680059345b6bf830f226c0a9c59.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2017/Jan/42cdd1969e0867ea7fff8623d086cd06.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2017/Nov/f8c0f152c3aa77fd6769a30df2661f87.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2016/Sep/82b1cab8190a32a2272d6a261dd8828c.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2016/Oct/3b1382fbc49d54746e55deaf1f388887.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2016/Nov/bf106e5620e413c10dc5491f4481571c.jpg");
        mImageUrls.add("https://www.adventurenation.com/semcms/media/offers/2015/Apr/f80813de162aa3abe3a8d30f80f16f74.jpg");

    }
}
