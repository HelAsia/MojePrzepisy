package com.moje.przepisy.mojeprzepisy.addRecipe.addMainInfo;

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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.addRecipe.addIngredients.AddIngredientsActivity;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import com.moje.przepisy.mojeprzepisy.utils.TimeSetDialog;
import com.moje.przepisy.mojeprzepisy.utils.URLDialog;

public class AddRecipeActivity extends AppCompatActivity implements AddRecipeContract.View,
    View.OnClickListener {
  private static final int MY_CAMERA_PERMISSION_CODE = 100;
  private static final int CAMERA_REQUEST = 1888;
  private static int GALLERY_REQUEST = 1;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  @BindView(R.id.recipeNameEditText) EditText recipeNameEditText;
  @BindView(R.id.mainPhotoImageView) ImageView mainPhotoImageView;
  @BindView(R.id.categoryChooseSpinner) Spinner categoryChooseSpinner;
  @BindView(R.id.preparedTimeEditText) TextView preparedTimeEditText;
  @BindView(R.id.cookTimeEditText) TextView cookTimeEditText;
  @BindView(R.id.bakeTimeEditText) TextView bakeTimeEditText;
  @BindView(R.id.galleryImageView) ImageView galleryImageView;
  @BindView(R.id.cameraImageView) ImageView cameraImageView;
  @BindView(R.id.URLImageView) ImageView URLImageView;
  private AddRecipeContract.Presenter presenter;
  TimeSetDialog timeSetDialog = new TimeSetDialog();
  URLDialog urlDialog = new URLDialog();
  BitmapConverter converter = new BitmapConverter();
  String imgDecodableString;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new AddRecipePresenter(this);

    setListeners();
    setToolbar();
    presenter.setFirstScreen();
  }

  public Context getContext(){
    return context;
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();
    }else if(view.getId() == R.id.nextActionFab){
      if(!presenter.checkIfValueIsEmpty()){
        presenter.setRecipeValueInFile();
        navigateToNextPage();
      }
    }else if(view.getId() == R.id.preparedTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivity.this, preparedTimeEditText);
    }else if(view.getId() == R.id.cookTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivity.this, cookTimeEditText);
    }else if(view.getId() == R.id.bakeTimeEditText){
      timeSetDialog.showDialog(AddRecipeActivity.this, bakeTimeEditText);
    }else if(view.getId() == R.id.galleryImageView){
      loadImageFromGallery();
    }else if(view.getId() == R.id.cameraImageView){
      loadImageFromCamera();
    }else if(view.getId() == R.id.URLImageView){
      urlDialog.showDialog(AddRecipeActivity.this, mainPhotoImageView);
    }
  }

  @Override
  public void setRecipeNameEditText(String recipeName){
    recipeNameEditText.setText(recipeName);
  }

  @Override
  public void setMainPhotoImageView(String bitmapString){
    if (!bitmapString.isEmpty())
    mainPhotoImageView.setImageBitmap(converter.StringToBitMap(bitmapString));
  }

  @Override
  public void setCategoryChooseSpinner(String category){
    ArrayAdapter myAdap = (ArrayAdapter)  categoryChooseSpinner.getAdapter();
    int categorySpinnerPosition = myAdap.getPosition(category);
    categoryChooseSpinner.setSelection(categorySpinnerPosition);
  }

  @Override
  public void setPreparedTimeEditText(String time){
    preparedTimeEditText.setText(time);
  }

  @Override
  public void setCookTimeEditText(String time){
    cookTimeEditText.setText(time);
  }

  @Override
  public void setBakeTimeEditText(String time){
    bakeTimeEditText.setText(time);
  }

  @Override
  public EditText getRecipeNameEditText() {
    return recipeNameEditText;
  }

  @Override
  public ImageView getMainPhotoImageView() {
    return mainPhotoImageView;
  }

  @Override
  public Spinner getCategoryChooseSpinner() {
    return categoryChooseSpinner;
  }

  @Override
  public TextView getPreparedTimeEditText() {
    return preparedTimeEditText;
  }

  @Override
  public TextView getCookTimeEditText() {
    return cookTimeEditText;
  }

  @Override
  public TextView getBakeTimeEditText() {
    return bakeTimeEditText;
  }



  @RequiresApi(api = VERSION_CODES.M)
  @Override
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
      if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK
          && null != data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
            filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        mainPhotoImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

      }else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        mainPhotoImageView.setImageBitmap(photo);
      }else {
        Toast.makeText(this, "Zdjęcie nie zostało wybrane.", Toast.LENGTH_LONG).show();
      }
    }catch (Exception e) {
      Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();
    }
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_recipe);
    toolbar.setSubtitle(R.string.add_recipe_title_step_one);
    setSupportActionBar(toolbar);
  }

  public void setListeners(){
    nextActionFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    preparedTimeEditText.setOnClickListener(this);
    cookTimeEditText.setOnClickListener(this);
    bakeTimeEditText.setOnClickListener(this);
    galleryImageView.setOnClickListener(this);
    cameraImageView.setOnClickListener(this);
    URLImageView.setOnClickListener(this);
  }

  public void navigateToPreviousPage(){
    Intent intent = new Intent (AddRecipeActivity.this, MainCardsActivity.class);
    intent.putExtra("LOGGED",true);
    startActivity(intent);
  }

  public void navigateToNextPage(){
    Intent intent = new Intent (AddRecipeActivity.this, AddIngredientsActivity.class);
    startActivity(intent);
  }
}
