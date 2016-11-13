package com.hackprinceton.decide4u;

import com.hackprinceton.decide4u.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Interface to api to get questions
 */

public interface ApiEndpointInterface {

    @Headers({"Content-Type: application/json"})
    @POST("add")
    Call<Void> add(@Body Question question);

    @Headers({"Content-Type: application/json"})
    @GET("list")
    Call<List<Question>> list();

}
