package com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;

import java.util.Collections;
import java.util.List;

public class AddIngredientsAdapter extends RecyclerView.Adapter<AddIngredientsAdapter.ViewHolder>
                                    implements ItemMoveCallback.ItemTouchHelperContract{
  private List<Ingredient> ingredientList;

  AddIngredientsAdapter(List<Ingredient> ingredientList){
    this.ingredientList = ingredientList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public AddIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_ingredient_layout, parent, false);
    return new AddIngredientsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull AddIngredientsAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(ingredientList.get(position));

    viewHolder.deleteImageView.setOnClickListener(view -> {
      ingredientList.remove(position);
      notifyItemRemoved(position);
      notifyDataSetChanged();
    });

    RxTextView.textChanges(viewHolder.ingredientQuantityEditText)
    .subscribe( s -> {
      if(s.toString().length() > 0){
        int ingredientQuantity = Integer.valueOf(s.toString());
        Ingredient updatedIngredient = new Ingredient(ingredientQuantity,
                ingredientList.get(position).getUnit(), ingredientList.get(position).getName());
        ingredientList.set(position, updatedIngredient);
      }
    });

    RxTextView.textChanges(viewHolder.ingredientNameEditText)
    .subscribe( s -> {
      if(s.toString().length() > 0){
        String ingredientName = s.toString();
        Ingredient updatedIngredient = new Ingredient(ingredientList.get(position).getQuantity(),
                ingredientList.get(position).getUnit(), ingredientName);
        ingredientList.set(position, updatedIngredient);
      }
    });

    viewHolder.ingredientUnitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int ItemPosition, long id) {
            String ingredientUnit = (String) adapterView.getSelectedItem();
            Ingredient updatedIngredient =
                    new Ingredient(ingredientList.get(position).getQuantity(),
                            ingredientUnit, ingredientList.get(position).getName());
            ingredientList.set(position, updatedIngredient);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
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

  @Override
  public void onRowMoved(int fromPosition, int toPosition) {
    if (fromPosition < toPosition) {
      for (int i = fromPosition; i < toPosition; i++) {
        Collections.swap(ingredientList, i, i + 1);
      }
    } else {
      for (int i = fromPosition; i > toPosition; i--) {
        Collections.swap(ingredientList, i, i - 1);
      }
    }
    notifyItemMoved(fromPosition, toPosition);
  }

  @Override
  public void onRowSelected(ViewHolder myViewHolder) {
    myViewHolder.ingredientCardView.setBackgroundColor(Color.GRAY);
  }

  @Override
  public void onRowClear(ViewHolder myViewHolder) {
    myViewHolder.ingredientCardView.setBackgroundColor(Color.WHITE);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ingredientQuantityEditText) EditText ingredientQuantityEditText;
    @BindView(R.id.ingredientUnitSpinner) Spinner ingredientUnitSpinner;
    @BindView(R.id.ingredientNameEditText) EditText ingredientNameEditText;
    @BindView(R.id.deleteImageView) ImageView deleteImageView;
    @BindView(R.id.ingredient_my_card_view_layout) CardView ingredientCardView;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Ingredient ingredient) {
      ArrayAdapter myAdapter = (ArrayAdapter)  ingredientUnitSpinner.getAdapter();
      int ingredientUnitSpinnerPosition = myAdapter.getPosition(ingredient.getUnit());
      ingredientUnitSpinner.setSelection(ingredientUnitSpinnerPosition);
      ingredientQuantityEditText.setText(Integer.toString(ingredient.getQuantity()));
      ingredientNameEditText.setText(ingredient.getName());
    }
  }
}
