package com.moje.przepisy.mojeprzepisy.data.network;

import com.moje.przepisy.mojeprzepisy.utils.Constans;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

  private static Retrofit retrofit;

  public static Retrofit getRetrofitInstance(){
    if(retrofit == null){
      retrofit = new retrofit2.Retrofit.Builder()
          .baseUrl(Constans.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }
}
