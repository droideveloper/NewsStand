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

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.fs.architecture.mvi.common.Model
import org.fs.architecture.mvi.common.ViewModel
import org.fs.architecture.mvi.core.AbstractFragment
import org.fs.news.BuildConfig
import org.fs.news.R
import java.io.PrintWriter
import java.io.StringWriter

inline fun <reified T> T.isLogEnabled(): Boolean = BuildConfig.DEBUG
inline fun <reified T> T.getClassTag(): String = T::class.java.simpleName

inline fun <reified T> T.log(error: Throwable) {
  val sw = StringWriter()
  val pw = PrintWriter(sw)
  error.printStackTrace(pw)
  log(Log.ERROR, sw.toString())
}
inline fun <reified T> T.log(message: String) = log(Log.DEBUG, message)

inline fun <reified T> T.log(logLevel: Int, message: String) {
  if (isLogEnabled()) {
    Log.println(logLevel, getClassTag(), message)
  }
}

fun SwipeRefreshLayout.bind(showProgress: Boolean) {
  isRefreshing = showProgress
}

fun <T, VM> AbstractFragment<T, VM>.showError(error: Throwable) where T: Model<*>, VM: ViewModel<T> {
  val snackbar = Snackbar.make(view as ViewGroup, error.message ?: error.localizedMessage, Snackbar.LENGTH_INDEFINITE)
  snackbar.setAction(getString(android.R.string.ok)) { snackbar.dismiss() }
  snackbar.show()
}

// glide options base
fun RequestOptions.applyBase(): RequestOptions = placeholder(R.drawable.ic_placeholder)
  .error(R.drawable.ic_error_placeholder)
  .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
  .dontAnimate()

// recycler extensions
fun Drawable.recyclerDivider(viewRecycler: RecyclerView, gravity: Int = DividerItemDecoration.VERTICAL) {
  val divider = DividerItemDecoration(viewRecycler.context, gravity)
  divider.setDrawable(this)
  viewRecycler.addItemDecoration(divider)
}

fun Context.resolveActivity(component: Class<*>, bundle: Bundle? = null) = startActivity(Intent(this, component).apply {
  bundle?.let(::putExtras)
})
