package com.moje.przepisy.mojeprzepisy.welcome;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WelcomeRepository implements WelcomeRepositoryInterface {

  private Retrofit retrofit;
  private UserAPI userAPI;

  public WelcomeRepository(Context context){
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void checkUser(final OnLoggedListener loggedListener) {
    Call<Message> resp = userAPI.getUser();
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message msg = response.body();
        if(msg != null) {
          Log.i("checkUser.onResponse(): SERVER", "Message: " + msg.message);
          if (msg.status == 401) {
            loggedListener.onNotLogged();
          } else {
            loggedListener.onLogged();
          }
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("checkUser.onFailure(): SERWER", t.getMessage());
        loggedListener.showErrorMessage();
      }
    });
  }
}
