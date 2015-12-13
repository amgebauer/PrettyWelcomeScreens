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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by amade on 12. Dez. 2015.
 */
public class PrettyWelcomeScreensView extends FrameLayout implements ViewPager.OnPageChangeListener {
  private static final int[] COLORS = {0xffffc107, 0xffFF5722, 0xffCDDC39, 0xff8BC34A, 0xff4CAF50};

  private CurrentPageIndicatorView    pageIndicatorView;
  private Button                      skip;
  private ImageButton                 next;
  private ViewPager                   viewPager;
  private PrettyWelcomeScreensAdapter adapter;
  private ArrayList<Fragment>         fragments;
  private ArrayList<Fragment>         activeFragments;

  public PrettyWelcomeScreensView(Context context) {
    super(context);
    init();
  }

  public PrettyWelcomeScreensView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PrettyWelcomeScreensView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public PrettyWelcomeScreensView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View           v        = inflater.inflate(R.layout.main, this, true);

    pageIndicatorView = (CurrentPageIndicatorView) v.findViewById(R.id.welcome_screens_page_indicator);
    skip = (Button) v.findViewById(R.id.welcome_screens_skip);
    next = (ImageButton) v.findViewById(R.id.welcome_screens_next);
    viewPager = (ViewPager) v.findViewById(R.id.welcome_screens_view_pager);

    viewPager.addOnPageChangeListener(this);

    this.fragments = new ArrayList<>();
    this.activeFragments = new ArrayList<>();
  }

  public void addPage(PrettyWelcomeScreenPage page) {
    PrettyWelcomeScreenPageFragment pageFragment = new PrettyWelcomeScreenPageFragment();
    pageFragment.setPage(page);
    this.fragments.add(pageFragment);
  }

  public void addPage(PrettyWelcomeScreenPage page, int pos) {
    PrettyWelcomeScreenPageFragment pageFragment = new PrettyWelcomeScreenPageFragment();
    pageFragment.setPage(page);
    this.fragments.add(pos, pageFragment);
  }

  public void addFragment(Fragment f) {
    this.fragments.add(f);
  }

  public void setFragmentManager(FragmentManager fragmentManager) {
    FragmentManager fragmentManager1 = fragmentManager;
    adapter = new PrettyWelcomeScreensAdapter(fragmentManager);
    adapter.setFragments(this.activeFragments);
    viewPager.setAdapter(adapter);
  }

  public void notifyDataSetChanged() {
    if(adapter != null) adapter.notifyDataSetChanged();
    if(fragments != null) pageIndicatorView.setPagesCount(fragments.size());
  }

  public ArrayList<Fragment> getFragments() {
    return fragments;
  }

  public void setFragments(ArrayList<Fragment> fragments) {
    this.fragments = fragments;

    invalidateUnlockedFragments();

    pageIndicatorView.setPagesCount(fragments.size());
  }

  private void invalidateUnlockedFragments() {
    if (activeFragments != null) {
      activeFragments.clear();
    } else {
      activeFragments = new ArrayList<>();
    }

    for (Fragment f : fragments) {
      boolean unlocked = false;
      if (f instanceof PrettyWelcomeScreensBaseInterface) {
        if (((PrettyWelcomeScreensBaseInterface) f).isUnlocked()) {
          unlocked = true;
        }
      } else {
        unlocked = true;
      }

      if (unlocked) {
        activeFragments.add(f);
      }

    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    pageIndicatorView.setCurrentScrollLeft(positionOffset);
    pageIndicatorView.setCurrentSelectedPage(position);

    // Start with the colot at the beginning, if there are more pages than colors
    int colPos1 = position;
    int colPos2 = position + 1;
    while (colPos1 >= COLORS.length) {
      colPos1 -= COLORS.length;
    }
    while (colPos2 >= COLORS.length) {
      colPos2 -= COLORS.length;
    }
    // Change background color depending on scroll position
    int bg = interpolateColor(positionOffset, COLORS[colPos1], COLORS[colPos2]);
    setBackgroundColor(bg);

    Fragment fLeft  = fragments.get(position);
    Fragment fRight = null;
    if (position + 1 < fragments.size()) {
      fRight = fragments.get(position + 1);
    }
    boolean showSkipLeft  = true;
    boolean showSkipRight = true;
    boolean showNextLeft  = true;
    boolean showNextRight = true;
    if (fLeft instanceof PrettyWelcomeScreensBaseInterface) {
      PrettyWelcomeScreensBaseInterface f = (PrettyWelcomeScreensBaseInterface) fLeft;
      f.setScrollOffset(-positionOffset);
      showSkipLeft = f.showSkip();
      showNextLeft = f.showNext();
    }
    if (fRight != null && fRight instanceof PrettyWelcomeScreensBaseInterface) {
      PrettyWelcomeScreensBaseInterface f = (PrettyWelcomeScreensBaseInterface) fRight;
      f.setScrollOffset(1 - positionOffset);
      showSkipRight = f.showSkip();
      showNextRight = f.showNext();
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

      if (!showNextLeft && !showNextRight) {
        next.setVisibility(INVISIBLE);
      } else {
        next.setVisibility(VISIBLE);
        int shn = (showNextLeft) ? 1 : 0;
        int shr = (showNextRight) ? 1 : 0;
        next.setAlpha((1 - positionOffset) * shn + positionOffset * shr);
      }

      if (!showSkipLeft && !showSkipRight) {
        skip.setVisibility(INVISIBLE);
      } else {
        skip.setVisibility(VISIBLE);
        int shn = (showSkipLeft) ? 1 : 0;
        int shr = (showSkipRight) ? 1 : 0;
        skip.setAlpha((1 - positionOffset) * shn + positionOffset * shr);
      }

    }
  }

  @Override
  public void onPageSelected(int position) {

  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  public int interpolateColor(float fraction, int startValue, int endValue) {
    int startA = (startValue >> 24) & 0xff;
    int startR = (startValue >> 16) & 0xff;
    int startG = (startValue >> 8) & 0xff;
    int startB = startValue & 0xff;
    int endA   = (endValue >> 24) & 0xff;
    int endR   = (endValue >> 16) & 0xff;
    int endG   = (endValue >> 8) & 0xff;
    int endB   = endValue & 0xff;
    return ((startA + (int) (fraction * (endA - startA))) << 24) |
        ((startR + (int) (fraction * (endR - startR))) << 16) |
        ((startG + (int) (fraction * (endG - startG))) << 8) |
        ((startB + (int) (fraction * (endB - startB))));
  }
}
