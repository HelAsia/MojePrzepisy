package com.moje.przepisy.mojeprzepisy.data.repositories.recipe;

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

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeRepository implements RecipeRepositoryInterface{
  private RecipeAPI recipeAPI;

  public RecipeRepository(Context context) {
    Retrofit retrofit = RetrofitSingleton.getRetrofitInstance(context);
    this.recipeAPI = retrofit.create(RecipeAPI.class);
  }

  @Override
  public void addPhoto(String photo, final OnAddPhotoFinishedListener listener) {
    recipeAPI.addPhoto(photo)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<Message>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Message message) {
          listener.onSetPhotoNumber(Integer.parseInt(message.message));
        }

        @Override
        public void onError(Throwable e) {
          listener.onPhotoError();
        }
      });
  }

  @Override
  public void addWholeRecipeElements(final RecipeAllElements recipeAllElements,
                                     final OnWholeRecipeElementsFinishedListener listener) {
    recipeAPI.addWholeRecipeElements(recipeAllElements)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
          listener.onWholeRecipeElementsAdded(true);
        }

        @Override
        public void onError(Throwable e) {
          listener.onRecipeError();
          listener.onWholeRecipeElementsAdded(false);
        }
      });
  }


  @Override
  public void addComment(Comment comment, final OnCommentsDetailsDisplayListener listener) {
    recipeAPI.addComment(comment)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
          listener.setCommentAddedState(true);
        }

        @Override
        public void onError(Throwable e) {
          listener.onCommentError();
        }
      });
}

  @Override
  public void editStarsAndHeart(final int recipeId, String columnName, int columnValue,
                                final OnStarsEditListener listener, final int position) {
    recipeAPI.editStarsAndHeart(recipeId, columnName, columnValue)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onComplete() {
              listener.onUpdateStarsOrFavorite(recipeId, position);
          }

          @Override
          public void onError(Throwable e) {
              Log.i("editStarsAndHeart.onFailure()", e.getMessage());
          }
      });
  }

  @Override
  public void editStarsAndHeartInRecipe(final int recipeId, String columnName, int columnValue,
                                        final OnStarsEditInRecipeListener listener) {
    recipeAPI.editStarsAndHeart(recipeId, columnName, columnValue)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new CompletableObserver() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onComplete() {
                         listener.onUpdateStarsOrFavoriteInRecipe(recipeId);
                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.i("editStarsAndHeart.onFailure()", e.getMessage());
                     }
                 });
  }

  @Override
  public void getRecipe(int recipeId, final OnMainInfoDetailsDisplayListener listener) {
    recipeAPI.getRecipe(recipeId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<Recipe>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Recipe recipe) {
          listener.setMainInfoRecipe(recipe);
        }

        @Override
        public void onError(Throwable e) {
          listener.onRecipeError();
        }
      });
  }

  @Override
  public void getIngredients(int recipeId, final OnIngredientsDetailsDisplayListener listener) {
    recipeAPI.getIngredient(recipeId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<List<Ingredient>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<Ingredient> ingredientList) {
          listener.setIngredients(ingredientList);
        }

        @Override
        public void onError(Throwable e) {
          listener.onIngredientsError();
        }
      });
  }

  @Override
  public void getSteps(int recipeId, final OnStepsDetailsDisplayListener listener) {
    recipeAPI.getStep(recipeId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<List<Step>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<Step> stepList) {
          listener.setSteps(stepList);
        }

        @Override
        public void onError(Throwable e) {
          listener.onStepsError();
        }
      });
  }

  @Override
  public void getComments(int recipeId, final OnCommentsDetailsDisplayListener listener) {
    recipeAPI.getComment(recipeId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<List<Comment>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<Comment> commentList) {
          listener.setCommentAddedState(true);
          listener.setComment(commentList);
        }

        @Override
        public void onError(Throwable e) {
          listener.setCommentAddedState(false);
          listener.onCommentError();
        }
      });
  }

  @Override
  public void getRecipeDetailsStars(int recipeId, final OnMainInfoDetailsDisplayListener listener) {
    recipeAPI.getRecipeDetailsStars(recipeId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new SingleObserver<List<Stars>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onSuccess(List<Stars> starsList) {
              listener.setStars(starsList.get(0));
          }

          @Override
          public void onError(Throwable e) {
              listener.onStarsError();
          }
      });
  }

  @Override
  public void deleteComment(int commentId, final OnDeleteCommentsDetailsDisplayListener listener) {
    recipeAPI.deleteComment(commentId)
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
          listener.onError();
        }
      });
  }

  @Override
  public void deleteRecipe(int recipeId, final OnDeleteRecipeDetailsDisplayListener listener) {
    recipeAPI.deleteRecipe(recipeId)
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
          listener.onError();
        }
      });
  }
}
