package com.moje.przepisy.mojeprzepisy.ui;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION_CODES;
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
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.OneRecipeCard;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
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
  private BitmapConverter converter = new BitmapConverter();

  MyCardViewAdapter(Context context, List<OneRecipeCard> cardsList) {
    this.context = context;
    this.cardsList = cardsList;
    setHasStableIds(true);
  }

  public void setHeartOnShareClickedListener(OnShareHeartClickedListener callbackHeart) {
    this.callbackHeart = callbackHeart;
  }

  public interface OnShareHeartClickedListener {
    void shareHeartClicked(int recipeId, int favorite);
  }

  public void setStarsOnShareClickedListener(OnShareStarsClickedListener callbackStars) {
    this.callbackStars = callbackStars;
  }

  public interface OnShareStarsClickedListener {
    void shareStarsClicked(int recipeId, int starRate);
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
        callbackStars.shareStarsClicked(recipeId, rate);
      }
    });

    viewHolder.heartImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(context, "KLIKNĘTE", Toast.LENGTH_SHORT).show();
        int recipeId = (int)getItemId(position);
        Drawable heartOnCard = viewHolder.heartImageView.getDrawable();
        Drawable heartBorder = context.getResources().getDrawable(R.mipmap.ic_favorite_border);
        Drawable heartSolid = context.getResources().getDrawable(R.mipmap.ic_favorite);

        Bitmap heartOnCardB = ((BitmapDrawable) viewHolder.heartImageView.getDrawable()).getBitmap();
        Bitmap heartBorderB = ((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_favorite_border)).getBitmap();
        Bitmap heartSolidB = ((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_favorite)).getBitmap();

        ConstantState constHeartOnCard = heartOnCard.getConstantState();
        ConstantState constHeartBorder = heartBorder.getConstantState();
        ConstantState constHeartSolid = heartSolid.getConstantState();

        Boolean isPressed = viewHolder.heartImageView.isPressed();

        if(isPressed){
          viewHolder.heartImageView.setImageDrawable(heartSolid);
          callbackHeart.shareHeartClicked(recipeId, 1);
        }else {
          viewHolder.heartImageView.setImageDrawable(heartBorder);
          callbackHeart.shareHeartClicked(recipeId, 0);
        }

        /*if(constHeartOnCard.equals(constHeartBorder)){
          viewHolder.heartImageView.setImageDrawable(heartSolid);
          callbackHeart.shareHeartClicked(recipeId, false);
        }else if(constHeartOnCard.equals(constHeartSolid)){
          viewHolder.heartImageView.setImageDrawable(heartBorder);
          callbackHeart.shareHeartClicked(recipeId, true);
        }*/
      }
    });

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
      String recipeMainPicture = card.getRecipeMainPicture();
      Bitmap recipeMainPictureBitmap = converter.StringToBitMap(recipeMainPicture);
      String recipeName = card.getRecipeName();
      String recipeAuthor = card.getAuthorName();
      int starsCount = card.getStarsCount();
      int favoritesCount = card.getFavoritesCount();
      String starsCountString = String.valueOf(starsCount);
      String favoritesCountString = String.valueOf(favoritesCount);

      recipeImageView.setImageBitmap(recipeMainPictureBitmap);
      recipeNameTextView.setText(recipeName);
      recipeAuthorTextView.setText(recipeAuthor);
      starsCountTextView.setText(starsCountString);
      ratingBarStars.setRating(starsCount);
      favoritesCountTextView.setText(favoritesCountString);

    }
  }
}
