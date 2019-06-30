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

package org.fs.news.common.di.module

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.fs.news.BuildConfig
import org.fs.news.common.adapter.DateAdapter
import org.fs.news.net.Endpoint
import org.fs.news.util.C.Companion.CACHE_DIR
import org.fs.news.util.C.Companion.CACHE_SIZE
import org.fs.news.util.C.Companion.DEFAULT_TIMEOUT
import org.fs.news.util.C.Companion.NAME_AUTH
import org.fs.news.util.C.Companion.NAME_LOG
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton @Provides fun provideBaseUrl(): HttpUrl = HttpUrl.parse(BuildConfig.BASE_URL) ?: throw IllegalArgumentException("invalid url ${BuildConfig.BASE_URL}")

  @Singleton @Provides fun provideHttpClient(context: Context, @Named(NAME_AUTH) auth: Interceptor, @Named(NAME_LOG) log: Interceptor): OkHttpClient {

    val file = File(context.cacheDir, CACHE_DIR)
    val cache = Cache(file, CACHE_SIZE)

    val builder = OkHttpClient.Builder()
      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
      .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
      .cache(cache)
      .addInterceptor(auth)

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(log)
    }

    return builder.build()
  }

  @Singleton @Provides fun provideMoshi(): Moshi = Moshi.Builder()
    .add(DateAdapter())
    .build()

  @Singleton @Provides fun provideRetrofit(baseUrl: HttpUrl, client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

  @Singleton @Provides fun provideEndpoint(retrofit: Retrofit): Endpoint = retrofit.create(Endpoint::class.java)
}