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

package org.fs.news.net.repo

import io.reactivex.Observable
import org.fs.news.common.db.BookmarkDaoProxy
import org.fs.news.model.entity.News
import org.fs.news.net.EndpointProxy
import org.fs.news.net.model.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImp @Inject constructor(private val eProxy: EndpointProxy, private val dProxy: BookmarkDaoProxy): NewsRepository {
  override fun bookmarks(): Observable<List<News>> = dProxy.bookmarks()

  override fun hasBookmark(url: String): Observable<News> = dProxy.hasBookmark(url)

  override fun addBookmark(news: News): Observable<News> = dProxy.insert(news).andThen(Observable.just(news))

  override fun removeBookmark(news: News): Observable<News> = dProxy.delete(news).andThen(Observable.just(news))

  override fun news(sources: String, page: Int?): Observable<Resource<List<News>>> = eProxy.news(sources, page)
}