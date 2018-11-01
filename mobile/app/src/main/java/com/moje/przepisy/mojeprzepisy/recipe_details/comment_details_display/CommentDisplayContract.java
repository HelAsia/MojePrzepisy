package com.moje.przepisy.mojeprzepisy.recipe_details.comment_details_display;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import java.util.List;

public interface CommentDisplayContract {
  interface View{
    Context getContext();
    void setCommentListeners();
    int getRecipeId();
    Boolean getIsLogged();
    void setCommentsRecyclerView(List<Comment> commentList);
    EditText getCommentEditText();
    Button getAddCommentButton();
  }
  interface Presenter{
    void setWholeRecipeElements();
    void sendCommentToServer();
    String getCommentText();
    Comment getCommentObjectToAdd();
    void setEditTextAfterAdded();
  }
}