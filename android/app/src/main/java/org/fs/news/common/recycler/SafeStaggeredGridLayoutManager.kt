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

package org.fs.news.common.recycler

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import org.fs.news.util.log

class SafeStaggeredGridLayoutManager constructor(spanCount: Int, orientation: Int): StaggeredGridLayoutManager(spanCount, orientation) {

  override fun supportsPredictiveItemAnimations(): Boolean = false

  override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
    try {
      super.onLayoutChildren(recycler, state)
    } catch (error: Throwable) {
      log(error)
    }
  }
}
