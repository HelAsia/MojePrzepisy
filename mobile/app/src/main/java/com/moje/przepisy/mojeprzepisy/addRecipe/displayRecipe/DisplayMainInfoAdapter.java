package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

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

public class DisplayMainInfoAdapter extends RecyclerView.Adapter<DisplayMainInfoAdapter.ViewHolder>{
  public Context context;
  private List<Recipe> recipeList;
  private BitmapConverter converter = new BitmapConverter();

  DisplayMainInfoAdapter(Context context, List<Recipe> recipeList){
    this.context = context;
    this.recipeList = recipeList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public DisplayMainInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_main_recipe_info_layout, parent, false);
    return new DisplayMainInfoAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull DisplayMainInfoAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(recipeList.get(position));
  }

  @Override
  public int getItemCount()  {
    return recipeList.size();
  }

  @Override
  public long getItemId(int position) {
    return recipeList.get(position).getId();
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
      String recipeName = recipe.getName();
      String recipeCategory = recipe.getCategory();
      String recipePrepareTime = recipe.getPrepareTime();
      String recipeCookTime = recipe.getCookTime();
      String recipeBakeTime = recipe.getBakeTime();
      String recipeMainPicture = recipe.getMainPicture();

      recipeNameTextView.setText(recipeName);
      recipeCategoryTextView.setText(recipeCategory);
      preparedTimeTextView.setText(recipePrepareTime);
      cookTimeTextView.setText(recipeCookTime);
      bakeTimeTextView.setText(recipeBakeTime);
      recipeImageView.setImageBitmap(converter.StringToBitMap(recipeMainPicture));
    }
  }
}
