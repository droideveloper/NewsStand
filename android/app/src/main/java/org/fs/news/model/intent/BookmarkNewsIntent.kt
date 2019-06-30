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

package org.fs.news.model.intent

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.fs.architecture.mvi.common.*
import org.fs.news.model.NewsModel
import org.fs.news.model.entity.News
import org.fs.news.net.repo.NewsRepository
import org.fs.news.util.Operations.Companion.REMOVE_BOOKMARK
import org.fs.news.util.Operations.Companion.SAVE_BOOKMARK

class BookmarkNewsIntent(
  private val news: News,
  private val newsRepository: NewsRepository): ObservableIntent<NewsModel>() {

  private val state: Int = if (news.hasBookmark) REMOVE_BOOKMARK else SAVE_BOOKMARK

  override fun invoke(): Observable<Reducer<NewsModel>> {
    val repoSource = when (state) {
      REMOVE_BOOKMARK -> newsRepository.removeBookmark(news)
      SAVE_BOOKMARK -> newsRepository.addBookmark(news)
      else -> Observable.never()
    }

    return repoSource.concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())
  }

  private fun success(news: News): Observable<Reducer<NewsModel>> = Observable.just(
    { o -> o.copy(state = Operation(state), changeState = news) },
    { o -> o.copy(state = Idle, changeState = News.EMPTY) })

  private fun failure(error: Throwable): Observable<Reducer<NewsModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<NewsModel> = { o -> o.copy(state = Operation(state), changeState = News.EMPTY) }
}