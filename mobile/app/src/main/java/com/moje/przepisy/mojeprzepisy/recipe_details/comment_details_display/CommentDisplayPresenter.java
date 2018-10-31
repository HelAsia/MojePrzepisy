package com.moje.przepisy.mojeprzepisy.recipe_details.comment_details_display;

import android.widget.Toast;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import java.util.List;

public class CommentDisplayPresenter implements CommentDisplayContract.Presenter,
    RecipeRepository.OnCommentsDetailsDisplayListener {
  private RecipeRepository recipeRepository;
  private CommentDisplayContract.View commentsDisplayView;
  int commentId;

  public CommentDisplayPresenter(CommentDisplayContract.View commentsDisplayView,
      RecipeRepository recipeRepository){
    this.commentsDisplayView = commentsDisplayView;
    this.recipeRepository = recipeRepository;
  }

  @Override
  public void setComment(List<Comment> commentList) {
    if(commentsDisplayView != null){
      commentsDisplayView.setCommentsRecyclerView(commentList);
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
  public void setWholeRecipeElements() {
    recipeRepository.getComments(commentsDisplayView.getRecipeId(), this);
  }

  @Override
  public void sendCommentToServer() {
    if(!commentsDisplayView.getCommentEditText().getText().toString().equals("")){
      recipeRepository.addComment(getCommentObjectToAdd(), this);
      recipeRepository.getComments(commentsDisplayView.getRecipeId(),this);
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
}
