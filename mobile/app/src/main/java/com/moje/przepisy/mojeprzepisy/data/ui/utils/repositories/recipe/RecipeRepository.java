package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
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

  public RecipeRepository(Context context) {
    this.retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.recipeAPI = retrofit.create(RecipeAPI.class);
  }

  @Override
  public void addRecipe(List<Recipe> recipeList, final OnRecipeFinishedListener listener) {
    Call<Message> resp = recipeAPI.addRecipe(recipeList.get(0));
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("Recipe: ", "OK. Recipe has been added");
          listener.setRecipeId(message.message);
          listener.onRecipeAdded(true);
        }else if(message.status == 404){
          Log.e("Recipe: ", "NOT OK. Recipe hasn't been added");
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

  @Override
  public void addFirstStars(Stars stars, final OnRecipeFinishedListener listener) {
    Call<Message> resp = recipeAPI.addStars(stars);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("Stars: ", "OK. Stars has been added");
          listener.onStarsAdded(true);
        }else if(message.status == 404){
          Log.e("Stars: ", "NOT OK. Stars hasn't been added");
          listener.onStarsError();
          listener.onStarsAdded(false);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
        listener.onStarsError();
        listener.onStarsAdded(false);
      }
    });
  }

  @Override
  public void editStars(int recipeId, String columnName, int columnValue, final OnStarsEditListener listener) {
    Call<Message> resp = recipeAPI.editStars(recipeId, columnName, columnValue);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("Stars: ", "OK. Stars has been added");
          listener.refreshCards();
        }else if(message.status == 404){
          Log.e("Stars: ", "NOT OK. Stars hasn't been added");
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("SERWER", t.getMessage());
      }
    });
  }
}
