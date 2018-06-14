package com.moje.przepisy.mojeprzepisy.login;

import android.text.TextUtils;
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
  private UserAPI userService;

  public LoginRepository(){
    this.retrofit = UserService.getRetrofitInstance();
    this.userService = retrofit.create(UserAPI.class);
  }

  @Override
  public void login(final String login, final String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
          listener.onLoginError();
        }

        if (!TextUtils.isEmpty(login) && TextUtils.isEmpty(password)) {
          listener.onPasswordError();
        }

        if(TextUtils.isEmpty(login) && TextUtils.isEmpty(password)) {
          listener.onLoginAndPasswordError();
        }

        else {
          if(checkIfLoginExist(login)){
            if(checkIfPasswordIsCorrect(login, password)){

              loginWorker(login,password);

              listener.onSuccess();
            }
            listener.onPasswordError();
          }
          listener.onLoginAndPasswordError();
        }
  }

  public void loginWorker(String login, String password){

    User user = new User(login, password);

    Call<Message> resp = userService.login(user);

    resp.enqueue(new Callback<Message>() {

      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {

        Message msg = response.body();

      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {

      }
    });

  }
  @Override
  public boolean checkIfLoginExist(String login) {
    return true;
  }

  @Override
  public boolean checkIfPasswordIsCorrect(String login, String password) {
    return true;
  }
}
