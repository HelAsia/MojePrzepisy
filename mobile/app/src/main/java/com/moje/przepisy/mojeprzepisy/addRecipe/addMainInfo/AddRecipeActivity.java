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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

public class AddRecipeActivity extends AppCompatActivity implements AddRecipeContract.View{
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
  @BindView(R.id.toolbar_add_recipe) Toolbar recipeToolbar;
  private AddRecipeContract.Presenter presenter;
  private TimeSetDialog timeSetDialog = new TimeSetDialog();
  private URLDialog urlDialog = new URLDialog();
  private BitmapConverter converter = new BitmapConverter();
  private Context context;

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_recipe);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new AddRecipePresenter(this);

    setToolbar();
    presenter.setFirstScreen();
    setFabListeners();
    setTimeListeners();
    setImageListeners();

  }

  public Context getContext(){
    return context;
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
    ArrayAdapter myAdapter = (ArrayAdapter)  categoryChooseSpinner.getAdapter();
    int categorySpinnerPosition = myAdapter.getPosition(category);
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

  public Boolean checkIfValueIsEmpty() {
    if (recipeNameEditText.getText().toString().equals("")){
      recipeNameEditText.setHintTextColor(Color.parseColor("#ff3300"));
      recipeNameEditText.setHint("Brak nazwy! Uzypełnij nazwę.");
      return true;
    }if(preparedTimeEditText.getText().toString().equals(getResources().getString(R.string.first_time_pattern))){
      preparedTimeEditText.setText(getResources().getString(R.string.first_time_setting));
    }if(cookTimeEditText.getText().toString().equals(getResources().getString(R.string.first_time_pattern))){
      cookTimeEditText.setText(getResources().getString(R.string.first_time_setting));
    }if (bakeTimeEditText.getText().toString().equals(getResources().getString(R.string.first_time_pattern))){
      bakeTimeEditText.setText(getResources().getString(R.string.first_time_setting));
    }
    return false;
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
    if(ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
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
        String imgDecodableString = cursor.getString(columnIndex);
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

  private void setToolbar() {
    recipeToolbar.setSubtitle(R.string.add_recipe_title_step_one);
    setSupportActionBar(recipeToolbar);
  }

  @RequiresApi(api = VERSION_CODES.M)
  private void setFabListeners(){
    previousActionFab.setOnClickListener(v -> navigateToPreviousPage());
    nextActionFab.setOnClickListener(v -> {
      if(!checkIfValueIsEmpty()){
        presenter.setRecipeValueInFile();
        navigateToNextPage();
      }
    });
  }

  @RequiresApi(api = VERSION_CODES.M)
  private void setTimeListeners(){
    preparedTimeEditText.setOnClickListener(v -> timeSetDialog
            .showDialog(AddRecipeActivity.this, preparedTimeEditText));
    cookTimeEditText.setOnClickListener(v -> timeSetDialog
            .showDialog(AddRecipeActivity.this, cookTimeEditText));
    bakeTimeEditText.setOnClickListener(v -> timeSetDialog
            .showDialog(AddRecipeActivity.this, bakeTimeEditText));
  }

  @RequiresApi(api = VERSION_CODES.M)
  private void setImageListeners(){
    galleryImageView.setOnClickListener(v -> loadImageFromGallery());
    cameraImageView.setOnClickListener(v -> loadImageFromCamera());
    URLImageView.setOnClickListener(v -> urlDialog
            .showDialog(AddRecipeActivity.this, mainPhotoImageView));
  }

  private void navigateToPreviousPage(){
    Intent intent = new Intent (AddRecipeActivity.this, MainCardsActivity.class);
    intent.putExtra("isLogged",true);
    startActivity(intent);
  }

  private void navigateToNextPage(){
    Intent intent = new Intent (AddRecipeActivity.this, AddIngredientsActivity.class);
    startActivity(intent);
  }

  public String getRecipeName(){
    return recipeNameEditText.getText().toString();
  }

  public String getMainPhoto(){
    BitmapDrawable drawable = (BitmapDrawable) mainPhotoImageView.getDrawable();
    Bitmap bitmap = drawable.getBitmap();
    return converter.BitMapToString(bitmap);
  }

  public String getRecipeCategory(){
    return (String) categoryChooseSpinner.getSelectedItem();
  }

  public String getPrepareTime(){
    return preparedTimeEditText.getText().toString();
  }

  public String getCookTime(){
    return cookTimeEditText.getText().toString();
  }

  public String getBakeTime(){
    return bakeTimeEditText.getText().toString();
  }
}
