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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.user.UserRepository;
import com.moje.przepisy.mojeprzepisy.mainCards.MainCardsActivity;
import com.moje.przepisy.mojeprzepisy.setting.SettingsActivity;
import com.moje.przepisy.mojeprzepisy.utils.Constant;
import com.moje.przepisy.mojeprzepisy.utils.URLDialog;
import com.squareup.picasso.Picasso;

import static com.moje.przepisy.mojeprzepisy.utils.Constant.BASE_URL;

public class UserProfileActivity extends AppCompatActivity implements UserProfileContract.View{
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
  @BindView(R.id.toolbar_user_profile) Toolbar toolbar;
  private UserProfileContract.Presenter presenter;
  private URLDialog urlDialog = new URLDialog();
  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile_view);
    ButterKnife.bind(this);
    context = getApplicationContext();

    presenter = new UserProfilePresenter(this, new UserRepository(context));
    presenter.setFirstScreen();
  }

  public void setToolbar() {
    toolbar.setSubtitle(R.string.user_profile);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if(actionbar != null){
      actionbar.setDisplayHomeAsUpEnabled(true);
      actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
  }

  public boolean onOptionsItemSelected(MenuItem item){
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", true);
    startActivity(intent);
    UserProfileActivity.this.finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainCardsActivity.class);
    intent.putExtra("isLogged", true);
    startActivity(intent);
    UserProfileActivity.this.finish();
  }

  @Override
  public Context getContext() {
    return context;
  }

  @RequiresApi(api = VERSION_CODES.M)
  @Override
  public void setOnClickListeners() {
    galleryImageView.setOnClickListener(view ->
            loadImageFromGallery());
    cameraImageView.setOnClickListener(view ->
            loadImageFromCamera());
    URLImageView.setOnClickListener(view ->
            urlDialog.showDialog(UserProfileActivity.this, mainUserPhotoImageView));
    saveUserPictureImageView.setOnClickListener(view ->
            presenter.sendImageDataToServer(mainUserPhotoImageView));
    saveNameImageView.setOnClickListener(view ->
            presenter.sendEditDataToServer("first_name"));
    saveLastNameImageView.setOnClickListener(view ->
        presenter.sendEditDataToServer("last_name"));
    saveEmailImageView.setOnClickListener(view ->
        presenter.sendEditDataToServer("email"));
    savePasswordImageView.setOnClickListener(view ->
        presenter.sendEditDataToServer("user_password"));
    editPasswordImageView.setOnClickListener(view ->
        setRepeatLayoutVisible());
  }

  @Override
  public void setToastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show();
  }

  @RequiresApi(api = VERSION_CODES.M)
  private void loadImageFromCamera() {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
      requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.CAMERA_REQUEST);
    }else {
      Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(cameraIntent, Constant.CAMERA_REQUEST);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == Constant.CAMERA_REQUEST) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Intent cameraIntent = new
            Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constant.CAMERA_REQUEST);
      } else {
        Toast.makeText(this, "Brak pozwolenia na użycie aparatu.", Toast.LENGTH_LONG).show();
      }
    }else if(requestCode == Constant.GALLERY_REQUEST){
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, Constant.GALLERY_REQUEST);
      }else {
        Toast.makeText(this, "Brak pozwolenia na użycie galerii.", Toast.LENGTH_LONG).show();
      }
    }
  }

  @RequiresApi(api = VERSION_CODES.M)
  private void loadImageFromGallery() {
    if(ContextCompat.checkSelfPermission(context, permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
      requestPermissions(new String[]{permission.READ_EXTERNAL_STORAGE}, Constant.GALLERY_REQUEST);
    }else {
      Intent galleryIntent = new Intent(Intent.ACTION_PICK,
          Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(galleryIntent, Constant.GALLERY_REQUEST);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    try{
      if(requestCode == Constant.GALLERY_REQUEST && resultCode == RESULT_OK
          && null != data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
            filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        mainUserPhotoImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

      }else if(requestCode == Constant.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
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
  public void setMainUserPhotoImageView(String photo) {
    Glide.with(context).load(photo).into(mainUserPhotoImageView);
  }

  @Override
  public void setLoginTextView(String login) {
    editLoginTextView.setText(login);
  }

  @Override
  public void setNameEditText(String name) {
    editNameEditText.setText(name);
  }

  @Override
  public void setLastNameEditText(String lastName) {
    editLastNameEditText.setText(lastName);
  }

  @Override
  public void setEmailEditText(String email) {
    editEmailEditText.setText(email);
  }

  @Override
  public void setPasswordEditText(String password) {
    editPasswordEditText.setText(password);
  }

  @Override
  public void setRepeatPasswordEditText(String repeatPassword) {
    editRepeatPasswordEditText.setText(repeatPassword);
  }

  @Override
  public void setErrorMessageTextView(String errorMessage) {
    anyErrorMessageTextView.setText(errorMessage);
  }

  @Override
  public void setRepeatLayoutVisible() {
    editRepeatPasswordLinearLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public String getName() {
    return editNameEditText.getText().toString();
  }

  @Override
  public String getLastName() {
    return editLastNameEditText.getText().toString();
  }

  @Override
  public String getEmail() {
    return editEmailEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return editPasswordEditText.getText().toString();
  }

  @Override
  public String getRepeatPassword() {
    return editRepeatPasswordEditText.getText().toString();
  }
}
