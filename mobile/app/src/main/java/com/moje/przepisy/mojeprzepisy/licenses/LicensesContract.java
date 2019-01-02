package com.moje.przepisy.mojeprzepisy.licenses;

import android.content.Context;
import com.moje.przepisy.mojeprzepisy.data.model.License;
import java.util.List;

public interface LicensesContract {

  interface View{
    Context getContext();
    void setToolbar();
    void setRecyclerView(List<License> licenseList);
    void goToLicenseSource(String licenseUrl);
  }

  interface Presenter{
    void setFirstScreen();
  }

}
