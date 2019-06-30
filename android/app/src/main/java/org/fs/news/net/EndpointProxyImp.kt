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

package org.fs.news.net

import io.reactivex.Observable
import org.fs.news.model.entity.News
import org.fs.news.model.entity.Source
import org.fs.news.net.model.Resource
import org.fs.news.net.model.Response
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndpointProxyImp @Inject constructor(private val endpoint: Endpoint): EndpointProxy {

  private val atomicCount = AtomicInteger(0)

  override fun sources(): Observable<Resource<List<Source>>> = endpoint.sources().toResource()

  override fun news(sources: String, page: Int?): Observable<Resource<List<News>>> = endpoint.news(sources, page).toResource()

  private fun <T> Observable<Response<T>>.toResource(): Observable<Resource<T>> = map { response ->
    if (response.message != null) {
      return@map Resource.Failure<T>(response.code, response.message)
    }
    val hasSource = response.sources != null
    if (hasSource) {
      return@map Resource.Success(data = response.sources)
    } else {
      return@map Resource.Success(data = response.articles, totalResult = response.totalResults)
    }
  }.tryAgainIfFails()

  private fun <T> Observable<T>.tryAgainIfFails(max: Int = 3, delay: Long = 3L, unit: TimeUnit = TimeUnit.SECONDS): Observable<T> = retryWhen { errors ->
    return@retryWhen errors.flatMap { error ->
      val current = atomicCount.incrementAndGet()
      if (current <= max) {
        return@flatMap Observable.just(current)
          .delay(delay * current,  unit)
      }
      atomicCount.set(0)
      return@flatMap Observable.error<T>(error)
    }
  }
}