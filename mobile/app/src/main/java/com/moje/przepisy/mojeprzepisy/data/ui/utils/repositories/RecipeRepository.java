package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import retrofit2.Retrofit;

public class RecipeRepository implements RecipeRepositoryInterface{

  private Retrofit retrofit;
  private UserAPI userAPI;

  public RecipeRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

}
