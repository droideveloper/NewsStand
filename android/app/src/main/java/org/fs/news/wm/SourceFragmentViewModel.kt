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
package org.fs.news.wm

import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.ForFragment
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import org.fs.news.model.SourceModel
import org.fs.news.model.event.LoadSourceEvent
import org.fs.news.model.intent.LoadSourceIntent
import org.fs.news.model.intent.NothingIntent
import org.fs.news.net.repo.SourceRepository
import org.fs.news.view.SourceFragmentView
import javax.inject.Inject

@ForFragment
class SourceFragmentViewModel @Inject constructor(
  private val sourceRepository: SourceRepository,
  view: SourceFragmentView) : AbstractViewModel<SourceModel, SourceFragmentView>(view) {

  override fun initState(): SourceModel = SourceModel(state = Idle, data = emptyList())

  override fun toIntent(event: Event): Intent = when (event) {
    is LoadSourceEvent -> LoadSourceIntent(sourceRepository)
    else -> NothingIntent<SourceModel>() // if we can not resolve event to intent
  }
} 