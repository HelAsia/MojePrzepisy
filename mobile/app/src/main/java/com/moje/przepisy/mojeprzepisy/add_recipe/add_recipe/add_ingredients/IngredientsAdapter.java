package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import java.lang.reflect.Type;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
  public Context context;
  private List<Ingredient> ingredientList;
  private Gson gson = new Gson();

  IngredientsAdapter(Context context, List<Ingredient> ingredientList){
    this.context = context;
    this.ingredientList = ingredientList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_ingredient_layout, parent, false);
    return new IngredientsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder viewHolder, int position) {
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
    @BindView(R.id.ingredientQuantityEditText) EditText ingredientQuantityEditText;
    @BindView(R.id.ingredientUnitSpinner) Spinner ingredientUnitSpinner;
    @BindView(R.id.ingredientNameEditText) EditText ingredientNameEditText;
    @BindView(R.id.deleteImageView) ImageView deleteImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
      deleteImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          ingredientList.remove(getAdapterPosition());
          notifyItemRemoved(getAdapterPosition());
          String pojoJson = convertPojoToJsonString(ingredientList);
          addPojoListToPreferences(pojoJson, context);
        }
      });

      ingredientQuantityEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          int ingredientQuantity = Integer.valueOf(ingredientQuantityEditText.getText().toString());
          Ingredient updatedIngredient = new Ingredient(ingredientQuantity, ingredientList.get(getAdapterPosition()).getIngredientUnit(), ingredientList.get(getAdapterPosition()).getIngredientName());
          ingredientList.set(getAdapterPosition(), updatedIngredient);

          String pojoJson = convertPojoToJsonString(ingredientList);
          addPojoListToPreferences(pojoJson, context);
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
          Ingredient updatedIngredient = new Ingredient(ingredientList.get(getAdapterPosition()).getIngredientQuantity(), ingredientList.get(getAdapterPosition()).getIngredientUnit(), ingredientName);
          ingredientList.set(getAdapterPosition(), updatedIngredient);

          String pojoJson = convertPojoToJsonString(ingredientList);
          addPojoListToPreferences(pojoJson, context);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
      });

      ingredientUnitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            int ingredientUnit = (int) adapterView.getSelectedItemId();

            Ingredient updatedIngredient = new Ingredient(ingredientList.get(getAdapterPosition()).getIngredientQuantity(), ingredientUnit, ingredientList.get(getAdapterPosition()).getIngredientName());
            ingredientList.set(getAdapterPosition(), updatedIngredient);

            String pojoJson = convertPojoToJsonString(ingredientList);
            addPojoListToPreferences(pojoJson, context);
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
      });

    }

    void bind(Ingredient ingredient) {
      int ingredientQuantity = ingredient.getIngredientQuantity();
      int ingredientUnit = ingredient.getIngredientUnit();
      String ingredientName = ingredient.getIngredientName();

      ingredientQuantityEditText.setText(Integer.toString(ingredientQuantity));
      ingredientUnitSpinner.setSelection(ingredientUnit);
      ingredientNameEditText.setText(ingredientName);
    }
  }

  private String convertPojoToJsonString(List<Ingredient> ingredientList) {
    Type type = new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(ingredientList, type);
  }


  private void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_INGREDIENT, jsonList).apply();
    editor.commit();
  }
}
