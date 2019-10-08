package com.example.punto_verde.Interface;

import com.example.punto_verde.model.ImagePlace;
import com.example.punto_verde.model.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apiservice {
    @GET("places")
    Call<List<Place>> getPlaces();

    @POST("places")
    @FormUrlEncoded
    Call<Place> savePlaces(
            @Field("name") String name,
            @Field("description") String description,
            @Field("category") String category,
            @Field("schedule") String schedule,
            @Field("province") String province,
            @Field("contact") String contact,
            @Field("web") String web,
            @Field("calification") double calification,
            @Field("longitud") double longitud,
            @Field("latitud") double latitud);

    @GET("images")
    Call<List<ImagePlace>> getImages();

    @POST("images")
    @FormUrlEncoded
    Call<ImagePlace> saveImages(
            @Field("userID") String userID,
            @Field("url") String url);
}
