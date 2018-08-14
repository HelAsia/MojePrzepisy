package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.network.RecipeAPI;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import retrofit2.Retrofit;

public class RecipeRepository implements RecipeRepositoryInterface{

  private Retrofit retrofit;
  private RecipeAPI recipeAPI;

  public RecipeRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.recipeAPI = retrofit.create(RecipeAPI.class);
  }

}
