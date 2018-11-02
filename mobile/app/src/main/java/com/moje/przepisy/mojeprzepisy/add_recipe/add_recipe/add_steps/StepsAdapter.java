package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.moje.przepisy.mojeprzepisy.utils.URLDialog;
import java.lang.reflect.Type;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
  public Context context;
  private OnShareClickedListener callbackGallery;
  private OnShareClickedListener callbackCamera;
  private BitmapConverter converter = new BitmapConverter();
  private List<Step> stepList;
  private Gson gson = new Gson();

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
        new BackgroundGalleryAction(((AddStepsActivityView)context), viewHolder.mainPhotoImageView, position).execute();
      }
    });

    viewHolder.cameraImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        new BackgroundCameraAction(((AddStepsActivityView)context), viewHolder.mainPhotoImageView, position).execute();
      }
    });

    viewHolder.URLImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        new BackgroundUrlImageAction(((AddStepsActivityView)context), viewHolder.mainPhotoImageView, position).execute();
      }
    });

    viewHolder.stepNumberSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int positionInSpinner, long id) {
        stepList.get(position).setStepNumber(position + 1);

        String pojoJson = convertPojoToJsonString(stepList);
        addPojoListToPreferences(pojoJson, context);
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {
        String pojoJson = convertPojoToJsonString(stepList);
        addPojoListToPreferences(pojoJson, context);
      }
    });

    viewHolder.deleteImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        stepList.remove(position);
        notifyItemRemoved(position);
        String pojoJson = convertPojoToJsonString(stepList);
        addPojoListToPreferences(pojoJson, context);
      }
    });

    viewHolder.stepEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String stepDescription = viewHolder.stepEditText.getText().toString();

        Step updatedStep = stepList.get(position);
        updatedStep.setStepDescription(stepDescription);

        String pojoJson = convertPojoToJsonString(stepList);
        addPojoListToPreferences(pojoJson, context);
      }
      @Override
      public void afterTextChanged(Editable editable) {
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
    }

    void bind(Step step) {
      int stepNumber = step.getStepNumber();
      String stepDescription = step.getStepDescription();
      String mainPhoto = step.getPhoto();

      if(mainPhoto == null){
        mainPhotoImageView.setVisibility(View.GONE);
      }else {
        mainPhotoImageView.setVisibility(View.VISIBLE);
        Bitmap photoAfterConversion = converter.StringToBitMap(mainPhoto);
        mainPhotoImageView.setImageBitmap(photoAfterConversion);
      }
      stepNumberSpinner.setSelection(stepNumber);
      stepEditText.setText(stepDescription);
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

  private class BackgroundUrlImageAction extends AsyncTask<Void, Void, Void> {
    private Activity activity;
    private ImageView imageView;
    private int position;
    private URLDialog urlDialog = new URLDialog();


    public BackgroundUrlImageAction(Activity activity, ImageView imageView, int position) {
      this.activity = activity;
      this.imageView = imageView;
      this.position = position;
    }

    @Override
    protected void onPreExecute() {
      urlDialog.showDialog(activity, imageView);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        while (imageView.getVisibility()!= View.VISIBLE){
        }

        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;

    }

    @Override
    protected void onPostExecute(Void result) {
      BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
      Bitmap picture = drawable.getBitmap();

      stepList.get(position).setPhoto(converter.BitMapToString(picture));

      String pojoJson = convertPojoToJsonString(stepList);
      addPojoListToPreferences(pojoJson, context);

      Toast.makeText(activity, "DODANE", Toast.LENGTH_SHORT).show();
    }
  }

  private class BackgroundGalleryAction extends AsyncTask<Void, Void, Void> {
    private Activity activity;
    private ImageView imageView;
    int position;

    public BackgroundGalleryAction(Activity activity, ImageView imageView, int position) {
      this.activity = activity;
      this.imageView = imageView;
      this.position = position;
    }

    @Override
    protected void onPreExecute() {
      callbackGallery.ShareGalleryClicked("Gallery");
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        while (((AddStepsActivityView)context).getPicture() == null){

        }

        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      Bitmap pictureFromActivity =((AddStepsActivityView)context).getPicture();
      imageView.setImageBitmap(pictureFromActivity);
      imageView.setVisibility(View.VISIBLE);

      BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
      Bitmap picture = drawable.getBitmap();

      stepList.get(position).setPhoto(converter.BitMapToString(picture));

      String pojoJson = convertPojoToJsonString(stepList);
      addPojoListToPreferences(pojoJson, context);

      ((AddStepsActivityView)context).setPicture(null);

      Toast.makeText(activity, "DODANE", Toast.LENGTH_SHORT).show();
    }
  }

  private class BackgroundCameraAction extends AsyncTask<Void, Void, Void> {
    private Activity activity;
    private ImageView imageView;
    int position;


    public BackgroundCameraAction(Activity activity, ImageView imageView, int position) {
      this.activity = activity;
      this.imageView = imageView;
      this.position = position;
    }

    @Override
    protected void onPreExecute() {
      callbackCamera.ShareCameraClicked("Camera");
    }

    @Override
    protected Void doInBackground(Void... arg0) {
      try {
        while (((AddStepsActivityView)context).getPicture() == null){
        }

        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      Bitmap pictureFromActivity =((AddStepsActivityView)context).getPicture();
      imageView.setImageBitmap(pictureFromActivity);
      imageView.setVisibility(View.VISIBLE);

      BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
      Bitmap picture = drawable.getBitmap();

      stepList.get(position).setPhoto(converter.BitMapToString(picture));

      String pojoJson = convertPojoToJsonString(stepList);
      addPojoListToPreferences(pojoJson, context);

      ((AddStepsActivityView)context).setPicture(null);

      Toast.makeText(activity, "DODANE", Toast.LENGTH_SHORT).show();
    }
  }
}
