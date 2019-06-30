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

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import org.fs.news.R
import org.fs.news.common.db.BookmarkDao
import org.fs.news.common.db.LocalDatabase
import org.fs.news.common.navigation.HandsetNavigation
import org.fs.news.common.navigation.Navigation
import org.fs.news.common.navigation.TabletNavigation
import org.fs.news.model.entity.Source
import org.fs.news.util.C.Companion.DB_NAME
import javax.inject.Singleton

@Module
class StorageModule {

  @Singleton @Provides fun provideLocalDatabase(context: Context): LocalDatabase = Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME).build()

  @Singleton @Provides fun provideBookmarkDao(localDatabase: LocalDatabase): BookmarkDao = localDatabase.bookmarkDao()

  @Singleton @Provides fun provideNavigation(context: Context): Navigation<Source> {
    val isTabletDevice = context.resources.getBoolean(R.bool.isTablet)
    return if (isTabletDevice) TabletNavigation() else HandsetNavigation()
  }
}