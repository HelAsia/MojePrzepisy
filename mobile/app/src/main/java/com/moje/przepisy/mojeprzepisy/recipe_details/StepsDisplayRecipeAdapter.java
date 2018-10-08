package com.moje.przepisy.mojeprzepisy.recipe_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import java.util.List;

public class StepsDisplayRecipeAdapter extends RecyclerView.Adapter<StepsDisplayRecipeAdapter.ViewHolder> {
  public Context context;
  private List<Step> stepList;
  private BitmapConverter converter = new BitmapConverter();

  StepsDisplayRecipeAdapter(Context context, List<Step> stepList){
    this.context = context;
    this.stepList = stepList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public StepsDisplayRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_step_display_layout, parent, false);
    return new StepsDisplayRecipeAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull StepsDisplayRecipeAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(stepList.get(position));
  }

  @Override
  public int getItemCount()  {
    return stepList.size();
  }

  @Override
  public long getItemId(int position) {
    return stepList.get(position).getStepId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.stepNumberTextView) TextView stepNumberTextView;
    @BindView(R.id.stepDescriptionTextView) TextView stepDescriptionTextView;
    @BindView(R.id.stepImageView) ImageView stepImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Step step) {
      int stepNumber = step.getStepNumber();
      String stepDescription = step.getStepDescription();
      String photoId = step.getPhoto();

      if (stepNumber != 0) {
        stepNumberTextView.setText(Integer.toString(stepNumber));
      }else{
        stepNumberTextView.setText("0");
      }
      if(stepDescription != null){
        stepDescriptionTextView.setText(stepDescription);
      }else{
        stepDescriptionTextView.setText("Brak opisu");
      }
      if(photoId == null || photoId.equals("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg")){
        stepImageView.setVisibility(View.GONE);
      }else {
        stepImageView.setImageBitmap(converter.StringToBitMap(photoId));
      }
    }
  }
}