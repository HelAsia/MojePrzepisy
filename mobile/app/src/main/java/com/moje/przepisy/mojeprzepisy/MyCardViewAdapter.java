package com.moje.przepisy.mojeprzepisy;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Asia on 2018-05-30.
 */

public class MyCardViewAdapter extends RecyclerView.Adapter<MyCardViewAdapter.ViewHolder> {
  public Context context;
  public ArrayList<OneRecipeCard> cardsList;


  public MyCardViewAdapter(Context context, ArrayList<OneRecipeCard> cardsList) {
    this.context = context;
    this.cardsList = cardsList;
  }

  @Override
  public MyCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_card_view_recipe, parent, false);
    return new MyCardViewAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    Bitmap recipePhoto = cardsList.get(position).getPhotoRecipe();
    String recipeName = cardsList.get(position).getRecipeName();
    String recipeAuthor = cardsList.get(position).getAuthorName();
    String starsCount = cardsList.get(position).getStarsCount();
    String favoritesCount = cardsList.get(position).getFavoritesCount();

    ImageView recipeImageView = viewHolder.recipePhoto;
    TextView recipeNameTextView = viewHolder.recipeName;
    TextView recipeAuthorTextView = viewHolder.recipeAuthor;
    TextView starsCountTextView = viewHolder.starsCount;
    TextView favoritesCountTextView = viewHolder.favoritesCount;

    recipeImageView.setImageBitmap(recipePhoto);
    recipeNameTextView.setText(recipeName);
    recipeAuthorTextView.setText(recipeAuthor);
    starsCountTextView.setText(starsCount);
    favoritesCountTextView.setText(favoritesCount);

  }

  @Override
  public int getItemCount() {
    if (cardsList.isEmpty()) {
      return 0;
    } else {
      return cardsList.size();
    }
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public void animateCircularReveal(View view) {
    int centerX = 0;
    int centerY = 0;
    int startRadius = 0;
    int endRadius = Math.max(view.getWidth(), view.getHeight());
    Animator animation = ViewAnimationUtils
        .createCircularReveal(view, centerX, centerY, startRadius, endRadius);
    view.setVisibility(View.VISIBLE);
    animation.start();
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  @Override
  public void onViewAttachedToWindow(MyCardViewAdapter.ViewHolder viewHolder) {
    super.onViewAttachedToWindow(viewHolder);
    animateCircularReveal(viewHolder.itemView);
  }

  @Override
  public long getItemId(int position) {
    return cardsList.get(position).getId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView recipePhoto;
    private TextView recipeName;
    private TextView recipeAuthor;
    private TextView starsCount;
    private TextView favoritesCount;

    public ViewHolder(View v) {
      super(v);
      recipePhoto = (ImageView) v.findViewById(R.id.photo_recipe);
      recipeName = (TextView) v.findViewById(R.id.recipe_name);
      recipeAuthor = (TextView) v.findViewById(R.id.recipe_author);
      starsCount = (TextView) v.findViewById(R.id.star_count);
      favoritesCount = (TextView) v.findViewById(R.id.favorites_count);
    }
  }

}
