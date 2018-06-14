package com.moje.przepisy.mojeprzepisy.login;

import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import com.moje.przepisy.mojeprzepisy.data.network.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository implements LoginRepositoryInterface {

  private Retrofit retrofit;
  private UserAPI userAPI;

  public LoginRepository(){
    this.retrofit = UserService.getRetrofitInstance();
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void login(final String login, final String password, final OnLoginFinishedListener listener) {

    User user = new User(login, password);
    Call<Message> resp = userAPI.login(user);

    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message msg = response.body();
        listener.onSuccess();
        Log.i("SERWER", "Serwer zwrocil kod: " + Integer.toString(msg.status));
        Log.i("SERWER", "Wiadomosc: " + msg.message);
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
        listener.onLoginAndPasswordError();
      }
    });
  }
}
