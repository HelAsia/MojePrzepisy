package com.moje.przepisy.mojeprzepisy.recipeDetails.stepsDetails;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

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

import com.bumptech.glide.Glide;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.squareup.picasso.Picasso;
import java.util.List;

public class StepsDisplayAdapter extends RecyclerView.Adapter<StepsDisplayAdapter.ViewHolder> {
  public Context context;
  private List<Step> stepList;

  StepsDisplayAdapter(Context context, List<Step> stepList){
    this.context = context;
    this.stepList = stepList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public StepsDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_step_display_layout, parent, false);
    return new StepsDisplayAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull StepsDisplayAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(stepList.get(position));
  }

  @Override
  public int getItemCount()  {
    return stepList.size();
  }

  @Override
  public long getItemId(int position) {
    return stepList.get(position).getId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.stepNameTextView) TextView stepNumberTextView;
    @BindView(R.id.stepDescriptionTextView) TextView stepDescriptionTextView;
    @BindView(R.id.stepImageView) ImageView stepImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Step step) {
      int stepNumber = step.getStepNumber();
      String stepDescription = step.getStepDescription();
      int photo = step.getPhotoNumber();

      if (stepNumber != 0) {
        stepNumberTextView.setText(Integer.toString(stepNumber) + ".  ");
      }else{
        stepNumberTextView.setText("0");
      }
      if(stepDescription != null){
        stepDescriptionTextView.setText(stepDescription);
      }else{
        stepDescriptionTextView.setText("Brak opisu");
      }
      if(photo == -1){
        stepImageView.setVisibility(View.GONE);
      }else {
        stepImageView.setVisibility(View.VISIBLE);
        Glide.with(context).load(BASE_URL + "recipe/photo/" + photo).into(stepImageView);
      }
    }
  }
}
