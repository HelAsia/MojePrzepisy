package com.moje.przepisy.mojeprzepisy.ui;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Asia on 2018-05-30.
 */

public class MyCardViewAdapter extends RecyclerView.Adapter<MyCardViewAdapter.ViewHolder> {
  public Context context;
  private List<OneRecipeCard> cardsList;
  private OnShareStarsClickedListener callbackStars;
  private OnShareHeartClickedListener callbackHeart;
  private OnShareRecipeIdClickedListener callbackRecipeId;
  
  MyCardViewAdapter(Context context, List<OneRecipeCard> cardsList) {
    this.context = context;
    this.cardsList = cardsList;
    setHasStableIds(true);
  }

  public void setHeartOnShareClickedListener(OnShareHeartClickedListener callbackHeart) {
    this.callbackHeart = callbackHeart;
  }

  public interface OnShareHeartClickedListener {
    void shareHeartClicked(int recipeId, int favorite, int position);
  }

  public void setStarsOnShareClickedListener(OnShareStarsClickedListener callbackStars) {
    this.callbackStars = callbackStars;
  }

  public interface OnShareStarsClickedListener {
    void shareStarsClicked(int recipeId, int starRate, int position);
  }

  public void setCallbackRecipeIdOnShareClickedListener(OnShareRecipeIdClickedListener callbackRecipeId) {
    this.callbackRecipeId = callbackRecipeId;
  }

  public interface OnShareRecipeIdClickedListener {
    void shareRecipeIdClicked(int recipeId);
  }


  @Override
  public MyCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_card_view_recipe, parent, false);
    return new MyCardViewAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
    viewHolder.bind(cardsList.get(position));

    viewHolder.recipeImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        int recipeId = (int)getItemId(position);
        callbackRecipeId.shareRecipeIdClicked(recipeId);
      }
    });

    Boolean isLogged =((MainCardsActivityView)context).getIsLoggedStatus();
    String sortedMethodPref = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(Constant.PREF_SORTED_METHOD,"default");

    if(isLogged && !sortedMethodPref.equals("favorite")){
      viewHolder.starImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if(viewHolder.ratingBarStars.getVisibility() == View.INVISIBLE){
            viewHolder.ratingBarStars.setVisibility(View.VISIBLE);
          }else {
            viewHolder.ratingBarStars.setVisibility(View.INVISIBLE);
          }
        }
      });

      viewHolder.ratingBarStars.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
          int recipeId = (int)getItemId(position);
          int rate = (int)v;
          callbackStars.shareStarsClicked(recipeId, rate, position);
          viewHolder.ratingBarStars.setVisibility(View.INVISIBLE);
        }
      });

      viewHolder.heartImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          int recipeId = (int)getItemId(position);
          Drawable heartBorder = context.getResources().getDrawable(R.mipmap.ic_favorite_border);
          Drawable heartSolid = context.getResources().getDrawable(R.mipmap.ic_favorite);
          Boolean favorite = cardsList.get(position).getFavorite();

          if(!favorite){
            viewHolder.heartImageView.setImageDrawable(heartSolid);
            callbackHeart.shareHeartClicked(recipeId, 1, position);
          }else {
            viewHolder.heartImageView.setImageDrawable(heartBorder);
            callbackHeart.shareHeartClicked(recipeId, 0, position);
          }
        }
      });
    }
  }

  @Override
  public int getItemCount() {
      return cardsList.size();
  }

  public void updateFavoriteOnCard(OneRecipeCard updatedCard, int list_position) {
    cardsList.get(list_position).setFavorite(updatedCard.getFavorite());
    cardsList.get(list_position).setFavoritesCount(updatedCard.getFavoritesCount());
    cardsList.get(list_position).setStarsCount(updatedCard.getStarsCount());
    notifyItemChanged(list_position);
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
    return cardsList.get(position).getRecipeId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view_photo_recipe) ImageView recipeImageView;
    @BindView(R.id.text_view_recipe_name) TextView recipeNameTextView;
    @BindView(R.id.text_view_recipe_author) TextView recipeAuthorTextView;
    @BindView(R.id.text_view_star_count) TextView starsCountTextView;
    @BindView(R.id.text_view_favorites_count) TextView favoritesCountTextView;
    @BindView(R.id.ratingBarStars) RatingBar ratingBarStars;
    @BindView(R.id.starImageView) ImageView starImageView;
    @BindView(R.id.heart_image_view) ImageView heartImageView;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(OneRecipeCard card) {
      int recipeMainPictureNumber = card.getRecipeMainPictureNumber();
      String recipeName = card.getRecipeName();
      String recipeAuthor = card.getAuthorName();
      int starsCount = card.getStarsCount();
      int favoritesCount = card.getFavoritesCount();
      String starsCountString = String.valueOf(starsCount);
      String favoritesCountString = String.valueOf(favoritesCount);
      Boolean favourite = card.getFavorite();

      Picasso.get().load(BASE_URL + "recipe/photo/" + recipeMainPictureNumber).into(recipeImageView);
      recipeNameTextView.setText(recipeName);
      recipeAuthorTextView.setText(recipeAuthor);
      starsCountTextView.setText(starsCountString);
      ratingBarStars.setRating(starsCount);
      favoritesCountTextView.setText(favoritesCountString);

      Drawable heartBorder = context.getResources().getDrawable(R.mipmap.ic_favorite_border);
      Drawable heartSolid = context.getResources().getDrawable(R.mipmap.ic_favorite);

      if(favourite){
        heartImageView.setImageDrawable(heartSolid);
      }else {
        heartImageView.setImageDrawable(heartBorder);
      }
    }
  }
}
