package com.kingja.vinci.example;

import android.content.Context;
import android.widget.AbsListView;


public class SampleScrollListener implements AbsListView.OnScrollListener {
  private final Context context;

  public SampleScrollListener(Context context) {
    this.context = context;
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                       int totalItemCount) {
    // Do nothing.
  }
}
