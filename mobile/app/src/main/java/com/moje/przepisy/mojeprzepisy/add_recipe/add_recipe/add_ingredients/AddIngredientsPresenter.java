package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddIngredientsPresenter implements AddIngredientsContract.Presenter {
  private RecipeRepository recipeRepository;
  private AddIngredientsContract.View ingredientsView;
  private String backgroundColorString = "#CCFFCC";
  private Random randomNumber = new Random();
  private int[] layoutElementsArray = new int[6];
  private List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();

  public AddIngredientsPresenter(AddIngredientsContract.View ingredientsView, RecipeRepository recipeRepository){
    this.ingredientsView = ingredientsView;
    this.recipeRepository = recipeRepository;
  }

  public int getPositionOfLayoutToRemove(int elementId, List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      if (ingredientElementsIdList.get(i).getDeleteImageViewId() == elementId){
        return i;
      }
    }
    return -1;
  }

  public int generateViewId(){
    return randomNumber.nextInt((1000000000 - 100 + 1) + 100);
  }

  public void setBackground(View child){
    if(backgroundColorString.equals("#ffffff")){
      this.backgroundColorString = "#CCFFCC";
    }else if(backgroundColorString.equals("#CCFFCC")){
      this.backgroundColorString = "#ffffff";
    }
    child.setBackgroundColor(Color.parseColor(backgroundColorString));
  }

  public void setIngredientBackgroundAfterDelete(LinearLayout linearLayoutOneIngredient){
    for(int i = 0; i < linearLayoutOneIngredient.getChildCount(); i++){
      View linearLayoutOneIngredientView = linearLayoutOneIngredient.getChildAt(i);
      if(i % 2 == 0) {
        this.backgroundColorString = "#ffffff";
      }else if(i % 2 == 1){
        this.backgroundColorString = "#CCFFCC";
      }
      linearLayoutOneIngredientView.setBackgroundColor(Color.parseColor( backgroundColorString));
    }
  }

  public List<IngredientElementsId> getIngredientElementsIdList(){
    return ingredientElementsIdList;
  }

  public void setChildId(View child){
    child.setId(generateViewId());
  }

  public void getElementsIdToArray(ViewGroup childElementsView){
    for(int childNumber = 0; childNumber < childElementsView.getChildCount(); childNumber++){
      View childOneElementView = childElementsView.getChildAt(childNumber);
      childOneElementView.setId(generateViewId());
      int childId = childOneElementView.getId();
      if (childNumber == 0){
        this.layoutElementsArray[0] = childId;
        getInsideChildElementsToArray(ingredientsView.getInsideChildElementView(childId));
      }if (childNumber == 1){
        this.layoutElementsArray[5] = childId;
      }
    }
  }
  public void getInsideChildElementsToArray(ViewGroup insideChildElementView){
    for(int insideChildNumber = 0; insideChildNumber < insideChildElementView.getChildCount(); insideChildNumber++){
      View insideChildOneElementView = insideChildElementView.getChildAt(insideChildNumber);
      insideChildOneElementView.setId(generateViewId());
      int insideChildId = insideChildOneElementView.getId();
      this.layoutElementsArray[insideChildNumber+1] = insideChildId;
    }
  }

  public IngredientElementsId getLayoutForIngredient(View child){
    IngredientElementsId layoutForIngredient = new IngredientElementsId(child.getId(), this.layoutElementsArray[0],
        this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4], this.layoutElementsArray[5]);
    return layoutForIngredient;
  }

  public void addLayoutToElementsIdList(View child){
    ingredientElementsIdList.add(getLayoutForIngredient(child));
  }
}

