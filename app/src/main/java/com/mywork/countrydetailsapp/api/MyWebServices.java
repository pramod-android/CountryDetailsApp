package com.mywork.countrydetailsapp.api;

import com.mywork.countrydetailsapp.entity.AboutCanada;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyWebServices {
    @GET("/s/2iodh4vg0eortkl/facts")
    Call<AboutCanada> getAllData();
}
