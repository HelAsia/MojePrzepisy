package com.moje.przepisy.mojeprzepisy.data.repositories.welcome;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.User;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WelcomeRepository implements WelcomeRepositoryInterface {
  private UserAPI userAPI;

  public WelcomeRepository(Context context){
      Retrofit retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void checkUser(final OnLoggedListener loggedListener) {
    userAPI.getUser()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<User>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(User user) {
          if(user.getStatus() == 401) {
            Log.i("checkUser.onResponse()", "User: " + user.getLogin());
            loggedListener.onNotLogged();
          } else {
            loggedListener.onLogged();
          }
        }

        @Override
        public void onError(Throwable e) {
          loggedListener.onError();
        }
      });
  }
}
