package com.example.rohit.gmapslolipop;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
public class search extends android.support.v4.app.Fragment {
    private static final String URL_Search = "https://sdltrekker.000webhostapp.com/trekksCRUD.php?purpose=read&tname=";
    View rootView;
    String sQuery;
    List<Trek> trekList;
    RecyclerView searchView;

    public search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        trekList = new ArrayList<>();
        searchView = rootView.findViewById(R.id.recycleVertical2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        searchView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TrekAdapter trekAdapter = new TrekAdapter(getContext(), trekList);
        searchView.setAdapter(trekAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((HomeActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sQuery = query.trim();
                searchRequest(sQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                trekList.clear();
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchRequest(String Query) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Search + Query,
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
                            searchView.setAdapter(adapter);
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
        Volley.newRequestQueue(Objects.requireNonNull(getContext())).add(stringRequest);
    }
}
