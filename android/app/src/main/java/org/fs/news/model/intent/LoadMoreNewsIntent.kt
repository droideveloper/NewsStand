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
import org.fs.news.net.model.Resource
import org.fs.news.net.repo.NewsRepository
import org.fs.news.util.Operations.Companion.LOAD_MORE
import java.io.IOException

class LoadMoreNewsIntent(
  private val source: String,
  private val page: Int,
  private val newsRepository: NewsRepository): ObservableIntent<NewsModel>() {

  override fun invoke(): Observable<Reducer<NewsModel>> = newsRepository.news(source, page)
    .concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())

  private fun success(resource: Resource<List<News>>): Observable<Reducer<NewsModel>> = when(resource) {
    is Resource.Success<List<News>> -> Observable.just(
      { o -> o.copy(state = Operation(LOAD_MORE), data = resource.data ?: emptyList(), totalSize = resource.totalResult ?: 0) },
      { o -> o.copy(state = Idle, data = emptyList(), totalSize = 0) })
    is Resource.Failure<List<News>> -> Observable.just(
      { o -> o.copy(state = Failure(IOException(resource.message))) },
      { o -> o.copy(state = Idle) })
  }

  private fun failure(error: Throwable): Observable<Reducer<NewsModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<NewsModel> = { o -> o.copy(state = Operation(LOAD_MORE), data = emptyList(), totalSize = 0) }
}