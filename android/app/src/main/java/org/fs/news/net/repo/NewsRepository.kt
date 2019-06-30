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
import org.fs.news.model.entity.News
import org.fs.news.net.model.Resource

interface NewsRepository {

  fun bookmarks(): Observable<List<News>>
  fun hasBookmark(url: String): Observable<News>
  fun addBookmark(news: News): Observable<News>
  fun removeBookmark(news: News): Observable<News>

  fun news(sources: String, page: Int? = null): Observable<Resource<List<News>>>
}