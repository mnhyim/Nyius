package com.mnhyim.nyius.di

import com.mnhyim.nyius.ui.feature.home.HomeViewModel
import com.mnhyim.nyius.ui.feature.news.NewsViewModel
import com.mnhyim.nyius.ui.feature.search.SearchViewModel
import com.mnhyim.nyius.ui.feature.sources.SourcesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SourcesViewModel)
    viewModelOf(::NewsViewModel)
}