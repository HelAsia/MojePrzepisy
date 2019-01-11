package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails.commentDisplay;

import android.os.AsyncTask;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class CommentDisplayPresenter implements CommentDisplayContract.Presenter,
    RecipeRepository.OnCommentsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private CommentDisplayContract.View commentsDisplayView;
  private Boolean commentAddedState = false;

  CommentDisplayPresenter(CommentDisplayContract.View commentsDisplayView,
      RecipeRepository recipeRepository){
    this.commentsDisplayView = commentsDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setFirstScreen() {
    commentsDisplayView.setRecipeId();
    commentsDisplayView.setIsLogged();
    setCommentListeners();
    setWholeCommentsElements();
  }

  @Override
  public void setComment(List<Comment> commentList) {
    if(commentsDisplayView != null) {
      if (commentList != null) {
        commentsDisplayView.setCommentsRecyclerViewAndCommentNumber(commentList);
      } else {
        List<Comment> emptyCommentList = new ArrayList<Comment>();
        emptyCommentList.add(new Comment("Użytkownik", "Data utworzenia", "Brak komentarzy!"));
        commentsDisplayView.setCommentsRecyclerViewAndCommentNumber(emptyCommentList);
      }
    }
  }

  @Override
  public void setCommentListeners() {
    if(commentsDisplayView.getIsLogged()){
      commentsDisplayView.setAddCommentButtonListener();
    }else {
      commentsDisplayView.setAddCommentButtonAndEditCommentVisibility();
    }
  }

  @Override
  public void onCommentError() {
    commentsDisplayView.setToastMessage("Błąd podczas pobierania komentarzy!");
  }

  @Override
  public void setCommentAddedState(Boolean state) {
    this.commentAddedState = state;
  }

  @Override
  public void setWholeCommentsElements() {
    recipeRepository.getComments(commentsDisplayView.getRecipeId(), this);
  }

  @Override
  public void sendCommentToServer() {
    if(!commentsDisplayView.getCommentText().equals("")){
      new BackgroundAddCommentAction(this).execute();
    }
  }

  private class BackgroundAddCommentAction extends AsyncTask<Void, Void, Void> {
    RecipeRepository.OnCommentsDetailsDisplayListener listener;

    BackgroundAddCommentAction(RecipeRepository.OnCommentsDetailsDisplayListener listener) {
      this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
      recipeRepository.addComment(commentsDisplayView.getCommentObjectToAdd(), listener);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        while (!commentAddedState){

        }

        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }
    @Override
    protected void onPostExecute(Void result) {
      setWholeCommentsElements();
      commentAddedState = false;
      commentsDisplayView.setToastMessage("Komentarz dodany");
    }
  }
}
