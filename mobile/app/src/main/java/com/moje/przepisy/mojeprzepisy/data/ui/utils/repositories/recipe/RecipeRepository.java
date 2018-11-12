package com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe;

import android.content.Context;
import android.util.Log;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Message;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.data.model.RecipeAllElements;
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
  public void addPhoto(String photo, final OnAddPhotoFinishedListener listener) {
    Log.i("RecipeRepository.addPhoto(): ", "Before preparing request");
    Call<Message> resp = recipeAPI.addPhoto(photo);
    Log.i("RecipeRepository.addPhoto(): ", "Before sending request");
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("addPhoto.onResponse(): ", "OK. addPhoto has been added");
          listener.onSetPhotoNumber(Integer.parseInt(message.message));
        }else if(message.status == 404){
          Log.e("addPhoto.onResponse(): ", "NOT OK. addPhoto hasn't been added");
          listener.onPhotoError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addWholeRecipeElements.onFailure(): SERWER", t.getMessage());
        listener.onPhotoError();
      }
    });
  }

  @Override
  public void addWholeRecipeElements(final RecipeAllElements recipeAllElements,
      final OnWholeRecipeElementsFinishedListener listener) {
    Log.i("RecipeRepository.addWholeRecipeElements(): ", "Before preparing request");
    Call<Message> resp = recipeAPI.addWholeRecipeElements(recipeAllElements);
    Log.i("RecipeRepository.addWholeRecipeElements(): ", "Before sending request");
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("addWholeRecipeElements.onResponse(): WholeRecipeElements: ", "OK. WholeRecipeElements has been added");
          listener.onWholeRecipeElementsAdded(true);
        }else if(message.status == 404){
          Log.e("addWholeRecipeElements.onResponse(): WholeRecipeElements: ", "NOT OK. WholeRecipeElements hasn't been added");
          listener.onRecipeError();
          listener.onWholeRecipeElementsAdded(false);
        }
      }

      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addWholeRecipeElements.onFailure(): SERWER", t.getMessage());
        listener.onRecipeError();
        listener.onWholeRecipeElementsAdded(false);
      }
    });
  }



  @Override
  public void addComment(Comment comment, final OnCommentsDetailsDisplayListener listener) {
    Call<Message> resp = recipeAPI.addComment(comment);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("addComment.onResponse(): Stars: ", "OK. Comment has been added");
          listener.setCommentId(message.message);
          listener.setCommentAddedState(true);
        }else if(message.status == 404){
          Log.e("addComment.onResponse(): Stars: ", "NOT OK. Comment hasn't been added");
          listener.onCommentError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("addFirstStars.onFailure(): SERWER", t.getMessage());
        listener.onCommentError();
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
  public void editStarsAndHeartInRecipe(final int recipeId, String columnName, int columnValue,
      final OnStarsEditInRecipeListener listener) {
    Call<Message> resp = recipeAPI.editStarsAndHeart(recipeId, columnName, columnValue);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("editStarsAndHeartInRecipe.onResponse(): Stars: ", "OK. Stars has been edited");
          listener.onUpdateStarsOrFavoriteInRecipe(recipeId);
        }else if(message.status == 404){
          Log.e("editStarsAndHeartInRecipe.onResponse(): Stars: ", "NOT OK. Stars hasn't been added");
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("editStarsAndHeart.onFailure(): SERWER", t.getMessage());
      }
    });
  }

  @Override
  public void getRecipe(int recipeId, final OnMainInfoDetailsDisplayListener listener) {
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
  public void getIngredients(int recipeId, final OnIngredientsDetailsDisplayListener listener) {
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
  public void getSteps(int recipeId, final OnStepsDetailsDisplayListener listener) {
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
  public void getComments(int recipeId, final OnCommentsDetailsDisplayListener listener) {
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
  public void getRecipeDetailsStars(int recipeId, final OnMainInfoDetailsDisplayListener listener) {
    Call<List<Stars>> resp = recipeAPI.getRecipeDetailsStars(recipeId);
    resp.enqueue(new Callback<List<Stars>>() {
      @Override
      public void onResponse(Call<List<Stars>> call, Response<List<Stars>> response) {
        List<Stars> starsList = response.body();
        if(starsList.get(0) != null){
          Log.i("getRecipeDetailsStars.onResponse(): Stars: ", "OK. Stars have been downloaded");
          listener.setStars(starsList.get(0));
        }else if(starsList.get(0) == null){
          Log.i("getRecipeDetailsStars.onResponse(): Stars: ", "OK. Stars have been downloaded but is empty");
          listener.setStars(starsList.get(0));
        }
        else{
          Log.e("getRecipeDetailsStars.onResponse(): Stars: ", "NOT OK. Stars haven't been downloaded");
          listener.onStarsError();
        }
      }
      @Override
      public void onFailure(Call<List<Stars>> call, Throwable t) {
        Log.i("getRecipeDetailsStars.onFailure(): SERWER STARS: ", t.getMessage());
        listener.onStarsError();
      }
    });
  }

  @Override
  public void deleteComment(int commentId, final OnDeleteCommentsDetailsDisplayListener listener) {
    Call<Message> resp = recipeAPI.deleteComment(commentId);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("deleteComment.onResponse(): Comment ", "OK. Comment has been deleted");
          listener.onSuccess();
        }else if(message.status == 404){
          Log.e("deleteComment.onResponse(): Comment ", "NOT OK. Comment hasn't been deleted");
          listener.onError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("deleteComment.onFailure(): SERWER", t.getMessage());
        listener.onError();
      }
    });
  }

  @Override
  public void deleteRecipe(int recipeId, final OnDeleteRecipeDetailsDisplayListener listener) {
    Call<Message> resp = recipeAPI.deleteRecipe(recipeId);
    resp.enqueue(new Callback<Message>() {
      @Override
      public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if(message.status == 200){
          Log.i("deleteRecipe.onResponse(): Recipe ", "OK. Recipe has been deleted");
          listener.onSuccess();
        }else if(message.status == 404){
          Log.e("deleteRecipe.onResponse(): Recipe ", "NOT OK. Recipe hasn't been deleted");
          listener.onError();
        }
      }
      @Override
      public void onFailure(Call<Message> call, Throwable t) {
        Log.i("deleteRecipe.onFailure(): SERWER", t.getMessage());
        listener.onError();
      }
    });
  }
}
