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
import org.fs.news.model.SourceModel
import org.fs.news.model.entity.Source
import org.fs.news.net.model.Resource
import org.fs.news.net.repo.SourceRepository
import org.fs.news.util.Operations.Companion.REFRESH
import java.io.IOException

class LoadSourceIntent(private val sourceRepository: SourceRepository): ObservableIntent<SourceModel>() {

  override fun invoke(): Observable<Reducer<SourceModel>> = sourceRepository.sources()
    .concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())

  private fun success(resource: Resource<List<Source>>): Observable<Reducer<SourceModel>> = when(resource) {
    is Resource.Success<List<Source>> -> Observable.just(
      { o -> o.copy(state = Operation(REFRESH), data = resource.data ?: emptyList()) },
      { o -> o.copy(state = Idle, data = emptyList()) })
    is Resource.Failure<List<Source>> -> Observable.just(
      { o -> o.copy(state = Failure(IOException(resource.message))) },
      { o -> o.copy(state = Idle) })
  }

  private fun failure(error: Throwable): Observable<Reducer<SourceModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<SourceModel> = { o -> o.copy(state = Operation(REFRESH), data = emptyList()) }
}