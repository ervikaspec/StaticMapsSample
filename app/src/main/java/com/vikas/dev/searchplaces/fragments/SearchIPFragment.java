package com.vikas.dev.searchplaces.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vikas.dev.searchplaces.R;
import com.vikas.dev.searchplaces.lib.Constants;
import com.vikas.dev.searchplaces.lib.CustomApp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vikasmalhotra on 12/6/16.
 */
public class SearchIPFragment extends Fragment {
    EditText etPlace;
    TextView ip, country, name, abbr, area, lat, lng;
    ImageView map;
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_ip, container, false);
        etPlace = (EditText) rootView.findViewById(R.id.etPlace);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

        ip = (TextView) rootView.findViewById(R.id.ip);
        country = (TextView) rootView.findViewById(R.id.country);
        name = (TextView) rootView.findViewById(R.id.name);
        abbr = (TextView) rootView.findViewById(R.id.abbr);
        area = (TextView) rootView.findViewById(R.id.area);
        lat = (TextView) rootView.findViewById(R.id.lat);
        lng = (TextView) rootView.findViewById(R.id.lng);

        map = (ImageView) rootView.findViewById(R.id.map);

        btnSearch.setText(R.string.search_ip);
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
        String url = Constants.GEOLOCATION_API + text;
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("city")) {
                                String _ip = safeJsonString(response, "ip");
                                String _country = safeJsonString(response, "country_name");
                                String _name = safeJsonString(response, "city");
                                String _abbr = safeJsonString(response, "region_code");
                                String _area = safeJsonString(response, "region_name");
                                String _lat = safeJsonString(response, "latitude");
                                String _lng = safeJsonString(response, "longitude");

                                ip.setText(_ip);
                                country.setText(_country);
                                name.setText(_name);
                                abbr.setText(_abbr);
                                area.setText(_area);
                                lat.setText(_lat);
                                lng.setText(_lng);

                                String mapsUrl = Constants.GOOGLE_STATIC_MAPS_URL;
                                mapsUrl += "center=" + _lat + "," + _lng;
                                mapsUrl += "&zoom=12";
                                mapsUrl += "&size=400x400";
                                mapsUrl += "&key=" + getActivity().getString(R.string.google_static_maps_key);
                                CustomApp.getInstance().getImageLoader().get(mapsUrl, new ImageLoader.ImageListener() {
                                    @Override
                                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                        map.setImageBitmap(response.getBitmap());
                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), R.string.error_getting_map, Toast.LENGTH_SHORT).show();
                                    }
                                });
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