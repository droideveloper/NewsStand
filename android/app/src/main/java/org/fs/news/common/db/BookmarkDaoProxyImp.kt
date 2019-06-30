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

package org.fs.news.common.db

import io.reactivex.Completable
import io.reactivex.Observable
import org.fs.news.model.entity.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkDaoProxyImp @Inject constructor(private val dao: BookmarkDao): BookmarkDaoProxy {

  override fun insert(news: News): Completable = Completable.fromAction { dao.insert(news) }

  override fun update(news: News): Completable = Completable.fromAction { dao.update(news) }

  override fun delete(news: News): Completable = Completable.fromAction { dao.delete(news) }

  override fun bookmarks(): Observable<List<News>> = Observable.create { emitter ->
    val data = dao.bookmarks()
    emitter.onNext(data)
    emitter.onComplete()
  }

  override fun hasBookmark(url: String): Observable<News> = Observable.create { emitter ->
    val data = dao.hasBookmark(url) ?: News.EMPTY
    emitter.onNext(data)
    emitter.onComplete()
  }
}