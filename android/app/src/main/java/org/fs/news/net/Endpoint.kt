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
import org.fs.news.net.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

  @GET("sources") fun sources(): Observable<Response<List<Source>>>
  @GET("top-headlines") fun news(@Query("sources") sources: String, @Query("page") page: Int? = null): Observable<Response<List<News>>>
}