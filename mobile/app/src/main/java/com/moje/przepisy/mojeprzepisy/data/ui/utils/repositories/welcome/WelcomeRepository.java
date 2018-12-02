package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.welcome;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.User;
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
    Call<User> resp = userAPI.getUser();
    resp.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        User user = response.body();
        if(user.getStatus() == 401) {
          Log.i("checkUser.onResponse()", "User: " + user.getLogin());
            loggedListener.onNotLogged();
        } else {
            loggedListener.onLogged();
          }
        }
      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Log.i("checkUser.onFailure()", t.getMessage());
        loggedListener.showErrorMessage();
      }
    });
  }
}
