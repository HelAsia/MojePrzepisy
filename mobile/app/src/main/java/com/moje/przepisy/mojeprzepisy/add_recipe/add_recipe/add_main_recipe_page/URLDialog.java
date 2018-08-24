package com.moje.przepisy.mojeprzepisy.add_recipe.add_recipe.add_main_recipe_page;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.moje.przepisy.mojeprzepisy.R;

public class URLDialog {
  private String urlStringValue;

  public void showDialog(Activity activity, final ImageView imageView){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.url_dialog);

    final EditText urlAddressEditText = dialog.findViewById(R.id.setURL_EditText);

    Button setUrlButton = (Button) dialog.findViewById(R.id.set_url_button);
    setUrlButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        urlStringValue = urlAddressEditText.getText().toString();

        Picasso.get().load(urlStringValue).into(imageView);
          dialog.dismiss();
      }
    });
    dialog.show();
  }
}
