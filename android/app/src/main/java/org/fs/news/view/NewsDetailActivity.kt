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

package org.fs.news.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.fs.news.R
import org.fs.news.common.base.BaseAppCompatActivity
import org.fs.news.model.entity.Source
import org.fs.news.util.C.Companion.BUNDLE_ARGS_SOURCE

class NewsDetailActivity: BaseAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    overridePendingTransition(R.anim.translate_right_in, R.anim.scale_out)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.view_news_detail_activity)

    val extra = savedInstanceState ?: intent.extras ?: Bundle()
    val source = extra.getParcelable(BUNDLE_ARGS_SOURCE) ?: Source.EMPTY

    val fragment = NewsFragment.newInstance(source)
    val trans = supportFragmentManager.beginTransaction()
    trans.replace(R.id.viewContentDetail, fragment)
    trans.setReorderingAllowed(false)
    trans.addToBackStack(null)
    trans.commit()
  }

  override fun onBackPressed() = finish()

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.scale_in, R.anim.translate_right_out)
  }
}