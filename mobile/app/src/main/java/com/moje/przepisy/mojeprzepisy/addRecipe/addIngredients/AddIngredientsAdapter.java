package com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import java.util.List;

public class AddIngredientsAdapter extends RecyclerView.Adapter<AddIngredientsAdapter.ViewHolder> {
  public Context context;
  private List<Ingredient> ingredientList;

  AddIngredientsAdapter(Context context, List<Ingredient> ingredientList){
    this.context = context;
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
    @BindView(R.id.ingredientQuantityEditText) EditText ingredientQuantityEditText;
    @BindView(R.id.ingredientUnitSpinner) Spinner ingredientUnitSpinner;
    @BindView(R.id.ingredientNameEditText) EditText ingredientNameEditText;
    @BindView(R.id.deleteImageView) ImageView deleteImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
      deleteImageView.setOnClickListener(it -> {
          ingredientList.remove(getAdapterPosition());
          notifyItemRemoved(getAdapterPosition());
          ((AddIngredientsActivity)context).setIngredientList(ingredientList);
      });

      ingredientQuantityEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          int ingredientQuantity = Integer.valueOf(ingredientQuantityEditText.getText().toString());
          Ingredient updatedIngredient = new Ingredient(ingredientQuantity, ingredientList.get(getAdapterPosition()).getUnit(), ingredientList.get(getAdapterPosition()).getName());
          ingredientList.set(getAdapterPosition(), updatedIngredient);

          ((AddIngredientsActivity)context).setIngredientList(ingredientList);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
      });

      ingredientNameEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          String ingredientName = ingredientNameEditText.getText().toString();
          Ingredient updatedIngredient = new Ingredient(ingredientList.get(getAdapterPosition()).getQuantity(), ingredientList.get(getAdapterPosition()).getUnit(), ingredientName);
          ingredientList.set(getAdapterPosition(), updatedIngredient);

          ((AddIngredientsActivity)context).setIngredientList(ingredientList);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
      });

      ingredientUnitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
          String ingredientUnit = (String) adapterView.getSelectedItem();

          Ingredient updatedIngredient = new Ingredient(ingredientList.get(getAdapterPosition()).getQuantity(), ingredientUnit, ingredientList.get(getAdapterPosition()).getName());
          ingredientList.set(getAdapterPosition(), updatedIngredient);

          ((AddIngredientsActivity)context).setIngredientList(ingredientList);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
      });
    }

    void bind(Ingredient ingredient) {
      int ingredientQuantity = ingredient.getQuantity();
      String ingredientUnit = ingredient.getUnit();
      String ingredientName = ingredient.getName();

      ArrayAdapter myAdapter = (ArrayAdapter)  ingredientUnitSpinner.getAdapter();
      int ingredientUnitSpinnerPosition = myAdapter.getPosition(ingredientUnit);
      ingredientQuantityEditText.setText(Integer.toString(ingredientQuantity));
      ingredientUnitSpinner.setSelection(ingredientUnitSpinnerPosition);
      ingredientNameEditText.setText(ingredientName);
    }
  }
}
