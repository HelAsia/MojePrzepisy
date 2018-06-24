package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

  @POST("/user/login")
  Call<Message> login(@Body User user);

  @POST("/user/logout")
  Call<Message> logout(@Body User user);


}
