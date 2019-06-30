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
import okhttp3.logging.HttpLoggingInterceptor
import org.fs.news.BuildConfig
import org.fs.news.util.C.Companion.NAME_LOG
import org.fs.news.util.log
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Named(NAME_LOG) class LogInterceptor @Inject constructor(): Interceptor {

  private val logger by lazy { HttpLoggingInterceptor.Logger { message -> log(message) }}
  private val proxy by lazy { HttpLoggingInterceptor(logger).apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  override fun intercept(chain: Interceptor.Chain): Response = if (BuildConfig.DEBUG) proxy.intercept(chain) else chain.proceed(chain.request())
}