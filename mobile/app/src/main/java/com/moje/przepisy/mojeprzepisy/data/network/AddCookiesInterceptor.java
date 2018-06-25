package com.moje.przepisy.mojeprzepisy.data.network;

import android.content.Context;
import android.preference.PreferenceManager;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.io.IOException;
import java.util.HashSet;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
  private Context context;

  public AddCookiesInterceptor(Context context) {
    this.context = context;
  }

  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request.Builder builder = chain.request().newBuilder();

    HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context)
        .getStringSet(Constant.PREF_COOKIES, new HashSet<String>());

    for(String cookie : preferences) {
      builder.addHeader("Cookie", cookie);
    }

    return chain.proceed(builder.build());
  }
}
