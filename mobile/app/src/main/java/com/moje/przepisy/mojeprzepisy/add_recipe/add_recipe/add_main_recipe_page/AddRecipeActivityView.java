package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;

public class AddRecipeActivityView extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.nextButton) Button toTheIngredientsButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    ButterKnife.bind(this);

    toTheIngredientsButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    Intent intent = new Intent (AddRecipeActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }
}
