package com.moje.przepisy.mojeprzepisy.addRecipe.displayRecipe;

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

public class DisplayStepsAdapter extends RecyclerView.Adapter<DisplayStepsAdapter.ViewHolder> {
  public Context context;
  private List<Step> stepList;
  private BitmapConverter converter = new BitmapConverter();

  DisplayStepsAdapter(Context context, List<Step> stepList){
    this.context = context;
    this.stepList = stepList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public DisplayStepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_step_display_layout, parent, false);
    return new DisplayStepsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull DisplayStepsAdapter.ViewHolder viewHolder, int position) {
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

      stepNumberTextView.setText(Integer.toString(stepNumber));
      stepDescriptionTextView.setText(stepDescription);
      if(photoId == null || photoId.equals("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg")){
        stepImageView.setVisibility(View.GONE);
      }
      stepImageView.setImageBitmap(converter.StringToBitMap(photoId));
    }
  }
}
