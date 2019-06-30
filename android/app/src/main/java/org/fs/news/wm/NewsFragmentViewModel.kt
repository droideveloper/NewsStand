
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
package org.fs.news.wm

import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.ForFragment
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import org.fs.news.model.NewsModel
import org.fs.news.model.event.BookmarkNewsEvent
import org.fs.news.model.event.LoadMoreNewsEvent
import org.fs.news.model.event.LoadNewsEvent
import org.fs.news.model.intent.BookmarkNewsIntent
import org.fs.news.model.intent.LoadMoreNewsIntent
import org.fs.news.model.intent.LoadNewsIntent
import org.fs.news.model.intent.NothingIntent
import org.fs.news.net.repo.NewsRepository
import org.fs.news.view.NewsFragmentView
import javax.inject.Inject

@ForFragment
class NewsFragmentViewModel @Inject constructor(
  private val newsRepository: NewsRepository,
  view: NewsFragmentView) : AbstractViewModel<NewsModel, NewsFragmentView>(view) {

  override fun initState(): NewsModel = NewsModel(state = Idle, data = emptyList(), totalSize = 0)

  override fun toIntent(event: Event): Intent = when (event) {
    is LoadNewsEvent -> LoadNewsIntent(event.source, newsRepository)
    is LoadMoreNewsEvent -> LoadMoreNewsIntent(event.source, event.page, newsRepository)
    is BookmarkNewsEvent -> BookmarkNewsIntent(event.news, newsRepository)
    else -> NothingIntent<NewsModel>() // if we can not resolve event to intent
  }
} 