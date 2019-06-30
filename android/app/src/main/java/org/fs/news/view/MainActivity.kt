/*
 *  Copyright (C) 2019 Fatih, News Stand Android Kotlin.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fs.news.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import org.fs.news.R
import org.fs.news.common.base.BaseAppCompatActivity

class MainActivity: BaseAppCompatActivity() {

  private val isTabletDevice by lazy { resources.getBoolean(R.bool.isTablet) }

  override fun onCreate(savedInstanceState: Bundle?) {
    requestedOrientation = if (isTabletDevice) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    super.onCreate(savedInstanceState)
    setContentView(R.layout.view_main_activity)
  }
}