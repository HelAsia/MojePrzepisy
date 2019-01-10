package com.moje.przepisy.mojeprzepisy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.moje.przepisy.mojeprzepisy.R;
import com.squareup.picasso.Picasso;

public class URLDialog {
  private String urlStringValue;

  public void showDialog(final Activity activity, final ImageView imageView){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.url_dialog);


    final EditText urlAddressEditText = dialog.findViewById(R.id.setURL_EditText);


    Button cancelUrlButton = dialog.findViewById(R.id.set_url_cancel_button);
    cancelUrlButton.setOnClickListener(view -> dialog.dismiss());

    Button setUrlButton = dialog.findViewById(R.id.set_url_button);
    setUrlButton.setOnClickListener(v -> {
      urlStringValue = urlAddressEditText.getText().toString();

      if(!urlStringValue.isEmpty())
      Picasso.get().load(urlStringValue).into(imageView);
      imageView.setVisibility(View.VISIBLE);
      dialog.dismiss();
    });
    dialog.show();
  }
}
