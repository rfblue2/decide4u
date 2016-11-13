package com.hackprinceton.decide4u;

import com.hackprinceton.decide4u.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface to api to get questions
 */

public interface ApiEndpointInterface {

    @Headers({"Content-Type: application/json"})
    @POST("questions/add")
    Call<Question> add(@Body Question question);

    @Headers({"Content-Type: application/json"})
    @GET("users/{name}/list")
    Call<List<Question>> list(@Path("name") String name);

    @Headers({"Content-Type: application/json"})
    @PUT("questions/{question}")
    Call<Question> update(@Path("question") long id, @Body Question question);

    @Headers({"Content-Type: application/json"})
    @GET("questions")
    Call<List<Question>> listRecent();
}
