package com.moje.przepisy.mojeprzepisy.licenses;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.License;
import java.util.ArrayList;
import java.util.List;

public class LicensesPresenter implements LicensesContract.Presenter {
  LicensesContract.View licensesView;
  private List<License> licensesList = new ArrayList<>();

  public LicensesPresenter(LicensesContract.View licensesView){
    this.licensesView = licensesView;
  }

  @Override
  public List<License> getLicensesList(){
    License licenseOne = new License(1,
        licensesView.getContext().getResources().getString(R.string.license_one_name),
        licensesView.getContext().getResources().getString(R.string.license_one_author),
        licensesView.getContext().getResources().getString(R.string.license_one_description));
    License licenseTwo = new License(2,
        licensesView.getContext().getResources().getString(R.string.license_two_name),
        licensesView.getContext().getResources().getString(R.string.license_two_author),
        licensesView.getContext().getResources().getString(R.string.license_two_description));
    License licenseThree = new License(3,
        licensesView.getContext().getResources().getString(R.string.license_three_name),
        licensesView.getContext().getResources().getString(R.string.license_three_author),
        licensesView.getContext().getResources().getString(R.string.license_three_description));
    License licenseFour = new License(4,
        licensesView.getContext().getResources().getString(R.string.license_four_name),
        licensesView.getContext().getResources().getString(R.string.license_four_author),
        licensesView.getContext().getResources().getString(R.string.license_four_description));
    License licenseFive = new License(5,
        licensesView.getContext().getResources().getString(R.string.license_five_name),
        licensesView.getContext().getResources().getString(R.string.license_five_author),
        licensesView.getContext().getResources().getString(R.string.license_five_description));
    License licenseSix = new License(6,
        licensesView.getContext().getResources().getString(R.string.license_six_name),
        licensesView.getContext().getResources().getString(R.string.license_six_author),
        licensesView.getContext().getResources().getString(R.string.license_six_description));
    License licenseSeven = new License(7,
        licensesView.getContext().getResources().getString(R.string.license_seven_name),
        licensesView.getContext().getResources().getString(R.string.license_seven_author),
        licensesView.getContext().getResources().getString(R.string.license_seven_description));
    License licenseEight = new License(8,
        licensesView.getContext().getResources().getString(R.string.license_eight_name),
        licensesView.getContext().getResources().getString(R.string.license_eight_author),
        licensesView.getContext().getResources().getString(R.string.license_eight_description));
    License licenseNine = new License(9,
        licensesView.getContext().getResources().getString(R.string.license_nine_name),
        licensesView.getContext().getResources().getString(R.string.license_nine_author),
        licensesView.getContext().getResources().getString(R.string.license_nine_description));
    License licenseTen = new License(10,
        licensesView.getContext().getResources().getString(R.string.license_ten_name),
        licensesView.getContext().getResources().getString(R.string.license_ten_author),
        licensesView.getContext().getResources().getString(R.string.license_ten_description));
    License licenseEleven = new License(11,
        licensesView.getContext().getResources().getString(R.string.license_eleven_name),
        licensesView.getContext().getResources().getString(R.string.license_eleven_author),
        licensesView.getContext().getResources().getString(R.string.license_eleven_description));

    licensesList.add(licenseOne);
    licensesList.add(licenseTwo);
    licensesList.add(licenseThree);
    licensesList.add(licenseFour);
    licensesList.add(licenseFive);
    licensesList.add(licenseSix);
    licensesList.add(licenseSeven);
    licensesList.add(licenseEight);
    licensesList.add(licenseNine);
    licensesList.add(licenseTen);
    licensesList.add(licenseEleven);

    return licensesList;
  }

}
