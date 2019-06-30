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

package org.fs.news.util 

sealed class C {
  companion object {

    const val RECYCLER_CACHE_SIZE = 10

    const val CACHE_DIR = "caches"
    const val CACHE_SIZE = 24 * 1024 * 1024L
    const val DEFAULT_TIMEOUT = 20L

    const val DB_NAME = "news.db"
    const val DB_VERSION = 1

    const val NAME_AUTH = "authorization"
    const val NAME_LOG = "log"

    const val HEADER_API_KEY = "X-Api-Key"

    const val RATIO_16_9 = 1.777777f
    const val RATIO_4_3 = 1.333333f

    const val VIEW_TYPE_SIMPLE = 0x1
    const val VIEW_TYPE_PROGRESS = 0x2

    const val BUNDLE_ARGS_SOURCE = "bundle.args.source"
  }
}