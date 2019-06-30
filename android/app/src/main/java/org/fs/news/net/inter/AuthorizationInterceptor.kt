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

package org.fs.news.net.inter

import okhttp3.Interceptor
import okhttp3.Response
import org.fs.news.BuildConfig
import org.fs.news.util.C.Companion.HEADER_API_KEY
import org.fs.news.util.C.Companion.NAME_AUTH
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Named(NAME_AUTH) class AuthorizationInterceptor @Inject constructor(): Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val newRequest = request.newBuilder()
      .addHeader(HEADER_API_KEY, BuildConfig.API_KEY)
      .build()

    return chain.proceed(newRequest)
  }
}