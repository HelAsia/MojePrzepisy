package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.network.RecipeAPI;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeRepository implements RecipeRepositoryInterface{
  private Retrofit retrofit;
  private RecipeAPI recipeAPI;
  private int recipeId;

  public RecipeRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.recipeAPI = retrofit.create(RecipeAPI.class);
  }

  @Override
  public int getRecipeId(Recipe recipe) {
    Call<Recipe> resp = recipeAPI.getRecipeId(recipe);
    resp.enqueue(new Callback<Recipe>() {
      @Override
      public void onResponse(Call<Recipe> call, Response<Recipe> response) {
        Recipe recipe = response.body();
        recipeId = recipe.getRecipeId();
      }

      @Override
      public void onFailure(Call<Recipe> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
        recipeId = -1;
      }
    });
    return recipeId;
  }

  @Override
  public void addRecipe(List<Recipe> recipeList, final OnRecipeFinishedListener listener) {
    Call<Message> resp = recipeAPI.addRecipe(recipeList.get(0));
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          listener.onRecipeSuccess();
          listener.onRecipeAdded(true);
        }else if(message.status == 404){
          listener.onRecipeError();
          listener.onRecipeAdded(false);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
        listener.onRecipeError();
        listener.onRecipeAdded(false);
      }
    });

  }

  @Override
  public void addIngredients(List<Ingredient> ingredientList, final OnRecipeFinishedListener listener) {
    for(Ingredient ingredient : ingredientList){
      Call<Message> resp = recipeAPI.addIngredient(ingredient);
      resp.enqueue(new Callback<Message>() {
        @Override
        public void onResponse(Call<Message> call, Response<Message> response) {
          Message message = response.body();
          if(message.status == 200){
            listener.onRecipeSuccess();
            listener.onIngredientsAdded(true);
          }else if(message.status == 404){
            listener.onRecipeError();
            listener.onIngredientsAdded(false);
          }
        }

        @Override
        public void onFailure(Call<Message> call, Throwable t) {
          Log.i("SERWER", t.getMessage());
          listener.onRecipeError();
          listener.onIngredientsAdded(false);
        }
      });
    }
  }

  @Override
  public void addStep(List<Step> stepList, final OnRecipeFinishedListener listener) {
    for(Step step : stepList){
      Call<Message> resp = recipeAPI.addStep(step);
      resp.enqueue(new Callback<Message>() {
        @Override
        public void onResponse(Call<Message> call, Response<Message> response) {
          Message message = response.body();
          if(message.status == 200){
            listener.onRecipeSuccess();
            listener.onStepsAdded(true);
          }else if(message.status == 404){
            listener.onRecipeError();
            listener.onStepsAdded(false);
          }
        }

        @Override
        public void onFailure(Call<Message> call, Throwable t) {
          Log.i("SERWER", t.getMessage());
          listener.onRecipeError();
          listener.onStepsAdded(false);
        }
      });
    }
  }
}
