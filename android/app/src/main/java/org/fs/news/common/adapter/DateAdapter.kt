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

package org.fs.news.common.adapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*


class DateAdapter: JsonAdapter<Date>() {

  private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()) }
  private val fallbackFormat by lazy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()) }

  @FromJson override fun fromJson(reader: JsonReader): Date? {
    val string = reader.nextString()
    return try {
      dateFormat.parse(string)
    } catch (error: Throwable) {
      fallbackFormat.parse(string)
    }
  }

  @ToJson override fun toJson(writer: JsonWriter, value: Date?) {
    val string = dateFormat.format(value)
    writer.value(string)
  }
}