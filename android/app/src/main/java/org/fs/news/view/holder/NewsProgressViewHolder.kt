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

package org.fs.news.view.holder

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_simple_progress_item.view.*
import org.fs.architecture.mvi.util.inflate
import org.fs.news.R
import org.fs.news.model.entity.News

class NewsProgressViewHolder(view: View): BaseNewsViewHolder(view) {

  private val viewProgress by lazy { itemView.viewProgress }

  constructor(parent: ViewGroup): this(parent.inflate(R.layout.view_simple_progress_item))

  init {
    val lp = itemView.layoutParams
    if (lp is StaggeredGridLayoutManager.LayoutParams) {
      lp.isFullSpan = true
    }
  }

  override fun bind(value: News) = invalidateProgress(true)

  override fun unbind() = invalidateProgress(false)

  private fun invalidateProgress(showProgress: Boolean) {
    viewProgress.isIndeterminate = showProgress
  }
}