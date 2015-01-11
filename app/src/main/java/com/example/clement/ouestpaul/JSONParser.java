package com.example.clement.ouestpaul;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

/**
 * Created by Adrien on 20/11/2014.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    // constructor
            public JSONParser() {
        }
    public JSONObject getJSONFromUrl(String url) {
        // Making HTTP request

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8); //"iso-8859-1"
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                }
            is.close();
            json = sb.toString();
            } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
           }
        // return JSON String
        return jObj;
        }

    public static String makeRequest(String path) throws Exception
    {
        //instantiates httpclient to make request
        DefaultHttpClient httpclient = new DefaultHttpClient();

        //url with the post data

        URI uri = new URI("http", "", "192.168.1.91", 8080, "/apiDev/tracking/addTrack", "", "");
        HttpPost httpost = new HttpPost(uri);

        //convert parameters into JSON object
        JSONObject tracks = new JSONObject();
        JSONObject holder = new JSONObject();
        JSONObject simple = new JSONObject();
        JSONObject holderTrack = new JSONObject();

        JSONArray tab = new JSONArray();
        holder.put("user", "yolo");
        holder.put("date", "A1231");
        simple.put("x", 12345);
        simple.put("y", 54321);
        tab.put(simple);
        tab.put(simple);
        holderTrack.put("date", "1111");
        holderTrack.put("tracks", tab);
        holder.put("track", holderTrack);
        Log.e("HEY", holder.toString());
        tracks.put("tracking", holder);

        //passes the results to a string builder/entity
        StringEntity se = new StringEntity(tracks.toString());

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        //Handles what is returned from the page
        ResponseHandler responseHandler = new BasicResponseHandler();
        httpclient.execute(httpost, responseHandler);
        return "ok";
    }
}
