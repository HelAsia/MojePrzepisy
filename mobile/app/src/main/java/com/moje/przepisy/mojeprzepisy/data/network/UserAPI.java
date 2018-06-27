package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserAPI {

  @POST("/user/login")
  Call<Message> login(@Body User user);

  @PUT("/user/logout")
  Call<Message> logout(@Body User user);

  @GET("/user/profile")
  Call<Message> getUser(@Body User user);




}
