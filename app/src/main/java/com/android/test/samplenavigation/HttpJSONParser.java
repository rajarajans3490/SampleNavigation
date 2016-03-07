package com.android.test.samplenavigation;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpJSONParser {
    String response="";
    URL url;
    JSONArray mJSONArray = null;
    HttpURLConnection mHttpURLConnection = null;


    public JSONArray getJSONData(String path) {
        try {
            url = new URL(path);

            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setReadTimeout(15000);
            mHttpURLConnection.setConnectTimeout(15000);
            mHttpURLConnection.setRequestMethod("GET");

            int responseCode = mHttpURLConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    result.append(line + '\n');
                }
                response = result.toString();
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mHttpURLConnection.disconnect();
        }

        try{
            mJSONArray = new JSONArray(response);
        }catch(JSONException ex){
            ex.printStackTrace();
        }
        return mJSONArray;
    }


}
