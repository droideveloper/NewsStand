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

package org.fs.news.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.fs.architecture.mvi.util.EMPTY
import java.util.*

@Entity(tableName = "bookmarks")
@JsonClass(generateAdapter = true)
@Parcelize class News: Parcelable {

  @PrimaryKey(autoGenerate = false) var url: String = String.EMPTY // we can use this property as primary key

  var author: String? = null
  var title: String? = null
  var description: String? = null
  var urlToImage: String? = null
  var publishedAt: Date? = null
  var content: String? = null

  @Ignore var hasBookmark: Boolean = false

  companion object {
    val EMPTY = News()
  }
}