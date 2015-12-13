/*
 * Copyright (c) 2015 Amadeus Gebauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ammyg.design.prettywelcomescreen;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by amade on 13. Dez. 2015.
 */
public class PrettyWelcomeScreenPageFragment extends Fragment implements PrettyWelcomeScreensBaseInterface {

  private TextView     tvTitle;
  private TextView     tvDescription;
  private ImageView    imageView;
  private LinearLayout containerView;

  private PrettyWelcomeScreenPage page;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_page, container, false);

    tvTitle = (TextView) v.findViewById(R.id.welcome_screens_page_title);
    tvDescription = (TextView) v.findViewById(R.id.welcome_screens_page_description);
    imageView = (ImageView) v.findViewById(R.id.welcome_screens_page_image);
    containerView = (LinearLayout) v.findViewById(R.id.welcome_screens_page_container);

    notifyDataSetChanged();

    return v;
  }

  public PrettyWelcomeScreenPage getPage() {
    return page;
  }

  public void setPage(PrettyWelcomeScreenPage page) {
    this.page = page;
  }

  public void notifyDataSetChanged() {
    if (tvTitle != null && page != null) {

      tvTitle.setText(page.getTitle());
      tvDescription.setText(page.getDescription());
      imageView.setImageDrawable(page.getImage());
    }
  }

  @Override
  public boolean isUnlocked() {
    if (page != null) {
      return page.isUnlocked();
    }
    return false;
  }

  @Override
  public boolean showSkip() {
    if (page != null) {
      return page.isShowSkip();
    }
    return false;
  }

  @Override
  public boolean showNext() {
    if (page != null) {
      return page.isShowNext();
    }
    return false;
  }

  private float px2dp(float px) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
  }

  public void setScrollOffset(float scrollOffset) {
    if (tvTitle != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        // Animate views on Android >= 3
        tvTitle.setTranslationX(scrollOffset * px2dp(300));
        tvDescription.setTranslationX(scrollOffset * px2dp(150));
      }
    }
  }
}
