package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

  @POST("/login")
  void postLogin(@Body User user, Callback<User> pResponse)
  Call<Message> login(@Body User user);

  @POST("/register")
  Call<Message> register(@Body User user);


}
