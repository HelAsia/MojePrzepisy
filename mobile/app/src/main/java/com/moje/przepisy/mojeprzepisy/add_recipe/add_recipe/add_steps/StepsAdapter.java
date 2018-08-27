package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Type;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
  public Context context;
  private OnShareClickedListener callbackGallery;
  private OnShareClickedListener callbackCamera;
  private BitmapConverter converter = new BitmapConverter();
  private AddStepContract.View stepView;
  private List<Step> stepList;
  private Gson gson = new Gson();
  private Bitmap bitmap = null;
  private Boolean state = false;
  private static int GALLERY_REQUEST = 1;

  StepsAdapter(Context context, List<Step> stepList){
    this.context = context;
    this.stepList = stepList;
    setHasStableIds(true);
  }

  public void setGalleryOnShareClickedListener(OnShareClickedListener callbackGallery) {
    this.callbackGallery = callbackGallery;
  }

  public void setCameraOnShareClickedListener(OnShareClickedListener callbackCamera) {
    this.callbackCamera = callbackCamera;
  }

  public interface OnShareClickedListener {
    void ShareGalleryClicked(String massage);
    void ShareCameraClicked(String massage);
  }

  public void setBitmap(Bitmap bitmap){
    this.bitmap = bitmap;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setState(Boolean state){
    this.state = state;
  }

  public Boolean getState(){
    return state;
  }

  @NonNull
  @Override
  public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_step_layout, parent, false);
    return new StepsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
    viewHolder.bind(stepList.get(position));

    viewHolder.galleryImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        callbackGallery.ShareGalleryClicked("Gallery");

        while(getBitmap() == null){

        }
        Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show();

        viewHolder.mainPhotoImageView.setImageBitmap(bitmap);

        Step updatedStep = new Step(converter.BitMapToString(bitmap),
            stepList.get(position).getStepNumber(), stepList.get(position).getStepDescription());
        stepList.set(position, updatedStep);

        String pojoJson = convertPojoToJsonString(stepList);
        addPojoListToPreferences(pojoJson, context);
      }
    });
  }


  @Override
  public int getItemCount() {
    return stepList.size();
  }

  @Override
  public long getItemId(int position) {
    return stepList.get(position).getStepId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.stepNumberSpinner) Spinner stepNumberSpinner;
    @BindView(R.id.deleteImageView) ImageView deleteImageView;
    @BindView(R.id.stepEditText) EditText stepEditText;
    @BindView(R.id.mainPhotoImageView) ImageView mainPhotoImageView;
    @BindView(R.id.galleryImageView) ImageView galleryImageView;
    @BindView(R.id.cameraImageView) ImageView cameraImageView;
    @BindView(R.id.URLImageView) ImageView URLImageView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);

      deleteImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          stepList.remove(getAdapterPosition());
          notifyItemRemoved(getAdapterPosition());
          String pojoJson = convertPojoToJsonString(stepList);
          addPojoListToPreferences(pojoJson, context);
        }
      });


      stepNumberSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
          int stepNumber = (int) adapterView.getSelectedItemId();
          Step updatedStep = new Step(stepList.get(getAdapterPosition()).getPhotoId(),
              stepNumber + 1, stepList.get(getAdapterPosition()).getStepDescription());
          stepList.set(getAdapterPosition(), updatedStep);

          String pojoJson = convertPojoToJsonString(stepList);
          addPojoListToPreferences(pojoJson, context);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
      });

      stepEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          String stepDescription = stepEditText.getText().toString();
          Step updatedStep = new Step(stepList.get(getAdapterPosition()).getPhotoId(),
              stepList.get(getAdapterPosition()).getStepNumber(), stepDescription);
          stepList.set(getAdapterPosition(), updatedStep);

          String pojoJson = convertPojoToJsonString(stepList);
          addPojoListToPreferences(pojoJson, context);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
      });

/*      galleryImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          callbackGallery.ShareGalleryClicked("Gallery");

          while(getBitmap() == null){

          }

          mainPhotoImageView.setImageBitmap(bitmap);

          Step updatedStep = new Step(converter.BitMapToString(bitmap),
              stepList.get(getAdapterPosition()).getStepNumber(), stepList.get(getAdapterPosition()).getStepDescription());
          stepList.set(getAdapterPosition(), updatedStep);

          String pojoJson = convertPojoToJsonString(stepList);
          addPojoListToPreferences(pojoJson, context);

        }
      });*/

      cameraImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          callbackCamera.ShareCameraClicked("Camera");

          while(!state){

          }

          mainPhotoImageView.setImageBitmap(bitmap);

          Step updatedStep = new Step(converter.BitMapToString(bitmap),
              stepList.get(getAdapterPosition()).getStepNumber(), stepList.get(getAdapterPosition()).getStepDescription());
          stepList.set(getAdapterPosition(), updatedStep);

          String pojoJson = convertPojoToJsonString(stepList);
          addPojoListToPreferences(pojoJson, context);
        }
      });

      URLImageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
        }
      });
    }


    void bind(Step step) {
      int stepNumber = step.getStepNumber();
      String stepDescription = step.getStepDescription();
      String mainPhoto = step.getPhotoId();

      stepNumberSpinner.setSelection(stepNumber);
      stepEditText.setText(stepDescription);
      Picasso.get().load(mainPhoto).into(mainPhotoImageView);
    }
  }

  private String convertPojoToJsonString(List<Step> stepList) {
    Type type = new TypeToken<List<Step>>(){}.getType();
    return gson.toJson(stepList, type);
  }

  private void addPojoListToPreferences(String jsonList, Context context) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(Constant.PREF_STEP, jsonList).apply();
    editor.commit();
  }
}
