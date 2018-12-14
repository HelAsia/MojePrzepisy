package com.moje.przepisy.mojeprzepisy.userProfile;

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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.utils.URLDialog;

public class UserProfileActivity extends AppCompatActivity implements UserProfileContract.View,
        OnClickListener {
  @BindView(R.id.mainUserPhotoImageView) ImageView mainUserPhotoImageView;
  @BindView(R.id.galleryImageView) ImageView galleryImageView;
  @BindView(R.id.cameraImageView) ImageView cameraImageView;
  @BindView(R.id.URLImageView) ImageView URLImageView;
  @BindView(R.id.saveUserPictureImageView) ImageView saveUserPictureImageView;
  @BindView(R.id.saveNameImageView) ImageView saveNameImageView;
  @BindView(R.id.saveLastNameImageView) ImageView saveLastNameImageView;
  @BindView(R.id.saveEmailImageView) ImageView saveEmailImageView;
  @BindView(R.id.savePasswordImageView) ImageView savePasswordImageView;
  @BindView(R.id.editPasswordImageView) ImageView editPasswordImageView;
  @BindView(R.id.editLoginTextView) TextView editLoginTextView;
  @BindView(R.id.editNameEditText) EditText editNameEditText;
  @BindView(R.id.editLastNameEditText) EditText editLastNameEditText;
  @BindView(R.id.editEmailEditText) EditText editEmailEditText;
  @BindView(R.id.editPasswordEditText) EditText editPasswordEditText;
  @BindView(R.id.editRepeatPasswordEditText) EditText editRepeatPasswordEditText;
  @BindView(R.id.anyErrorMessageTextView) TextView anyErrorMessageTextView;
  @BindView(R.id.editRepeatPasswordLinearLayout) LinearLayout editRepeatPasswordLinearLayout;
  private static final int MY_CAMERA_PERMISSION_CODE = 100;
  private static final int CAMERA_REQUEST = 1888;
  private static int GALLERY_REQUEST = 1;
  private UserProfileContract.Presenter presenter;
  URLDialog urlDialog = new URLDialog();
  String imgDecodableString;
  Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_view);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new UserProfilePresenter(this, new UserRepository(context));

    presenter.getUserData();
    setOnClickListeners();
    setToolbar();
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.saveUserPictureImageView){
      presenter.sendImageDataToServer(mainUserPhotoImageView);
    }else if(view.getId() == R.id.galleryImageView){
      loadImageFromGallery();
    }else if(view.getId() == R.id.cameraImageView){
      loadImageFromCamera();
    }else if(view.getId() == R.id.URLImageView){
      urlDialog.showDialog(UserProfileActivity.this, mainUserPhotoImageView);
    }else if(view.getId() == R.id.saveNameImageView){
      presenter.sendEditDataToServer("first_name", editNameEditText);
    }else if(view.getId() == R.id.saveLastNameImageView){
      presenter.sendEditDataToServer("last_name", editLastNameEditText);
    }else if(view.getId() == R.id.saveEmailImageView){
      presenter.sendEditDataToServer("email", editEmailEditText);
    }else if(view.getId() == R.id.savePasswordImageView){
      presenter.sendEditDataToServer("user_password", editPasswordEditText);
    }else if(view.getId() == R.id.editPasswordImageView){
      presenter.showRepeatPassword(getEditRepeatPasswordLinearLayout());
    }
  }

  @Override
  public Context getContext() {
    return context;
  }

  @Override
  public void setOnClickListeners() {
    galleryImageView.setOnClickListener(this);
    cameraImageView.setOnClickListener(this);
    URLImageView.setOnClickListener(this);
    saveUserPictureImageView.setOnClickListener(this);
    saveNameImageView.setOnClickListener(this);
    saveLastNameImageView.setOnClickListener(this);
    saveEmailImageView.setOnClickListener(this);
    savePasswordImageView.setOnClickListener(this);
    editPasswordImageView.setOnClickListener(this);
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
        mainUserPhotoImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

      }else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        mainUserPhotoImageView.setImageBitmap(photo);
      }else {
        Toast.makeText(this, "Zdjęcie nie zostało wybrane.", Toast.LENGTH_LONG).show();
      }
    }catch (Exception e) {
      Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public ImageView getMainUserPhotoImageView() {
    return mainUserPhotoImageView;
  }

  @Override
  public TextView getLoginTextView() {
    return editLoginTextView;
  }

  @Override
  public EditText getNameEditText() {
    return editNameEditText;
  }

  @Override
  public ImageView getSaveNameImageView() {
    return saveNameImageView;
  }

  @Override
  public EditText getLastNameEditText() {
    return editLastNameEditText;
  }

  @Override
  public ImageView getSaveLastNameImageView() {
    return saveLastNameImageView;
  }

  @Override
  public EditText getEmailEditText() {
    return editEmailEditText;
  }

  @Override
  public ImageView getEmailNameImageView() {
    return saveEmailImageView;
  }

  @Override
  public EditText getPasswordEditText() {
    return editPasswordEditText;
  }

  @Override
  public ImageView getSavePasswordImageView() {
    return savePasswordImageView;
  }

  @Override
  public EditText getRepeatPasswordEditText() {
    return editRepeatPasswordEditText;
  }

  @Override
  public ImageView getEditPasswordImageView() {
    return editPasswordImageView;
  }

  @Override
  public TextView getErrorMessageTextView() {
    return anyErrorMessageTextView;
  }

  @Override
  public LinearLayout getEditRepeatPasswordLinearLayout() {
    return editRepeatPasswordLinearLayout;
  }

  public void setToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_profile);
    toolbar.setSubtitle(R.string.user_profile);
    setSupportActionBar(toolbar);
  }
}
