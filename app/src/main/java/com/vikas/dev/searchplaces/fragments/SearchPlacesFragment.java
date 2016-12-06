package com.vikas.dev.searchplaces.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vikas.dev.searchplaces.R;
import com.vikas.dev.searchplaces.adapters.StatesListAdapter;
import com.vikas.dev.searchplaces.lib.Constants;
import com.vikas.dev.searchplaces.lib.CustomApp;
import com.vikas.dev.searchplaces.model.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikasmalhotra on 12/6/16.
 */
public class SearchPlacesFragment extends Fragment {
    EditText etPlace;
    RecyclerView recyclerView;
    Button btnSearch;

    StatesListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_places, container, false);
        etPlace = (EditText) rootView.findViewById(R.id.etPlace);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

        adapter = new StatesListAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etPlace.getText())) {
                    getData(etPlace.getText().toString().trim());
                }
            }
        });
        return rootView;
    }

    private void getData(final String text) {
        String url = Constants.PLACES_URL + text;
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("RestResponse")) {
                                JSONObject restResponseObj = response.getJSONObject("RestResponse");
                                if (restResponseObj.has("result")) {
                                    JSONArray result = restResponseObj.getJSONArray("result");
                                    if (result != null && result.length() > 0) {
                                        List<State> states = new ArrayList<>();
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject res = result.getJSONObject(i);
                                            String country = safeJsonString(res, "country");
                                            String name = safeJsonString(res, "name");
                                            String abbr = safeJsonString(res, "abbr");
                                            String area = safeJsonString(res, "area");
                                            String largest_city = safeJsonString(res, "largest_city");
                                            String capital = safeJsonString(res, "capital");
                                            State state = new State(country, name, abbr, area, largest_city, capital);
                                            states.add(state);
                                        }

                                        adapter.setData(states);
                                    } else {
                                        Toast.makeText(getActivity(), R.string.not_found, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), R.string.not_found, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), R.string.not_found, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), R.string.not_found, Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });

        CustomApp.getInstance().addToRequestQueue(jsonObjReq, "SearchPlaces");
    }

    private String safeJsonString(JSONObject object, String key) {
        String res = "";
        try {
            if (object.has(key)) {
                res = object.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            res = "";
        }
        return res;
    }
}