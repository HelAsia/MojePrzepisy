package com.moje.przepisy.mojeprzepisy.data.network;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
  public Context context;

  private static Retrofit retrofit = null;

  public static Retrofit getRetrofitInstance(Context context){
    if(retrofit == null){

      OkHttpClient client = new OkHttpClient();
      OkHttpClient.Builder builder = new OkHttpClient.Builder();

      builder.addInterceptor(new AddCookiesInterceptor(context));
      builder.addInterceptor(new ReceivedCookiesInterceptor(context));
      client = builder.build();

      retrofit = new retrofit2.Retrofit.Builder()
          .baseUrl(Constant.BASE_URL)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }

}
