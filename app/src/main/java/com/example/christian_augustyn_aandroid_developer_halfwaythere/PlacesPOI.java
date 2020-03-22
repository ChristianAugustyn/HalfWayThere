package com.example.christian_augustyn_aandroid_developer_halfwaythere;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlacesPOI extends AsyncTask<Object, String, String> {

    InputStream is;
    BufferedReader br;
    StringBuilder sb;
    GoogleMap mMap;
    String url;
    String data;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
//        Toast.makeText(this, "We out here", Toast.LENGTH_SHORT).show();
        try {
            URL mUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)mUrl.openConnection();
            httpURLConnection.connect();

            is = httpURLConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            String line = "";
            sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected  void onPostExecute(String s) {
        try {
            JSONObject parent = new JSONObject(s);
            System.out.println(parent);
            JSONArray arr = parent.getJSONArray("results");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                JSONObject location = obj.getJSONObject("geometry").getJSONObject("location");
                String lat = location.getString("lat");
                String lng = location.getString("lng");

                String name = obj.getString("name");

                LatLng pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                mMap.addMarker(new MarkerOptions().position(pos).title(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
