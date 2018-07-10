package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OperationsOnCardRepository implements OperationsOnCardRepositoryInterface {

  private Retrofit retrofit;
  private UserAPI userAPI;

  public OperationsOnCardRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.userAPI = retrofit.create(UserAPI.class);
  }

  @Override
  public void getCards() {

    Call<OneRecipeCard> resp = userAPI.getCards();

    resp.enqueue(new Callback<OneRecipeCard>() {
      @Override
      public void onResponse(Call<OneRecipeCard> call, Response<OneRecipeCard> response) {
        OneRecipeCard oneRecipeCard = response.body();

      }

      @Override
      public void onFailure(Call<OneRecipeCard> call, Throwable t) {

      }
    });
  }
}
