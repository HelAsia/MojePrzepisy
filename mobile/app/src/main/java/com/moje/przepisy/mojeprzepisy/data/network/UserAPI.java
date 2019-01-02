package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

  @POST("/login")
  Single<Message> login(@Body User user);

  @GET("/logout")
  Completable logout();

  @PUT("/user")
  Single<Message> register(@Body User user);

  @GET("/user")
  Single<User> getUser();

  @POST("/user/{columnName}/{columnValue}")
  Completable editUser(@Path("columnName") String columnName, @Path("columnValue") String columnValue);

  @POST("/user/photo")
  Completable editPhotoUser(@Body User user);

  @DELETE("/user")
  Completable deleteUser();



}
