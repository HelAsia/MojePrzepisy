package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import java.util.concurrent.atomic.AtomicInteger;

public class AddIngredientsActivityView extends AppCompatActivity implements View.OnClickListener {
  private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);
  @BindView(R.id.addIngredientButton) Button addIngredientButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_ingredients_view);
    ButterKnife.bind(this);

    addIngredientButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    LinearLayout linearLayoutOneIngredient = (LinearLayout) findViewById(
        R.id.addIngredientsLayout);
      View child = getLayoutInflater().inflate(R.layout.one_ingredient_layout, null);
      child.setId(generateViewId());
      linearLayoutOneIngredient.addView(child);
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
