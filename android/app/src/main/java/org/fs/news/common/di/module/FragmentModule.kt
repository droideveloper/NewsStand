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

import dagger.Binds
import dagger.Module
import org.fs.architecture.mvi.common.ForFragment
import org.fs.news.view.NewsFragment
import org.fs.news.view.NewsFragmentView
import org.fs.news.view.SourceFragment
import org.fs.news.view.SourceFragmentView

@Module
abstract class FragmentModule {

  @ForFragment @Binds abstract fun bindSourceFragmentView(fragment: SourceFragment): SourceFragmentView

  @ForFragment @Binds abstract fun bindNewsFragmentView(fragment: NewsFragment): NewsFragmentView
}