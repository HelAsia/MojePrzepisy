package com.moje.przepisy.mojeprzepisy.register;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import com.moje.przepisy.mojeprzepisy.login.LoginRepositoryInterface.OnLoginFinishedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterRepository implements RegisterRepositoryInterface {

  private Retrofit retrofit;
  private UserAPI userAPI;

  public RegisterRepository(Context context){
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void register(final String name, final String lastName, final String login, final String password, final String email,
      final OnLoginFinishedListener listener) {

    User user = new User(name, lastName, login, password, email);
    Call<Message> resp = userAPI.register(user);

    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message msg = response.body();

        Log.i("SERVER", "Server return code: " + Integer.toString(msg.status));
        Log.i("SERVER", "Message: " + msg.message);

        if (msg.status == 200){
          listener.onSuccess();
        }else if (msg.status == 404){
          listener.onLoginAndPasswordError();
        }

      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
        listener.onLoginAndPasswordError();
      }
    });
  }
}

