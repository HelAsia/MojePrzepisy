package com.moje.przepisy.mojeprzepisy.search_options;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moje.przepisy.mojeprzepisy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListSearchFragment extends Fragment {


  public ListSearchFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_list_search, container, false);
  }

}