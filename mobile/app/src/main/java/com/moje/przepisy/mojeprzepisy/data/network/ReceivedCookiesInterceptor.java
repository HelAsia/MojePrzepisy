package com.moje.przepisy.mojeprzepisy.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.io.IOException;
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
      String cookies = (String) PreferenceManager.getDefaultSharedPreferences(context)
          .getString(Constant.PREF_COOKIES,new String());

      for (String header : orginalResponse.headers("Set-Cookie")){
        cookies = header;
        Log.i("DEB","Received cookie: " + cookies);
      }

      SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
      memes.putString(Constant.PREF_COOKIES,cookies).apply();
      memes.commit();
    }

    return orginalResponse;
  }
}
