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

package org.fs.news.common.navigation

import android.content.Context
import android.os.Bundle
import org.fs.news.model.entity.Source
import org.fs.news.util.C.Companion.BUNDLE_ARGS_SOURCE
import org.fs.news.util.resolveActivity
import org.fs.news.view.NewsDetailActivity

class HandsetNavigation: Navigation<Source> {

  override fun navigate(context: Context?, payload: Source) {
    context?.resolveActivity(NewsDetailActivity::class.java, Bundle().apply {
      putParcelable(BUNDLE_ARGS_SOURCE, payload)
    })
  }
}