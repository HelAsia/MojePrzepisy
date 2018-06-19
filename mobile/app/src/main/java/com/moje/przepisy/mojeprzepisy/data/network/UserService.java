package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.utils.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

  private static Retrofit retrofit;

  public static Retrofit getRetrofitInstance(){
    if(retrofit == null){

      retrofit = new retrofit2.Retrofit.Builder()
          .baseUrl(Constant.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }
}
