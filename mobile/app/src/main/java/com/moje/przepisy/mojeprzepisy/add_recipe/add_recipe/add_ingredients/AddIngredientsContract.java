package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients;

import android.support.design.widget.FloatingActionButton;

public interface AddIngredientsContract {

  interface View {

    FloatingActionButton getAddIngredientFloatingActionButton();

    FloatingActionButton getPreviousFloatingActionButton();

    FloatingActionButton getNextloatingActionButton();

    void setToolbar();

  }

  interface Presenter {

  }

}
