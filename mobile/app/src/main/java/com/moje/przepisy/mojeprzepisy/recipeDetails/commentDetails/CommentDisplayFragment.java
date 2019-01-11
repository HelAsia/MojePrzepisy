package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepositoryInterface;
import com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay.MainDetailsTabActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentDisplayFragment extends Fragment
        implements CommentDisplayContract.View{
  @BindView(R.id.commentsDisplayRecyclerView) RecyclerView commentsDisplayRecyclerView;
  @BindView(R.id.commentEditText) EditText commentEditText;
  @BindView(R.id.addCommentButton) Button addCommentButton;
  @BindView(R.id.commentQtyTextView) TextView commentQtyTextView;
  private RecipeRepository recipeRepository;
  private CommentDisplayContract.Presenter presenter;
  private Context context;
  private int recipeId;
  private Boolean isLogged;
  private int commentId;
  private String commentText;

  public CommentDisplayFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_comment_display, container, false);

    ButterKnife.bind(this, view);

    recipeRepository = new RecipeRepository(context);

    presenter = new CommentDisplayPresenter(this, recipeRepository);
    presenter.setFirstScreen();

    return view;
  }

  public void setIsLogged() {
    if(getArguments() != null){
      isLogged = getArguments().getBoolean("isLogged");
    }
  }

  @Override
  public void setAddCommentButtonListener(){
    addCommentButton.setOnClickListener(view -> {
      presenter.sendCommentToServer();
      setEditTextAfterAdded();
    });
  }

  @Override
  public void setAddCommentButtonAndEditCommentVisibility() {
    addCommentButton.setVisibility(View.GONE);
    commentEditText.setVisibility(View.GONE);
  }

  @Override
  public void setRecipeId(){
    if(getArguments() != null){
      recipeId = getArguments().getInt("id");
    }
  }

  @Override
  public int getRecipeId() {
    return recipeId;
  }

  @Override
  public Boolean getIsLogged() {
    return isLogged;
  }

  @Override
  public void setCommentsRecyclerViewAndCommentNumber(List<Comment> commentList) {
    CommentDisplayAdapter adapter = new CommentDisplayAdapter(context, commentList);
    commentsDisplayRecyclerView.setAdapter(adapter);
    commentsDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    adapter.setMenuClickedListener((id, commentCard, comment) -> {
        commentId = id;
        commentText = comment;
        registerForContextMenu(commentCard);
    });
    setCommentNumberOnScreen(adapter.getItemCount());
  }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_and_delete_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
              Intent intent = new Intent(context, CommentEditActivity.class);
              Bundle data = new Bundle();
              data.putString("comment", commentText);
              data.putInt("commentId", commentId);
              data.putInt("recipeId", recipeId);
              intent.putExtras(data);
              startActivity(intent);

                return true;
            case R.id.delete:
                recipeRepository.deleteComment(commentId,
                        new RecipeRepositoryInterface.OnDeleteCommentsDetailsDisplayListener() {
                            @Override
                            public void onSuccess() {
                              presenter.setWholeCommentsElements();
                                Toast.makeText(context, "Komentarz został usunięty!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(context, "Błąd podczas usuwania komentarza!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

  @Override
  public void setToastMessage(String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public String getCommentText() {
    return commentEditText.getText().toString();
  }

  @Override
  public Comment getCommentObjectToAdd() {
    return new Comment(recipeId, getCommentText());
  }

  @Override
  public void setEditTextAfterAdded() {
    commentEditText.setText("");
    commentEditText.setHint("Napisz komentarz...");
  }

  @Override
  public void setCommentNumberOnScreen(int commentNumber) {
    commentQtyTextView.setText((commentNumber + " komentarz(y)"));
  }
}
