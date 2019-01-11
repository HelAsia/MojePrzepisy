package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import java.util.List;

public interface CommentDisplayContract {
  interface View{
    Context getContext();
    void setAddCommentButtonListener();
    void setAddCommentButtonAndEditCommentVisibility();
    void setRecipeId();
    void setIsLogged();
    int getRecipeId();
    Boolean getIsLogged();
    String getCommentText();
    Comment getCommentObjectToAdd();
    void setEditTextAfterAdded();
    void setCommentNumberOnScreen(int commentNumber);
    void setCommentsRecyclerViewAndCommentNumber(List<Comment> commentList);
    void setToastMessage(String message);
  }

  interface Presenter{
    void setFirstScreen();
    void setWholeCommentsElements();
    void setCommentListeners();
    void sendCommentToServer();
  }
}
