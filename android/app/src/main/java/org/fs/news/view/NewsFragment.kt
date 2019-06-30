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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.view_news_fragment.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.plusAssign
import org.fs.news.R
import org.fs.news.common.recycler.SafeStaggeredGridLayoutManager
import org.fs.news.model.NewsModel
import org.fs.news.model.entity.News
import org.fs.news.model.entity.Source
import org.fs.news.model.event.*
import org.fs.news.util.*
import org.fs.news.util.C.Companion.BUNDLE_ARGS_SOURCE
import org.fs.news.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.news.util.Operations.Companion.LOAD_MORE
import org.fs.news.util.Operations.Companion.REFRESH
import org.fs.news.util.Operations.Companion.REMOVE_BOOKMARK
import org.fs.news.util.Operations.Companion.SAVE_BOOKMARK
import org.fs.news.view.adapter.NewsAdapter
import org.fs.news.wm.NewsFragmentViewModel
import org.fs.rx.extensions.v4.util.refreshes
import org.fs.rx.extensions.v7.util.loadMore
import org.fs.rx.extensions.v7.util.navigationClicks
import javax.inject.Inject

class NewsFragment: AbstractFragment<NewsModel, NewsFragmentViewModel>(), NewsFragmentView {

  companion object {
    @JvmStatic fun newInstance(source: Source): NewsFragment = NewsFragment().apply {
      arguments = Bundle().apply {
        putParcelable(BUNDLE_ARGS_SOURCE, source)
      }
    }
  }

  @Inject lateinit var newsAdapter: NewsAdapter
  @Inject lateinit var dataSet: ObservableList<News>

  override val layoutRes: Int get() = R.layout.view_news_fragment

  private val verticalHandsetDivider by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_trans_handset_divider, context?.theme) }
  private val verticalDivider by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_trans_divider, context?.theme) }
  private val horizontalDivider by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_horizontal_trans_divider, context?.theme) }

  private val spanCount by lazy { resources.getInteger(R.integer.spanCount) }
  private val isTabletDevice by lazy { resources.getBoolean(R.bool.isTablet) }

  private var source = Source.EMPTY
  private var page = 0
  private var totalSize = 0

  override fun setUp(state: Bundle?) {
    source = state?.getParcelable(BUNDLE_ARGS_SOURCE) ?: Source.EMPTY

    viewTextTitle.text = source.name

    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = SafeStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
      adapter = newsAdapter
      if (isTabletDevice) {
        verticalDivider?.recyclerDivider(this, DividerItemDecoration.VERTICAL)
        horizontalDivider?.recyclerDivider(this, DividerItemDecoration.HORIZONTAL)
      } else {
        verticalHandsetDivider?.recyclerDivider(this, DividerItemDecoration.VERTICAL)
      }
    }
    viewSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorTitleText)
  }

  override fun attach() {
    super.attach()

    disposeBag += BusManager.add(Consumer { evt -> when(evt) {
        is SelectSourceEvent -> bindTabletNavigation(evt.source) // only will be called in tablet device mode
        is BookmarkNewsEvent -> accept(evt) // we provide this event into process-pipe
        is SelectNewsEvent -> startActivity(Intent(Intent.ACTION_VIEW).apply {
          data = Uri.parse(evt.news.url) ?: Uri.EMPTY
        })
      }
    })

    disposeBag += viewToolbar.navigationClicks()
      .subscribe { activity?.finish() }

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == LOAD_MORE
        }
        return@map false
      }
      .subscribe(::loadMore)

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == REFRESH
        }
        return@map false
      }
      .subscribe(viewSwipeRefreshLayout::bind)

    disposeBag += bindLoadMore().subscribe(::accept)
    disposeBag += bindSwipeRefresh().subscribe(::accept)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    checkIfInitialLoadNeeded()
  }

  override fun render(model: NewsModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> showError(model.state.error)
    is Operation -> when(model.state.type) {
      REFRESH, LOAD_MORE -> render(model.data).also {
        totalSize = model.totalSize
      }
      SAVE_BOOKMARK, REMOVE_BOOKMARK -> render(model.changeState)
      else -> Unit
    }.also {
      log(Log.ERROR, model.toString())
    }
  }

  override fun bindLoadMore(): Observable<LoadMoreNewsEvent> = viewRecycler.loadMore()
    .concatMap { viewModel.state().take(1) }
    .filter { state ->
      if (state is Operation) {
        return@filter state.type != LOAD_MORE && totalSize > dataSet.size
      }
      return@filter true
    }
    .doOnNext { page += 1 }
    .map { LoadMoreNewsEvent(source.id ?: String.EMPTY, page) }

  override fun bindSwipeRefresh(): Observable<LoadNewsEvent> = viewSwipeRefreshLayout.refreshes()
    .map { LoadNewsEvent(source.id ?: String.EMPTY) }
    .doOnNext {
      dataSet.clear()
      page = 0
      totalSize = 0
    }

  private fun render(data: List<News>) {
    if (data.isNotEmpty()) {
      dataSet.addAll(data)
    }
  }

  private fun render(data: News) {
    if (data != News.EMPTY) {
      val position = dataSet.indexOfFirst { n -> TextUtils.equals(n.url, data.url) }
      if (position != -1) {
        val news = dataSet[position]
        news.hasBookmark = !news.hasBookmark
        dataSet[position] = news
      }
    }
  }

  private fun checkIfInitialLoadNeeded() {
    if (source != Source.EMPTY && dataSet.isEmpty()) {
      accept(LoadNewsEvent(source.id ?: String.EMPTY))
    }
  }

  private fun bindTabletNavigation(source: Source) {
    if (this.source != source) {
      this.source = source
      // bind name of source
      viewTextTitle.text = source.name
      // continue
      if (dataSet.isNotEmpty()) {
        dataSet.clear()
      }
      page = 0
      totalSize = 0
      checkIfInitialLoadNeeded()
    }
  }

  private fun loadMore(loadMore: Boolean) { // we can show item bottom progress indicator this way
    val position = dataSet.indexOfFirst { n -> n == News.EMPTY }
    // if we have empty and state is no longer loadMore we remove empty item
    if (position != -1 && !loadMore) {
      dataSet.removeAt(position)
    }
    // if state is loadMore and we do not have item then we add it
    if (position == -1 && loadMore) {
      dataSet.add(News.EMPTY)
    }
  }
}