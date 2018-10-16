package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.Photo;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.Stars;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.network.RecipeAPI;
import com.moje.przepisy.mojeprzepisy.data.network.RetrofitSingleton;
import java.util.ArrayList;
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
          Log.i("addRecipe.onResponse(): Recipe: ", "OK. Recipe has been added");
          listener.setRecipeId(message.message);
          listener.onRecipeAdded(true);
        }else if(message.status == 404){
          Log.e("addRecipe.onResponse(): Recipe: ", "NOT OK. Recipe hasn't been added");
          listener.onRecipeError();
          listener.onRecipeAdded(false);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addRecipe.onFailure(): SERWER", t.getMessage());
        listener.onRecipeError();
        listener.onRecipeAdded(false);
      }
    });
  }

  @Override
  public void addPhoto(List<Photo> photoList, final OnPhotoFinishedListener listener) {
    Call<Message> resp = recipeAPI.addPhoto(photoList.get(0));
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("addPhoto.onResponse(): Photo: ", "OK. Photo has been added");
          listener.setPhotoId(message.message);
          listener.onPhotoAdded(true);
        }else if(message.status == 404){
          Log.e("addPhoto.onResponse(): Photo: ", "NOT OK. Photo hasn't been added");
          listener.onPhotoError();
          listener.onPhotoAdded(false);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addPhoto.onFailure(): SERWER", t.getMessage());
        listener.onPhotoError();
        listener.onPhotoAdded(false);
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
          if(message != null) {
            if (message.status == 200) {
              listener.onIngredientsAdded(true);
            } else if (message.status == 404) {
              listener.onRecipeError();
              listener.onIngredientsAdded(false);
            }
          }
        }

        @Override
        public void onFailure(Call<Message> call, Throwable t) {
          Log.i("addIngredients.onFailure(): SERWER", t.getMessage());
          listener.onRecipeError();
          listener.onIngredientsAdded(false);
        }
      });
    }
  }

  @Override
  public void addStep(List<Step> stepList, final OnRecipeFinishedListener listener) {
    for(Step step : stepList){
      Log.d("addStep()", "About to PUT /recipe/step: " + step.toString());
      Call<Message> resp = recipeAPI.addStep(step);
      resp.enqueue(new Callback<Message>() {
        @Override
        public void onResponse(Call<Message> call, Response<Message> response) {
          Message message = response.body();
          Log.d("addStep.onResponse()", "Added step, got response");
          if(message.status == 200){
            Log.d("addStep.onResponse()", "Success;");
            listener.onStepsAdded(true);
          }else if(message.status == 404){
            Log.d("addStep.onResponse()", "Failure: 404;");
            listener.onRecipeError();
            listener.onStepsAdded(false);
          }
        }

        @Override
        public void onFailure(Call<Message> call, Throwable t) {
          Log.i("addStep.onFailure(): SERWER", t.getMessage());
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
          Log.i("addFirstStars.onResponse(): Stars: ", "OK. Stars has been added");
          listener.onStarsAdded(true);
        }else if(message.status == 404){
          Log.e("addFirstStars.onResponse(): Stars: ", "NOT OK. Stars hasn't been added");
          listener.onStarsError();
          listener.onStarsAdded(false);
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addFirstStars.onFailure(): SERWER", t.getMessage());
        listener.onStarsError();
        listener.onStarsAdded(false);
      }
    });
  }

  @Override
  public void editStarsAndHeart(final int recipeId, String columnName, int columnValue, final OnStarsEditListener listener, final int position) {
    Call<Message> resp = recipeAPI.editStarsAndHeart(recipeId, columnName, columnValue);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("editStarsAndHeart.onResponse(): Stars: ", "OK. Stars has been edited");
          listener.onUpdateStarsOrFavorite(recipeId, position);
        }else if(message.status == 404){
          Log.e("editStarsAndHeart.onResponse(): Stars: ", "NOT OK. Stars hasn't been added");
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("editStarsAndHeart.onFailure(): SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getRecipe(int recipeId, final OnRecipeDisplayListener listener) {
    Call<Recipe> resp = recipeAPI.getRecipe(recipeId);
    resp.enqueue(new Callback<Recipe>() {
      @Override
      public void onResponse(Call<Recipe> call, Response<Recipe> response) {
        Recipe recipe = response.body();
        if(recipe != null){
          Log.i("getRecipe.onResponse(): Recipe: ", "OK. Recipe has been downloaded");
          listener.setMainInfoRecipe(recipe);
        }else if(recipe == null){
          Log.i("getRecipe.onResponse(): Recipe: ", "OK. Recipe has been downloaded but is empty");
          listener.setMainInfoRecipe(recipe);
        }
        else{
          Log.e("getRecipe.onResponse(): Recipe: ", "NOT OK. Recipe hasn't been downloaded");
          listener.onRecipeError();
        }
      }
      @Override
      public void onFailure(Call<Recipe> call, Throwable t) {
        Log.i("getRecipe.onFailure(): SERWER RECIPE: ", t.getMessage());
        listener.onRecipeError();
      }
    });
  }

  @Override
  public void getPhoto(int photoId, final OnPhotoDisplayListener listener) {
    Call<Photo> resp = recipeAPI.getPhoto(photoId);
    resp.enqueue(new Callback<Photo>() {
      @Override
      public void onResponse(Call<Photo> call, Response<Photo> response) {
        Photo photo = response.body();
        if(photo != null){
          Log.i("getPhoto.onResponse(): Photo: ", "OK. Photo has been downloaded");
          listener.setPhoto(photo);
        }else if(photo == null){
          Log.i("getPhoto.onResponse(): Photo: ", "OK. Photo has been downloaded but is empty");
          listener.setPhoto(photo);
        }
        else{
          Log.e("getPhoto.onResponse(): Photo: ", "NOT OK. Photo hasn't been downloaded");
          listener.onPhotoError();
        }
      }
      @Override
      public void onFailure(Call<Photo> call, Throwable t) {
        Log.i("getPhoto.onFailure(): SERWER PHOTO: ", t.getMessage());
        listener.onPhotoError();
      }
    });
  }

  @Override
  public void getIngredients(int recipeId, final OnRecipeDisplayListener listener) {
    Call<List<Ingredient>> resp = recipeAPI.getIngredient(recipeId);
    resp.enqueue(new Callback<List<Ingredient>>() {
      @Override
      public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
        List<Ingredient> ingredientList = response.body();
        if(ingredientList != null){
          Log.i("getIngredients.onResponse(): Ingredients: ", "OK. Ingredient has been downloaded");
          listener.setIngredients(ingredientList);
        }else if(ingredientList == null){
          Log.i("getIngredients.onResponse(): Ingredients: ", "OK. Ingredient has been downloaded but id empty");
          listener.setIngredients(ingredientList);
        }else{
          Log.e("getIngredients.onResponse(): Ingredients: ", "NOT OK. Ingredient hasn't been downloaded");
          listener.onIngredientsError();
        }
      }
      @Override
      public void onFailure(Call<List<Ingredient>> call, Throwable t) {
        Log.i("getIngredients.onFailure(): SERWER INGREDIENTS:", t.getMessage());
        listener.onIngredientsError();
      }
    });
  }

  @Override
  public void getSteps(int recipeId, final OnRecipeDisplayListener listener) {
    Call<List<Step>> resp = recipeAPI.getStep(recipeId);
    resp.enqueue(new Callback<List<Step>>() {
      @Override
      public void onResponse(Call<List<Step>> call, Response<List<Step>> response) {
        List<Step> stepList = response.body();
        if(stepList != null){
          Log.i("getSteps.onResponse(): Steps: ", "OK. Steps has been downloaded");
          listener.setSteps(stepList);
        }else if(stepList == null){
        Log.i("getSteps.onResponse(): Ingredients: ", "OK. Ingredient has been downloaded but is empty");
        listener.setSteps(stepList);
        }else{
          Log.e("getSteps.onResponse(): Steps: ", "NOT OK. Steps hasn't been downloaded");
          listener.onStepsError();
        }
      }
      @Override
      public void onFailure(Call<List<Step>> call, Throwable t) {
        Log.i("getSteps.onFailure(): SERWER STEPS: ", t.getMessage());
        listener.onStepsError();
      }
    });
  }

  @Override
  public void getComments(int recipeId, final OnRecipeDisplayListener listener) {
    Call<List<Comment>> resp = recipeAPI.getComment(recipeId);
    resp.enqueue(new Callback<List<Comment>>() {
      @Override
      public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        List<Comment> commentList = response.body();
        if(commentList != null){
          Log.i("getComments.onResponse(): Comments: ", "OK. Comments has been downloaded");
          listener.setComment(commentList);
        }else{
          Log.e("getComments.onResponse(): Comments: ", "NOT OK. Comments hasn't been downloaded");
          listener.onCommentError();
        }
      }
      @Override
      public void onFailure(Call<List<Comment>> call, Throwable t) {
        Log.i("getComments.onFailure(): SERWER COMMENT: ", t.getMessage());
        Log.i("getComments.onFailure(): Comments: ", "OK. Comments has been downloaded but is empty");
        List<Comment> commentList = new ArrayList<Comment>();
        commentList.add(new Comment("Użytkownik", "Data utworzenia", "Brak komentarzy!"));
        listener.setComment(commentList);
      }
    });
  }

  @Override
  public void getRecipeDetailsStars(int recipeId, final OnRecipeDisplayListener listener) {
    Call<Stars> resp = recipeAPI.getRecipeDetailsStars(recipeId);
    resp.enqueue(new Callback<Stars>() {
      @Override
      public void onResponse(Call<Stars> call, Response<Stars> response) {
        Stars stars = response.body();
        if(stars != null){
          Log.i("getRecipeDetailsStars.onResponse(): Stars: ", "OK. Stars have been downloaded");
          listener.setStars(stars);
        }else if(stars == null){
          Log.i("getRecipeDetailsStars.onResponse(): Stars: ", "OK. Stars have been downloaded but is empty");
          listener.setStars(stars);
        }
        else{
          Log.e("getRecipeDetailsStars.onResponse(): Stars: ", "NOT OK. Stars haven't been downloaded");
          listener.onStarsError();
        }
      }
      @Override
      public void onFailure(Call<Stars> call, Throwable t) {
        Log.i("getRecipeDetailsStars.onFailure(): SERWER STARS: ", t.getMessage());
        listener.onStarsError();
      }
    });
  }


}
