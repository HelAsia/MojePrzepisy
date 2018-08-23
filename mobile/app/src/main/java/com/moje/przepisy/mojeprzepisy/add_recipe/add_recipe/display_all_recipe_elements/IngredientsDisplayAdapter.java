package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;
import com.google.gson.Gson;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class IngredientsDisplayAdapter extends RecyclerView.Adapter<IngredientsDisplayAdapter.ViewHolder> {
  public Context context;
  private List<Ingredient> ingredientList;

  IngredientsDisplayAdapter(Context context, List<Ingredient> ingredientList){
    this.context = context;
    this.ingredientList = ingredientList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public IngredientsDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_ingredient_display_layout, parent, false);
    return new IngredientsDisplayAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientsDisplayAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(ingredientList.get(position));
  }

  @Override
  public int getItemCount()  {
    return ingredientList.size();
  }

  @Override
  public long getItemId(int position) {
    return ingredientList.get(position).getIngredientId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.quantityTextView) TextView quantityTextView;
    @BindView(R.id.unitTextView) TextView unitTextView;
    @BindView(R.id.nameIngredientTextView) TextView nameIngredientTextView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Ingredient ingredient) {
      int ingredientQuantity = ingredient.getIngredientQuantity();
      String ingredientUnit = ingredient.getIngredientUnit();
      String ingredientName = ingredient.getIngredientName();

      quantityTextView.setText(Integer.toString(ingredientQuantity));
      unitTextView.setText(ingredientUnit);
      nameIngredientTextView.setText(ingredientName);
    }
  }
}
