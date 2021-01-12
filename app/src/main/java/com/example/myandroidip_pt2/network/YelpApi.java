package com.example.myandroidip_pt2.network;

import com.example.myandroidip_pt2.models.YelpBusinessesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("businesses/search")
    Call<YelpBusinessesSearchResponse> getDryCleaning(
            @Query("location") String location,
            @Query("term") String term
    );
}
