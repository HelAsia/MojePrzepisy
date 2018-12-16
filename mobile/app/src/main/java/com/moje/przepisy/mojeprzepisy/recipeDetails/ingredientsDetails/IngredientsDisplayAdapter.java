package com.moje.przepisy.mojeprzepisy.recipeDetails.ingredientsDetails;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class IngredientsDisplayAdapter extends RecyclerView.Adapter<IngredientsDisplayAdapter.ViewHolder> {
  public Context context;
  private List<Ingredient> ingredientList;
  private Boolean isCrossed = false;

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
  public void onBindViewHolder(@NonNull final IngredientsDisplayAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(ingredientList.get(position));

    viewHolder.ingredientsCardViewLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(isCrossed){
          viewHolder.ingredientsCardViewLayout.setCardBackgroundColor(Color.parseColor("#ffffff"));
          viewHolder.ingredientsTableRow.setBackgroundColor(Color.parseColor("#ffffff"));
          viewHolder.nameIngredientTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
          viewHolder.quantityTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
          viewHolder.unitTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
          isCrossed = false;
        }else {
          viewHolder.ingredientsCardViewLayout.setCardBackgroundColor(Color.parseColor("#757575"));
          viewHolder.ingredientsTableRow.setBackgroundColor(Color.parseColor("#757575"));
          viewHolder.nameIngredientTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
          viewHolder.quantityTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
          viewHolder.unitTextView.setPaintFlags(viewHolder.nameIngredientTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
          isCrossed = true;
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return ingredientList.size();
  }

  @Override
  public long getItemId(int position) {
    return ingredientList.get(position).getId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.quantityTextView) TextView quantityTextView;
    @BindView(R.id.unitTextView) TextView unitTextView;
    @BindView(R.id.nameIngredientTextView) TextView nameIngredientTextView;
    @BindView(R.id.ingredientsTableRow) TableRow ingredientsTableRow;
    @BindView(R.id.ingredient_display_my_card_view_layout) CardView ingredientsCardViewLayout;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Ingredient ingredient) {
      int ingredientQuantity = ingredient.getQuantity();
      String ingredientUnit = ingredient.getUnit();
      String ingredientName = ingredient.getName();

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
