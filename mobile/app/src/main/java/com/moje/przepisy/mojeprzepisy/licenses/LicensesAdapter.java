package com.moje.przepisy.mojeprzepisy.licenses;

import android.animation.Animator;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.License;
import java.util.List;

public class LicensesAdapter extends RecyclerView.Adapter<LicensesAdapter.ViewHolder> {
  private Context context;
  private List<License> licensesList;

  LicensesAdapter(Context context, List<License> licensesList) {
    this.context = context;
    this.licensesList = licensesList;
    setHasStableIds(true);
  }

  @Override
  public LicensesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_license_card, parent, false);
    return new LicensesAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final LicensesAdapter.ViewHolder viewHolder, final int position) {
    viewHolder.bind(licensesList.get(position));

    viewHolder.licenseCardViewLayout.setOnClickListener(view ->
            ((LicensesActivity)context)
                    .goToLicenseSource(licensesList.get(position).getLicenseUrl()));
  }

  @Override
  public int getItemCount() {
    return licensesList.size();
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  private void animateCircularReveal(View view) {
    int centerX = 0;
    int centerY = 0;
    int startRadius = 0;
    int endRadius = Math.max(view.getWidth(), view.getHeight());
    Animator animation = ViewAnimationUtils
        .createCircularReveal(view, centerX, centerY, startRadius, endRadius);
    view.setVisibility(View.VISIBLE);
    animation.start();
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  @Override
  public void onViewAttachedToWindow(LicensesAdapter.ViewHolder viewHolder) {
    super.onViewAttachedToWindow(viewHolder);
    animateCircularReveal(viewHolder.itemView);
  }

  @Override
  public long getItemId(int position) {
    return licensesList.get(position).getLicenseId();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.licenseName) TextView licenseNameTextView;
    @BindView(R.id.licenseAuthor) TextView licenseAuthorTextView;
    @BindView(R.id.licenseDescription) TextView licenseDescriptionTextView;
    @BindView(R.id.my_license_card_view_layout) CardView licenseCardViewLayout;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(License license) {
      licenseNameTextView.setText(license.getLicenseName());
      licenseAuthorTextView.setText(license.getLicenseAuthor());
      licenseDescriptionTextView.setText(license.getLicenseDescription());
    }
  }
}
