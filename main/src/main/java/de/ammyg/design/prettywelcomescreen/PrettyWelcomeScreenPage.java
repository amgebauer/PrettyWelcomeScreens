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

import android.graphics.drawable.Drawable;

/**
 * Created by amade on 13. Dez. 2015.
 */
public class PrettyWelcomeScreenPage {

  private String title;
  private String description;
  private Drawable image;
  private boolean showSkip;
  private boolean showNext;
  private boolean unlocked;

  public PrettyWelcomeScreenPage() {}

  public PrettyWelcomeScreenPage(String title, String description, Drawable image) {
    this.title = title;
    this.description = description;
    this.image = image;
    this.showNext = true;
    this.showSkip = true;
    this.unlocked = true;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Drawable getImage() {
    return image;
  }

  public void setImage(Drawable image) {
    this.image = image;
  }

  public boolean isShowSkip() {
    return showSkip;
  }

  public void setShowSkip(boolean showSkip) {
    this.showSkip = showSkip;
  }

  public boolean isShowNext() {
    return showNext;
  }

  public void setShowNext(boolean showNext) {
    this.showNext = showNext;
  }

  public boolean isUnlocked() {
    return unlocked;
  }

  public void setUnlocked(boolean unlocked) {
    this.unlocked = unlocked;
  }
}
