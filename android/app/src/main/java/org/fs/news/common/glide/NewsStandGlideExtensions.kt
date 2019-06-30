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

package org.fs.news.common.glide

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.fs.news.util.applyBase

@GlideExtension sealed class NewsStandGlideExtensions {

  companion object {

    @JvmStatic @GlideOption fun applyCenterInside(options: RequestOptions): RequestOptions {
      return options.applyBase()
        .centerInside()
    }

    @JvmStatic @GlideOption fun applyCircularCrop(options: RequestOptions): RequestOptions {
      return options.applyBase()
        .circleCrop()
    }

    @JvmStatic @GlideOption fun applyRoundedRectCrop(options: RequestOptions, r: Int = 12): RequestOptions {
      return options.applyBase()
        .transforms(CenterCrop(), RoundedCorners(r))
    }

    @JvmStatic @GlideOption fun applyCrop(options: RequestOptions): RequestOptions {
      return options.applyBase()
        .centerCrop()
    }
  }
}