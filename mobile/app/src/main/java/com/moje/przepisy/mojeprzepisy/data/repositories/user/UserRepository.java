package com.moje.przepisy.mojeprzepisy.data.repositories.user;

import android.content.Context;

import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserRepository implements UserRepositoryInterface{
  private UserAPI userAPI;

  public UserRepository(Context context) {
    Retrofit retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void login(String login, String password, final OnLoginFinishedListener listener) {
    User user = new User(login, password, "loginData");
    userAPI.login(user)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<Message>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Message message) {
          listener.onSuccess(Integer.parseInt(message.message));
        }

        @Override
        public void onError(Throwable e) {
          listener.onLoginAndPasswordError();
        }
      });
  }

  @Override
  public void logout(final OnLogoutFinishedListener listener) {
    userAPI.logout()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
          listener.onSuccess();
        }

        @Override
        public void onError(Throwable e) {
          listener.onLogoutError(e.getMessage());
        }
      });
  }

  @Override
  public void register(String firstName, String lastName, String login, String password,
                       String email, final OnRegisterFinishedListener listener) {
    if(listener.onValidatePasswordError(password)){
      if(listener.onValidateEmailError(email)){
        if(listener.onPasswordOrEmailError()){

          User user = new User(firstName, lastName, login, password, email);
          userAPI.register(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<Message>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onSuccess(Message message) {
                listener.onSuccess(Integer.parseInt(message.message));
              }

              @Override
              public void onError(Throwable e) {
                listener.onError(e.getMessage());
              }
            });
        }
      }
    }
  }

  @Override
  public void editUser(String columnName, String columnValue,
                       final OnEditUserFinishedListener listener) {
    userAPI.editUser(columnName, columnValue)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
          listener.onSuccess();
        }

        @Override
        public void onError(Throwable e) {
          listener.onEditAndSendDataError();
        }
      });
  }

  @Override
  public void editPhotoUser(String photoUser, final OnEditPhotoUserFinishedListener listener) {
    User user = new User(photoUser, "userPhoto");
    userAPI.editPhotoUser(user)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
          listener.onPhotoSuccess();
        }

        @Override
        public void onError(Throwable e) {
          listener.onEditAndSendPhotoError();
        }
      });
  }

  @Override
  public void getUser(final OnGetUserFinishedListener listener) {
    userAPI.getUser()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<User>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(User user) {
          listener.setUserValue(user);
        }

        @Override
        public void onError(Throwable e) {
          listener.onError();
        }
      });
  }
}

