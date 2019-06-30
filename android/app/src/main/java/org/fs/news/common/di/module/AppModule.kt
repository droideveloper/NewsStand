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

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import okhttp3.Interceptor
import org.fs.architecture.mvi.common.ForActivity
import org.fs.news.App
import org.fs.news.common.db.BookmarkDaoProxy
import org.fs.news.common.db.BookmarkDaoProxyImp
import org.fs.news.net.EndpointProxy
import org.fs.news.net.EndpointProxyImp
import org.fs.news.net.inter.AuthorizationInterceptor
import org.fs.news.net.inter.LogInterceptor
import org.fs.news.net.repo.NewsRepository
import org.fs.news.net.repo.NewsRepositoryImp
import org.fs.news.net.repo.SourceRepository
import org.fs.news.net.repo.SourceRepositoryImp
import org.fs.news.util.C.Companion.NAME_AUTH
import org.fs.news.util.C.Companion.NAME_LOG
import org.fs.news.view.MainActivity
import org.fs.news.view.NewsDetailActivity
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

  @Singleton @Binds abstract fun bindApplication(app: App): Application
  @Singleton @Binds abstract fun bindContext(app: Application): Context

  @Singleton @Binds abstract fun bindBookmarkDaoProxy(proxy: BookmarkDaoProxyImp): BookmarkDaoProxy
  @Singleton @Binds abstract fun bindEndpointProxy(proxy: EndpointProxyImp): EndpointProxy

  @Singleton @Binds @Named(NAME_AUTH) abstract fun bindAuthorizationInterceptor(inter: AuthorizationInterceptor): Interceptor
  @Singleton @Binds @Named(NAME_LOG) abstract fun bindLoggingInterceptor(inter: LogInterceptor): Interceptor

  @Singleton @Binds abstract fun bindNewsRepository(repo: NewsRepositoryImp): NewsRepository
  @Singleton @Binds abstract fun bindSourceRepository(repo: SourceRepositoryImp): SourceRepository

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun contributeMainActivity(): MainActivity

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun contributeNewsDetailActivity(): NewsDetailActivity
}