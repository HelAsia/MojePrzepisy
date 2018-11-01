package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

  @POST("/login")
  Call<Message> login(@Body User user);

  @GET("/logout")
  Call<Message> logout();

  @PUT("/user")
  Call<Message> register(@Body User user);

  @GET("/user")
  Call<User> getUser();

  @POST("/user/{columnName}/{columnValue}")
  Call<Message> editUser(@Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @POST("/user/photo")
  Call<Message> editPhotoUser(@Body User user);

  @DELETE("/user")
  Call<Message> deleteUser();



}
