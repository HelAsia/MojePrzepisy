package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.util.List;

public class AddStepsActivityView extends AppCompatActivity implements AddStepContract.View,
    View.OnClickListener, StepsAdapter.OnShareClickedListener {
  private static final int MY_CAMERA_PERMISSION_CODE = 100;
  private static final int CAMERA_REQUEST = 1888;
  private static int GALLERY_REQUEST = 1;
  @BindView(R.id.addStepFab) FloatingActionButton addStepFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  private AddStepContract.Presenter presenter;
  String imgDecodableString;
  Context context;
  public Bitmap picture = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_steps_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddStepPresenter(this);

    setListeners();
    setToolbar();
    presenter.setFirstScreen();
  }

  @Override
  public void setRecyclerView(List<Step> stepList) {
    StepsAdapter adapter = new StepsAdapter(this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    adapter.setGalleryOnShareClickedListener(this);
    adapter.setCameraOnShareClickedListener(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public Context getContext() {
    return context;
  }

  public Bitmap getPicture() {
    return picture;
  }

  public void setPicture(Bitmap picture) {
    this.picture = picture;
  }

  @Override
  public void setListeners(){
    addStepFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addStepFab){
      presenter.setNextStep();

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if(view.getId() == R.id.nextActionFab) {
      navigateToNextPage();
    }
  }

  @RequiresApi(api = VERSION_CODES.M)

  public void loadImageFromCamera() {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
      requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
    }else {
      Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == MY_CAMERA_PERMISSION_CODE) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Intent cameraIntent = new
            Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
      } else {
        Toast.makeText(this, "Brak pozwolenia na użycie aparatu.", Toast.LENGTH_LONG).show();
      }
    }else if(requestCode == GALLERY_REQUEST){
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
      }else {
        Toast.makeText(this, "Brak pozwolenia na użycie galerii.", Toast.LENGTH_LONG).show();
      }
    }
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void loadImageFromGallery() {
    if(ContextCompat.checkSelfPermission(context, permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
      requestPermissions(new String[]{permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST);
    }else {
      Intent galleryIntent = new Intent(Intent.ACTION_PICK,
          Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    try{
      if(requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK
          && null != data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
            filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        picture = BitmapFactory.decodeFile(imgDecodableString);

      }else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
        picture = (Bitmap) data.getExtras().get("data");

      }else {
        Toast.makeText(this, "Zdjęcie nie zostało wybrane.", Toast.LENGTH_LONG).show();
      }
    }catch (Exception e) {
      Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_step);
    toolbar.setSubtitle(R.string.add_recipe_title_step_three);
    setSupportActionBar(toolbar);
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent(AddStepsActivityView.this, AddIngredientsActivityView.class);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent(AddStepsActivityView.this, DisplayAllRecipeElementsActivityView.class);
    startActivity(intent);
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void ShareGalleryClicked(String massage) {
    loadImageFromGallery();
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void ShareCameraClicked(String massage) {
    loadImageFromCamera();
  }
}
