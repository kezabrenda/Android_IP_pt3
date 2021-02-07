package com.example.myandroidip_pt2.service;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YelpService {
    public static void findDryCleaning(String location, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.PREFERENCES_LOCATION_KEY, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.YELP_API_KEY)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Cleaning> processResults(Response response){
        ArrayList<Cleaning> cleaning = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
            if (response.isSuccessful()){
                for (int i = 0; i < businessesJSON.length(); i++){
                    JSONObject dryCleaningJSON = businessesJSON.getJSONObject(i);
                    String name = dryCleaningJSON.getString("name");
                    String phone = dryCleaningJSON.optString("display_phone", "Phone not available");
                    String website = dryCleaningJSON.getString("url");
                    double rating = dryCleaningJSON.getDouble("rating");
                    String imageUrl = dryCleaningJSON.getString("image_url");
                    double latitude = dryCleaningJSON.getJSONObject("coordinates").getDouble("latitude");
                    double longitude = dryCleaningJSON.getJSONObject("coordinates").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = dryCleaningJSON.getJSONObject("location").getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++){
                        address.add(addressJSON.get(y).toString());
                    }
                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = dryCleaningJSON.getJSONArray("categories");
                    for (int y = 0; y < categoriesJSON.length(); y++){
                        categories.add(categoriesJSON.getJSONObject(y).getString("title"));
                    }
                    Cleaning dryCleaning = new Cleaning(name, phone, website, rating,
                            imageUrl, address, latitude, longitude, categories);
                    cleaning.add(dryCleaning);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cleaning;
    }
}
