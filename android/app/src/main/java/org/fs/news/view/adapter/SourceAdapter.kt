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

package org.fs.news.view.adapter

import android.view.ViewGroup
import org.fs.architecture.mvi.common.ForFragment
import org.fs.architecture.mvi.core.AbstractRecyclerViewAdapter
import org.fs.architecture.mvi.util.ObservableList
import org.fs.news.model.entity.Source
import org.fs.news.util.C.Companion.VIEW_TYPE_PROGRESS
import org.fs.news.util.C.Companion.VIEW_TYPE_SIMPLE
import org.fs.news.view.holder.BaseSourceViewHolder
import org.fs.news.view.holder.SourceProgressViewHolder
import org.fs.news.view.holder.SourceSimpleViewHolder
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ForFragment
class SourceAdapter @Inject constructor(dataSet: ObservableList<Source>): AbstractRecyclerViewAdapter<Source, BaseSourceViewHolder>(dataSet) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSourceViewHolder = when(viewType) {
    VIEW_TYPE_PROGRESS -> SourceProgressViewHolder(parent)
    VIEW_TYPE_SIMPLE -> SourceSimpleViewHolder(parent)
    else -> throw IllegalArgumentException("can not recognize viewType for $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    val item = dataSet[position]
    return when (item) {
      Source.EMPTY -> VIEW_TYPE_PROGRESS
      else -> VIEW_TYPE_SIMPLE
    }
  }
}