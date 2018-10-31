package com.moje.przepisy.mojeprzepisy.recipe_details.main_recipe_details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.recipe_details.comment_details_display.CommentDisplayFragment;
import com.moje.przepisy.mojeprzepisy.recipe_details.ingredients_details_display.IngredientsDisplayFragment;
import com.moje.przepisy.mojeprzepisy.recipe_details.main_info_details_display.MainInfoDetailsDisplayFragment;
import com.moje.przepisy.mojeprzepisy.recipe_details.steps_details_display.StepsDisplayFragment;

public class MainDetailsFragmentPagerAdapter extends FragmentPagerAdapter {
  private Context context;
  private int recipeId;
  private Boolean isLogged;

  public MainDetailsFragmentPagerAdapter(Context context, FragmentManager fm, int recipeId, Boolean isLogged){
    super(fm);
    this.context = context;
    this.recipeId = recipeId;
    this.isLogged = isLogged;
  }

  @Override
  public Fragment getItem(int position) {
    if(position == 0){
      return getMainInfoDetailsDisplayFragment();
    }else if(position == 1){
      return getIngredientsDisplayFragment();
    }else if(position == 2){
      return getStepsDisplayFragment();
    }else{
      return getCommentDisplayFragment();
    }
  }

  public MainInfoDetailsDisplayFragment getMainInfoDetailsDisplayFragment(){
    Bundle data = new Bundle();
    data.putInt("recipeId", recipeId);
    data.putBoolean("isLogged", isLogged);

    MainInfoDetailsDisplayFragment mainInfoDetailsDisplayFragment = new MainInfoDetailsDisplayFragment();
    mainInfoDetailsDisplayFragment.setArguments(data);

    return mainInfoDetailsDisplayFragment;
  }

  public IngredientsDisplayFragment getIngredientsDisplayFragment(){
    Bundle data = new Bundle();
    data.putInt("recipeId", recipeId);
    data.putBoolean("isLogged", isLogged);

    IngredientsDisplayFragment ingredientsDisplayFragment = new IngredientsDisplayFragment();
    ingredientsDisplayFragment.setArguments(data);

    return ingredientsDisplayFragment;
  }

  public StepsDisplayFragment getStepsDisplayFragment(){
    Bundle data = new Bundle();
    data.putInt("recipeId", recipeId);
    data.putBoolean("isLogged", isLogged);

    StepsDisplayFragment stepsDisplayFragment = new StepsDisplayFragment();
    stepsDisplayFragment.setArguments(data);

    return stepsDisplayFragment;
  }

  public CommentDisplayFragment getCommentDisplayFragment(){
    Bundle data = new Bundle();
    data.putInt("recipeId", recipeId);
    data.putBoolean("isLogged", isLogged);

    CommentDisplayFragment commentDisplayFragment = new CommentDisplayFragment();
    commentDisplayFragment.setArguments(data);

    return commentDisplayFragment;
  }

  @Override
  public int getCount() {
    return 4;
  }

  @Override
  public CharSequence getPageTitle(int position){
    switch (position){
      case 0:
        return context.getString(R.string.main_info);
      case 1:
        return context.getString(R.string.ingredients);
      case 2:
        return context.getString(R.string.steps);
      case 3:
        return context.getString(R.string.comments);
      default:
        return null;
    }
  }
}
