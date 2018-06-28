package com.moje.przepisy.mojeprzepisy.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.io.IOException;
import java.util.HashSet;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor{
  private Context context;

  public ReceivedCookiesInterceptor(Context context) {
    this.context = context;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response orginalResponse = chain.proceed(chain.request());

    if(!orginalResponse.headers("Set-Cookie").isEmpty()) {
      HashSet<String> cookies = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context)
          .getStringSet(Constant.PREF_COOKIES,new HashSet<String>());


      for (String header : orginalResponse.headers("Set-Cookie")){
        cookies.add(header);
        Log.i("DEB","Received cookie: " + cookies);
      }

      SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
      memes.putStringSet(Constant.PREF_COOKIES,cookies).apply();
      memes.commit();
    }

    return orginalResponse;
  }
}
