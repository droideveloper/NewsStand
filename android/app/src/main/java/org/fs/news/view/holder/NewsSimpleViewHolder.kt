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

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_simple_news_item.view.*
import org.fs.architecture.mvi.common.BusManager
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.inflate
import org.fs.architecture.mvi.util.plusAssign
import org.fs.news.R
import org.fs.news.common.glide.GlideRequests
import org.fs.news.model.entity.News
import org.fs.news.model.event.BookmarkNewsEvent
import org.fs.news.model.event.SelectNewsEvent
import org.fs.news.net.repo.NewsRepository
import org.fs.rx.extensions.util.clicks
import java.text.SimpleDateFormat
import java.util.*

class NewsSimpleViewHolder(view: View, private val glide: GlideRequests, private val newsRepository: NewsRepository): BaseNewsViewHolder(view) {

  private val disposeBag by lazy { CompositeDisposable() }
  private val timeFormat by lazy { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

  private val viewTextTitle by lazy { itemView.viewTextTitleNews }
  private val viewTextSpot by lazy { itemView.viewTextSpotNews }
  private val viewTextTime by lazy { itemView.viewTextTimeNews }
  private val viewImageNews by lazy { itemView.viewImageNews }

  private val viewTextBookmark by lazy { itemView.viewTextBookmark }
  private val viewProgress by lazy { itemView.viewProgress }

  constructor(parent: ViewGroup, glide: GlideRequests, newsRepository: NewsRepository): this(parent.inflate(R.layout.view_simple_news_item), glide, newsRepository)

  override fun bind(value: News) {
    invalidateProgress(false)

    glide.clear(viewImageNews)
    glide.load(value.urlToImage)
      .applyCrop()
      .into(viewImageNews)

    viewTextTitle.text = value.title
    viewTextSpot.text = value.content

    viewTextTime.text = timeFormat.format(value.publishedAt ?: Date())

    val bookmarkStateRes = if (value.hasBookmark) R.string.str_un_bookmark_news_title else R.string.str_bookmark_news_title
    viewTextBookmark.setText(bookmarkStateRes)

    disposeBag += bindBookmarkNewsEvent(value).subscribe(BusManager.Companion::send)
    disposeBag += bindSelectNewsEvent(value).subscribe(BusManager.Companion::send)

    // we will check every bind on local storage existence
    if (!value.hasBookmark) {
      disposeBag += newsRepository.hasBookmark(value.url)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { news ->
          val stateRes = if (TextUtils.equals(news.url, value.url)) R.string.str_un_bookmark_news_title else R.string.str_bookmark_news_title
          value.hasBookmark = TextUtils.equals(news.url, value.url)
          viewTextBookmark.setText(stateRes)
        }
    }
  }

  override fun unbind() = disposeBag.clear()

  private fun bindSelectNewsEvent(value: News): Observable<SelectNewsEvent> = itemView.clicks()
    .map { SelectNewsEvent(value) }

  private fun bindBookmarkNewsEvent(value: News): Observable<BookmarkNewsEvent> = itemView.viewTextBookmark.clicks()
    .map { BookmarkNewsEvent(value) }
    .doOnNext { invalidateProgress(true) }

  private fun invalidateProgress(showProgress: Boolean) {
    val visibility = if (showProgress) View.VISIBLE else View.INVISIBLE
    val textVisibility = if (showProgress) View.INVISIBLE else View.VISIBLE
    viewProgress.visibility = visibility
    viewTextBookmark.visibility = textVisibility
    viewProgress.isIndeterminate = showProgress
  }
}