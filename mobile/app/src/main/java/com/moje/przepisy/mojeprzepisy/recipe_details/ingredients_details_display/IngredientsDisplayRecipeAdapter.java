package com.moje.przepisy.mojeprzepisy.recipe_details.ingredients_details_display;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class IngredientsDisplayRecipeAdapter extends RecyclerView.Adapter<IngredientsDisplayRecipeAdapter.ViewHolder> {
  public Context context;
  private List<Ingredient> ingredientList;

  IngredientsDisplayRecipeAdapter(Context context, List<Ingredient> ingredientList){
    this.context = context;
    this.ingredientList = ingredientList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public IngredientsDisplayRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_ingredient_display_layout, parent, false);
    return new IngredientsDisplayRecipeAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientsDisplayRecipeAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(ingredientList.get(position));
  }

  @Override
  public int getItemCount() {
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

      if(ingredientQuantity != 0){
        quantityTextView.setText(Integer.toString(ingredientQuantity));
      }else {
        quantityTextView.setText("0");
      }
      if(ingredientUnit != null){
        unitTextView.setText(ingredientUnit);
      }else {
        unitTextView.setText("brak");
      }
      if(ingredientName != null){
        nameIngredientTextView.setText(ingredientName);
      }else {
        nameIngredientTextView.setText("Bran nazwy");
      }
    }
  }
}
