package com.moje.przepisy.mojeprzepisy.data.network;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.io.IOException;
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

    String cookiePreferences = (String) PreferenceManager.getDefaultSharedPreferences(context)
        .getString(Constant.PREF_COOKIES, new String());

      builder.addHeader("Cookie", cookiePreferences);
      Log.i("AddCookiesInterceptor::intercept(): DEB","Using cookie in request: " + cookiePreferences);

    return chain.proceed(builder.build());
  }
}
