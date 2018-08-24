package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_steps;

import android.Manifest;
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
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_ingredients.AddIngredientsActivityView;
import com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.display_all_recipe_elements.DisplayAllRecipeElementsActivityView;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.utils.BitmapConverter;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class AddStepsActivityView extends AppCompatActivity implements AddStepContract.View,
    View.OnClickListener {
  private static final int MY_CAMERA_PERMISSION_CODE = 100;
  private static final int CAMERA_REQUEST = 1888;
  private static int RESULT_LOAD_IMG = 1;
  @BindView(R.id.addStepFab) FloatingActionButton addStepFab;
  @BindView(R.id.previousActionFab) FloatingActionButton previousActionFab;
  @BindView(R.id.nextActionFab) FloatingActionButton nextActionFab;
  List<Step> stepList = new ArrayList<>();
  private AddStepContract.Presenter presenter;
  StepsAdapter mainAdapter;
  public Bitmap testBitmap;
  String imgDecodableString;
  int stepNumber = -1;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_steps_view);
    context = getApplicationContext();
    ButterKnife.bind(this);

    presenter = new AddStepPresenter(this, new RecipeRepository(context));

    addStepFab.setOnClickListener(this);
    previousActionFab.setOnClickListener(this);
    nextActionFab.setOnClickListener(this);

    setToolbar();

    stepList = presenter.getStepListAfterChangeScreen(presenter.getPojoListFromPreferences(context));

    if(stepList != null){
      presenter.setStepList(stepList);
      setRecyclerView(presenter.getStepList());
    }else {
      Step emptyStep = new Step("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", 0, "Opis kroku");
      presenter.getStepList().add(emptyStep);
      presenter.setStepList(presenter.getStepList());
      setRecyclerView(presenter.getStepList());
    }
  }

  @Override
  public void setRecyclerView(List<Step> stepList) {
    StepsAdapter adapter = new StepsAdapter(this, stepList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addStepsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.addStepFab){

      Step emptyStep = new Step("https://img.freepik.com/free-icon/gallery_318-131678.jpg?size=338c&ext=jpg", stepNumber + 1, "Opis kroku");
      this.stepNumber = stepNumber + 1;

      presenter.getStepList().add(emptyStep);
      presenter.setStepList(presenter.getStepList());
      setRecyclerView(presenter.getStepList());

      String pojoToJson = presenter.convertPojoToJsonString(presenter.getStepList());
      presenter.addPojoListToPreferences(pojoToJson, context);

    }else if(view.getId() == R.id.previousActionFab){
      navigateToPreviousPage();

    }else if((view.getId() == R.id.nextActionFab)) {
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
    }
  }

  public void loadImageFromGallery() {
    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
        Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
  }

  public Bitmap getTestBitmap(){
    return testBitmap;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    try{
      if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
          && null != data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
            filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();

        testBitmap = BitmapFactory.decodeFile(imgDecodableString);

      }else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        testBitmap = photo;
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

}
