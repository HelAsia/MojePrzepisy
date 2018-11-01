package com.moje.przepisy.mojeprzepisy.recipe_details.comment_details_display;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.recipe.RecipeRepository;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentDisplayFragment extends Fragment implements CommentDisplayContract.View,
    OnClickListener {
  private CommentDisplayContract.Presenter presenter;
  Context context;
  int commentQty;
  int recipeId;
  Boolean isLogged;
  View view;

  public CommentDisplayFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = getActivity();

    View view = inflater.inflate(R.layout.fragment_comment_display, container, false);
    setView(view);

    presenter = new CommentDisplayPresenter(this, new RecipeRepository(context));
    presenter.setWholeCommentsElements();

    getRecipeId();
    getIsLogged();
    setCommentListeners();

    return view;
  }

  @Nullable
  @Override
  public View getView() {
    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.addCommentButton) {
      presenter.sendCommentToServer();
      presenter.setEditTextAfterAdded();
    }
  }

  @Override
  public void setCommentListeners() {
    if(isLogged){
      getAddCommentButton().setOnClickListener(this);
    }else {
      presenter.setAddCommentButtonAndEditCommentVisibility();
    }
  }

  @Override
  public int getRecipeId() {
    this.recipeId = getArguments().getInt("recipeId");
    return recipeId;
  }

  @Override
  public Boolean getIsLogged() {
    this.isLogged = getArguments().getBoolean("isLogged");
    return isLogged;
  }

  @Override
  public void setCommentsRecyclerView(List<Comment> commentList) {
    CommentDisplayRecipeAdapter adapter = new CommentDisplayRecipeAdapter(context, commentList);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.commentsDisplayRecyclerView);
    recyclerView.setAdapter(adapter);
    setCommentQty(adapter.getItemCount());
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
  }

  @Override
  public EditText getCommentEditText() {
    return (EditText)getView().findViewById(R.id.commentEditText);
  }

  @Override
  public Button getAddCommentButton() {
    return (Button)getView().findViewById(R.id.addCommentButton);
  }

  @Override
  public TextView getCommentQtyTextView() {
    return (TextView) getView().findViewById(R.id.commentQtyTextView);
  }

  @Override
  public void setCommentQty(int commentQty) {
    this.commentQty = commentQty;
  }

  @Override
  public int getCommentQty() {
    return commentQty;
  }
}
