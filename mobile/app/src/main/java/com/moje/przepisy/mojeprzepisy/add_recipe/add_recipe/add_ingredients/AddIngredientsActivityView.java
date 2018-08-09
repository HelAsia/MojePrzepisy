package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.IngredientElementsId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AddIngredientsActivityView extends AppCompatActivity implements View.OnClickListener {
  private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);
  @BindView(R.id.addIngredientFab) FloatingActionButton addIngredientFab;
  String backgroundColorString = "#ffffff";
  int[] layoutElementsArray = new int[6];
  List<ImageView> imageViewList = new ArrayList<>();
  List<IngredientElementsId> ingredientElementsIdList = new ArrayList<>();
  Random randomNumber = new Random();
  LinearLayout linearLayoutOneIngredient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    ButterKnife.bind(this);

    linearLayoutOneIngredient = (LinearLayout) findViewById(
        R.id.addIngredientsLayout);

    addIngredientFab.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addIngredientFab){
      View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);
      child.setId(generateViewId());

      setBackground(child);

      linearLayoutOneIngredient.addView(child);

      getElementsIdToArray(child);

      IngredientElementsId layoutForIngredient = new IngredientElementsId(generateViewId(), this.layoutElementsArray[0],
          this.layoutElementsArray[1], this.layoutElementsArray[2], this.layoutElementsArray[3], this.layoutElementsArray[4], this.layoutElementsArray[5]);

      ingredientElementsIdList.add(layoutForIngredient);

      Toast.makeText(this, "Id: " + child.getId(), Toast.LENGTH_SHORT).show();

      setDeleteImageViews(ingredientElementsIdList);

      setDeleteImageViewListener();

    }else {
      Toast.makeText(this, "Id: " + view.getId(), Toast.LENGTH_SHORT).show();
      View myViewToRemove = findViewById(view.getId());

      ViewGroup parentViewToRemove = (ViewGroup) myViewToRemove.getParent();
      ViewGroup parentParentViewToRemove = (ViewGroup) parentViewToRemove.getParent();
      parentParentViewToRemove.removeAllViews();

/*      ingredientElementsIdList.remove(new IngredientElementsId(parentParentViewToRemove.getId(), parentViewToRemove.getId(),
          parentViewToRemove.getChildAt(0).getId(), parentViewToRemove.getChildAt(1).getId(),
          parentViewToRemove.getChildAt(2).getId(), parentViewToRemove.getChildAt(3).getId(), parentParentViewToRemove.getChildAt(1).getId()));*/
        ingredientElementsIdList.remove(2);
      setIngredientBackgroundAfterDelete(ingredientElementsIdList);
    }
  }

  public void setBackground(View child){
    if(backgroundColorString.equals("#ffffff")){
      this.backgroundColorString = "#8033ffff";
    }else if(backgroundColorString.equals("#8033ffff")){
      this.backgroundColorString = "#ffffff";
    }
    child.setBackgroundColor(Color.parseColor(backgroundColorString));
  }

  public void setIngredientBackgroundAfterDelete(List<IngredientElementsId> ingredientElementsIdList){

    for (IngredientElementsId oneIngredient : ingredientElementsIdList){
      View oneIngredientView = findViewById(oneIngredient.getLayoutId());
      if(backgroundColorString.equals("#ffffff")){
        this.backgroundColorString = "#8033ffff";
      }else if(backgroundColorString.equals("#8033ffff")){
        this.backgroundColorString = "#ffffff";
      }
      oneIngredientView.setBackgroundColor(Color.parseColor(backgroundColorString));
    }
  }

  public void getElementsIdToArray(View child){
    ViewGroup childElementsView = (ViewGroup) findViewById(child.getId());

    for(int i = 0; i < childElementsView.getChildCount(); i++){
      View childOneElementView = childElementsView.getChildAt(i);
      childOneElementView.setId(randomNumber.nextInt(100000 - 100 + 1) + 100);
      int childId = childOneElementView.getId();
      if (i == 0){
        this.layoutElementsArray[0] = childId;
        ViewGroup insideChildElementView = (ViewGroup) findViewById(childId);
        for(int j = 0; j < insideChildElementView.getChildCount(); j++){
          View insideChildOneElementView = insideChildElementView.getChildAt(j);
          insideChildOneElementView.setId(randomNumber.nextInt(100000 - 100 + 1) + 100);
          int insideChildId = insideChildOneElementView.getId();
          this.layoutElementsArray[j+1] = insideChildId;
        }
      }if (i == 1){
        this.layoutElementsArray[5] = childId;
      }
    }
  }

  public void setDeleteImageViews(List<IngredientElementsId> ingredientElementsIdList){
    for (int i = 0; i < ingredientElementsIdList.size(); i++){
      imageViewList.add((ImageView) findViewById(ingredientElementsIdList.get(i).getDeleteImageViewId()));
    }
  }

  public void setDeleteImageViewListener(){
    for(ImageView oneImageView : imageViewList){
      oneImageView.setOnClickListener(this);
    }
  }

  public int generateViewId(){
    for(;;){
      final int oldViewId = nextGeneratedId.get();
      int newViewId = oldViewId + 1;
      if (newViewId > 0x00FFFFFF) newViewId = 1;
      if (nextGeneratedId.compareAndSet(oldViewId, newViewId)){
        return oldViewId;
      }
    }
  }
}
