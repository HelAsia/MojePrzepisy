package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import java.util.List;

public class CommentDisplayPresenter implements CommentDisplayContract.Presenter,
    RecipeRepository.OnCommentsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private CommentDisplayContract.View commentsDisplayView;
  int commentId;
  private Boolean commentAddedState = false;

  public CommentDisplayPresenter(CommentDisplayContract.View commentsDisplayView,
      RecipeRepository recipeRepository){
    this.commentsDisplayView = commentsDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setComment(List<Comment> commentList) {
    if(commentsDisplayView != null){
      commentsDisplayView.setCommentsRecyclerViewAndCommentNumber(commentList);
    }
  }

  @Override
  public void onCommentError() {
    Toast.makeText(commentsDisplayView.getContext(), "Błąd podczas pobierania komentarzy!",
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void setCommentId(String message) {
    this.commentId = Integer.parseInt(message);
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
    if(!commentsDisplayView.getCommentEditText().getText().toString().equals("")){
      new BackgroundAddCommentAction((Activity)commentsDisplayView.getContext(), this).execute();
    }
  }

  @Override
  public String getCommentText() {
    return commentsDisplayView.getCommentEditText().getText().toString();
  }

  @Override
  public Comment getCommentObjectToAdd() {
    return new Comment(commentsDisplayView.getRecipeId(), getCommentText());
  }

  @Override
  public void setEditTextAfterAdded() {
    commentsDisplayView.getCommentEditText().setText("");
    commentsDisplayView.getCommentEditText().setHint("Dodaj komentarz...");
  }

  @Override
  public void setAddCommentButtonAndEditCommentVisibility() {
    commentsDisplayView.getAddCommentButton().setVisibility(View.GONE);
    commentsDisplayView.getCommentEditText().setVisibility(View.GONE);
  }

  @Override
  public void setCommentNumberOnScreen(int commentNumber) {
    commentsDisplayView.getCommentNumberTextView().setText((commentNumber + " komentarz(y)"));
  }

  private class BackgroundAddCommentAction extends AsyncTask<Void, Void, Void> {
    RecipeRepository.OnCommentsDetailsDisplayListener listener;
    private Activity activity;

    public BackgroundAddCommentAction(Activity activity, RecipeRepository.OnCommentsDetailsDisplayListener listener) {
      this.activity = activity;
      this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
      recipeRepository.addComment(getCommentObjectToAdd(), listener);
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
      Toast.makeText(activity, "Komentarz dodany", Toast.LENGTH_SHORT).show();
    }
  }
}
