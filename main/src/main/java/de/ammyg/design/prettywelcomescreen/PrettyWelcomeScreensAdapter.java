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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by amade on 12. Dez. 2015.
 */
public class PrettyWelcomeScreensAdapter extends FragmentPagerAdapter {

  private ArrayList<Fragment> fragments;
  public PrettyWelcomeScreensAdapter(FragmentManager fm) {
    super(fm);
  }

  public ArrayList<Fragment> getFragments() {
    return fragments;
  }

  public void setFragments(ArrayList<Fragment> fragments) {
    this.fragments = fragments;
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    if(fragments != null) {
      return fragments.size();
    }
    return 0;
  }
}
