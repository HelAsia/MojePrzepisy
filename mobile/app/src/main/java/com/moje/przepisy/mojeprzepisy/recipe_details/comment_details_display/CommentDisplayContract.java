package com.moje.przepisy.mojeprzepisy.recipe_details.comment_details_display;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import java.util.List;

public interface CommentDisplayContract {
  interface View{
    Context getContext();
    void setCommentListeners();
    int getRecipeId();
    Boolean getIsLogged();
    void setCommentsRecyclerViewAndCommentNumber(List<Comment> commentList);
    EditText getCommentEditText();
    Button getAddCommentButton();
    TextView getCommentNumberTextView();
  }
  interface Presenter{
    void setWholeCommentsElements();
    void sendCommentToServer();
    String getCommentText();
    Comment getCommentObjectToAdd();
    void setEditTextAfterAdded();
    void setAddCommentButtonAndEditCommentVisibility();
    void setCommentNumberOnScreen(int commentNumber);
  }
}
