package com.moje.przepisy.mojeprzepisy.recipe_details;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public interface RecipeDetailsContract {

  interface View{

    Context getContext();

    void setToolbar();

    void setListeners();

    void setMainRecipeInfoLayout();

    void setIngredientsRecyclerView(List<Ingredient> ingredientList);

    void setStepsRecyclerView(List<Step> stepList);

    void setCommentsRecyclerView(List<Comment> commentList);

    TextView getRecipeNameTextView();

    ImageView getRecipeImageView();

    TextView getRecipeCategoryTextView();

    TextView getPreparedTimeTextView();

    TextView getCookTimeTextView();

    TextView getBakeTimeTextView();

    EditText getCommentEditText();

    Button getAddCommentButton();

    ImageView getStarImageView();

    TextView getStarCountTextView();

    ImageView getFavoritesImageView();

    TextView getFavoritesCountTextView();

  }

  interface Presenter{

  }

}
