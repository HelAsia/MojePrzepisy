package com.moje.przepisy.mojeprzepisy.categorySearch;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.ViewHolder>{
  public Context context;
  private List<String> categoryNameList;
  private OnShareClickedListener callbackCategory;

  CategorySearchAdapter(Context context, List<String> categoryNameList){
    this.context = context;
    this.categoryNameList = categoryNameList;
    setHasStableIds(true);
  }

  public void setCategoryOnShareClickedListener(CategorySearchAdapter.OnShareClickedListener callbackCategory) {
    this.callbackCategory = callbackCategory;
  }

  public interface OnShareClickedListener {
    void ShareCategoryClicked();
  }

  @NonNull
  @Override
  public CategorySearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_grid_category_layout, parent, false);
    return new CategorySearchAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull final CategorySearchAdapter.ViewHolder viewHolder, final int position) {
    viewHolder.bind(categoryNameList.get(position));

    viewHolder.categoryImage.setOnClickListener(view -> {
      setSortedMethod();
      String testingCategory = categoryNameList.get(position);
      setCategoryNameInPreferences(testingCategory);
      callbackCategory.ShareCategoryClicked();
    });
  }

  @Override
  public int getItemCount() {
    return categoryNameList.size();
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  private void setSortedMethod(){
    SharedPreferences.Editor categorySetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    categorySetting.putString(Constant.PREF_SORTED_METHOD, "category").apply();
    categorySetting.commit();
  }

  private void setCategoryNameInPreferences(String category){
    SharedPreferences.Editor sortingSetting = PreferenceManager.getDefaultSharedPreferences(context).edit();
    sortingSetting.putString(Constant.PREF_CATEGORY, category).apply();
    sortingSetting.commit();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view_category_name) TextView categoryName;
    @BindView(R.id.category_image) ImageView categoryImage;
    @BindView(R.id.my_one_card_gridview_layout) CardView test;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(String oneCategoryName) {
      categoryName.setText(oneCategoryName);
      int photoNumber = (int)getItemId()+ 1;
      Glide.with(context).load(BASE_URL + "recipe/photo/" + photoNumber).into(categoryImage);
    }
  }

}