package com.moje.przepisy.mojeprzepisy.ui;

import android.animation.Animator;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Asia on 2018-05-30.
 */

public class MyCardViewAdapter extends RecyclerView.Adapter<MyCardViewAdapter.ViewHolder> {
  public Context context;
  private List<OneRecipeCard> cardsList;

  MyCardViewAdapter(Context context, List<OneRecipeCard> cardsList) {
    this.context = context;
    this.cardsList = cardsList;
    setHasStableIds(true);
  }

  @Override
  public MyCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_card_view_recipe, parent, false);
    return new MyCardViewAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.bind(cardsList.get(position));
  }

  @Override
  public int getItemCount() {
      return cardsList.size();
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
    @BindView(R.id.image_view_photo_recipe) ImageView recipeImageView;
    @BindView(R.id.text_view_recipe_name) TextView recipeNameTextView;
    @BindView(R.id.text_view_recipe_author) TextView recipeAuthorTextView;
    @BindView(R.id.text_view_star_count) TextView starsCountTextView;
    @BindView(R.id.text_view_favorites_count) TextView favoritesCountTextView;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(OneRecipeCard card) {
      String recipeMainPicture = card.getRecipeMainPicture();
      String recipeName = card.getRecipeName();
      String recipeAuthor = card.getAuthorName();
      int starsCount = card.getStarsCount();
      int favoritesCount = card.getFavoritesCount();
      String starsCountString = String.valueOf(starsCount);
      String favoritesCountString = String.valueOf(favoritesCount);

      Picasso.get().load(recipeMainPicture).into(recipeImageView);
      recipeNameTextView.setText(recipeName);
      recipeAuthorTextView.setText(recipeAuthor);
      starsCountTextView.setText(starsCountString);
      favoritesCountTextView.setText(favoritesCountString);
    }
  }
}
