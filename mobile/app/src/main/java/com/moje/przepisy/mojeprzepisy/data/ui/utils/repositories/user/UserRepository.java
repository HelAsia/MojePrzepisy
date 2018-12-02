package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository implements UserRepositoryInterface{

  private Retrofit retrofit;
  private UserAPI userAPI;

  public UserRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void login(final String login, final String password, final OnLoginFinishedListener listener) {

    User user = new User(login, password, "loginData");
    Call<Message> resp = userAPI.login(user);

    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message msg = response.body();
        Log.i("login.onResponse()", "Server return code: " + Integer.toString(msg.status));
        Log.i("login.onResponse()", "Message: " + msg.message);

        if (msg.status == 200){
          listener.onSuccess(Integer.parseInt(msg.message));
        }else if (msg.status == 404){
          listener.onLoginAndPasswordError();
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("login.onFailure()", t.getMessage());
        listener.onLoginAndPasswordError();
      }
    });
  }

  @Override
  public void logout(final OnLogoutFinishedListener listener) {
    Call<Message> resp = userAPI.logout();
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message msg = response.body();
        Log.i("logout.onResponse()", "Server return code: " + Integer.toString(msg.status));
        Log.i("logout.onResponse()", "Message: " + msg.message);

        if (msg.status == 200){
          listener.onSuccess();
        }else {
          listener.onLogoutError(msg.message);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("logout.onFailure()", t.getMessage());
        listener.onLogoutError(t.getMessage());
      }
    });
  }

  @Override
  public void register(final String firstName, final String lastName, final String login, final String password, final String email,
      final                                                                                                                                                                                                                                  OnRegisterFinishedListener listener) {

    if(listener.onValidatePasswordError(password)){
      if(listener.onValidateEmailError(email)){
        if(listener.onPasswordOrEmailError()){

          User user = new User(firstName, lastName, login, password, email);
          Call<Message> resp = userAPI.register(user);

          resp.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
              Message msg = response.body();

              Log.i("register.onResponse()", "Server return code: " + Integer.toString(msg.status));
              Log.i("register.onResponse()", "Message: " + msg.message);

              if (msg.status == 200){
                listener.onSuccess(Integer.parseInt(msg.message));
              }else if (msg.status == 404){
                listener.onLoginError();
              }else if (msg.status == 500){
                listener.onOtherError(msg.message);
              }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
              Log.i("register.onFailure()", t.getMessage());
              listener.onLoginError();
            }
          });
        }
      }
    }
  }

  @Override
  public void editUser(String columnName, String columnValue, final OnEditUserFinishedListener listener) {
    Call<Message> resp = userAPI.editUser(columnName, columnValue);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("editUser.onResponse()", "OK. User data has been edited");
          listener.onSuccess();
        }else if(message.status == 404){
          Log.e("editUser.onResponse()", "NOT OK. User data hasn't been added");
          listener.onEditAndSendDataError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("editUser.onFailure()", t.getMessage());
        listener.onEditAndSendDataError();
      }
    });
  }

  @Override
  public void editPhotoUser(String photoUser, final OnEditPhotoUserFinishedListener listener) {
    User user = new User(photoUser, "userPhoto");
    Call<Message> resp = userAPI.editPhotoUser(user);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("editPhotoUser.onResponse()", "OK. User data has been edited");
          listener.onPhotoSuccess();
        }else if(message.status == 404){
          Log.e("editPhotoUser.onResponse()", "NOT OK. User data hasn't been added");
          listener.onEditAndSendPhotoError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("editPhotoUser.onFailure()", t.getMessage());
        listener.onEditAndSendPhotoError();
      }
    });
  }

  @Override
  public void getUser(final OnGetUserFinishedListener listener) {
    Call<User> resp = userAPI.getUser();
    resp.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        User user = response.body();
        if(user.getStatus() == 401) {
          Log.i("checkUser.onResponse()", "User: " + user.getLogin());
          listener.onGetUserError();
        } else {
          listener.setUserValue(user);
        }
      }
      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Log.i("checkUser.onFailure()", t.getMessage());
        listener.onGetUserError();
      }
    });
  }
}

