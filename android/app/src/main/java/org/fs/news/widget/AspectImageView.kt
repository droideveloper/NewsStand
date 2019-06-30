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

package org.fs.news.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.SparseArray
import org.fs.news.R
import org.fs.news.util.C.Companion.RATIO_16_9
import org.fs.news.util.C.Companion.RATIO_4_3
import kotlin.math.roundToInt

class AspectImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, style: Int = 0): AppCompatImageView(context, attrs, style) {

  private val ratios by lazy { SparseArray<Float>().apply {
      put(1, RATIO_4_3)
      put(2, RATIO_16_9)
    }
  }

  private val ratio: Float

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.AspectImageView)
    ratio = if (a.hasValue(R.styleable.AspectImageView_ui_ratio)) {
      val r = a.getInt(R.styleable.AspectImageView_ui_ratio, 0)
      ratios[r] ?: 0f
    } else {
      0f
    }
    a.recycle()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val w = MeasureSpec.getSize(widthMeasureSpec)
    if (ratio != 0f) {
      val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec((w / ratio).roundToInt(), MeasureSpec.EXACTLY)
      super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }
}