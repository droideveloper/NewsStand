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

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_sources_fragment.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.plusAssign
import org.fs.news.R
import org.fs.news.common.navigation.Navigation
import org.fs.news.common.recycler.SafeLinearLayoutManager
import org.fs.news.model.SourceModel
import org.fs.news.model.entity.Source
import org.fs.news.model.event.LoadSourceEvent
import org.fs.news.model.event.SelectSourceEvent
import org.fs.news.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.news.util.Operations.Companion.REFRESH
import org.fs.news.util.bind
import org.fs.news.util.recyclerDivider
import org.fs.news.util.showError
import org.fs.news.view.adapter.SourceAdapter
import org.fs.news.wm.SourceFragmentViewModel
import org.fs.rx.extensions.v4.util.refreshes
import javax.inject.Inject

class SourceFragment : AbstractFragment<SourceModel, SourceFragmentViewModel>(),
  SourceFragmentView {

  @Inject lateinit var sourceAdapter: SourceAdapter
  @Inject lateinit var dataSet: ObservableList<Source>
  @Inject lateinit var navigator: Navigation<Source>

  private val verticalDrawable by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_divider, context?.theme) }
  private val isTabletDevice by lazy { resources.getBoolean(R.bool.isTablet) }

  override val layoutRes: Int get() = R.layout.view_sources_fragment

  override fun setUp(state: Bundle?) {
    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = SafeLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      adapter = sourceAdapter
      verticalDrawable?.recyclerDivider(this, DividerItemDecoration.VERTICAL)
    }
    viewSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorTitleText)
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { evt -> when(evt) {
        is SelectSourceEvent -> navigator.navigate(context, evt.source)
      }
    })

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == REFRESH
        }
        return@map false
      }
      .subscribe(viewSwipeRefreshLayout::bind)

    disposeBag += bindSwipeRefresh().subscribe(::accept)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    checkIfInitialLoadNeeded()
  }

  override fun render(model: SourceModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> showError(model.state.error)
    is Operation -> render(model.data)
  }

  override fun bindSwipeRefresh(): Observable<LoadSourceEvent> = viewSwipeRefreshLayout.refreshes()
    .map { LoadSourceEvent() }
    .doOnNext { dataSet.clear() }

  private fun render(data: List<Source>) {
    if (data.isNotEmpty()) {
      if (isTabletDevice && dataSet.isEmpty()) { // initial selection for tablet devices
        BusManager.send(SelectSourceEvent(data.firstOrNull() ?: Source.EMPTY))
      }
      dataSet.addAll(data)
    }
  }

  private fun checkIfInitialLoadNeeded() {
    if (dataSet.isEmpty()) {
      accept(LoadSourceEvent())
    }
  }
}