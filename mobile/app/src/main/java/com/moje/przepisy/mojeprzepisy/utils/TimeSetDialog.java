package com.moje.przepisy.mojeprzepisy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.R;

public class TimeSetDialog {

  String hour;
  String minute;

  public void showDialog(Activity activity, final TextView textViewToSet){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.time_set_dialog);

    final Spinner setHourSpinner = dialog.findViewById(R.id.hourSpinner);
    final Spinner setMinuteSpinner = dialog.findViewById(R.id.minuteSpinner);

    Button dialogButton = (Button) dialog.findViewById(R.id.set_time_button);
    dialogButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TextView setHourSpinnerTextView = (TextView) setHourSpinner.getSelectedView();
        hour = setHourSpinnerTextView.getText().toString();
        TextView setMinuteSpinnerTextView = (TextView) setMinuteSpinner.getSelectedView();
        minute = setMinuteSpinnerTextView.getText().toString();
        dialog.dismiss();
        textViewToSet.setText(hour+" : "+minute);

      }
    });
    dialog.show();
  }

  public String getHour(){
    return hour;
  }

  public String getMinute(){
    return minute;
  }

}
