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

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import org.fs.architecture.mvi.common.db.Converters
import org.fs.news.model.entity.News
import org.fs.news.util.C.Companion.DB_VERSION

@TypeConverters(value = [Converters::class])
@Database(entities = [News::class], exportSchema = false, version = DB_VERSION)
abstract class LocalDatabase: RoomDatabase() {

  abstract fun bookmarkDao(): BookmarkDao
}