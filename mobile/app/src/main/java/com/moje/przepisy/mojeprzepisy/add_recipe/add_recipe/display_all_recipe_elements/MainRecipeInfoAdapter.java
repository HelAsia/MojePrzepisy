package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Recipe;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import java.util.List;

public class MainRecipeInfoAdapter extends RecyclerView.Adapter<MainRecipeInfoAdapter.ViewHolder>{
  public Context context;
  private List<Recipe> recipeList;
  private BitmapConverter converter = new BitmapConverter();

  MainRecipeInfoAdapter(Context context, List<Recipe> recipeList){
    this.context = context;
    this.recipeList = recipeList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public MainRecipeInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_main_recipe_info_layout, parent, false);
    return new MainRecipeInfoAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull MainRecipeInfoAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(recipeList.get(position));
  }

  @Override
  public int getItemCount()  {
    return recipeList.size();
  }

  @Override
  public long getItemId(int position) {
    return recipeList.get(position).getRecipeId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipeNameTextView) TextView recipeNameTextView;
    @BindView(R.id.recipeCategoryTextView) TextView recipeCategoryTextView;
    @BindView(R.id.preparedTimeTextView) TextView preparedTimeTextView;
    @BindView(R.id.cookTimeTextView) TextView cookTimeTextView;
    @BindView(R.id.bakeTimeTextView) TextView bakeTimeTextView;
    @BindView(R.id.recipeImageView) ImageView recipeImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Recipe recipe) {
      String recipeName = recipe.getRecipeName();
      String recipeCategory = recipe.getRecipeCategory();
      String recipePrepareTime = recipe.getRecipePrepareTime();
      String recipeCookTime = recipe.getRecipeCookTime();
      String recipeBakeTime = recipe.getRecipeBakeTime();
      String recipeMainPicture = recipe.getRecipeMainPicture();

      recipeNameTextView.setText(recipeName);
      recipeCategoryTextView.setText(recipeCategory);
      preparedTimeTextView.setText(recipePrepareTime);
      cookTimeTextView.setText(recipeCookTime);
      bakeTimeTextView.setText(recipeBakeTime);
      recipeImageView.setImageBitmap(converter.StringToBitMap(recipeMainPicture));
    }
  }
}
