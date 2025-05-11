package com.mnhyim.data.remote

import android.util.Log
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import com.mnhyim.data.remote.NewsApi
import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.Source
import com.mnhyim.domain.network.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override fun getNewsSources(
        category: Category?,
        lang: Language?,
        country: Country?
    ): Flow<Resource<List<SourceDetail>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getNewsSources(
                category = category,
                lang = lang,
                country = country
            )
            val mappedResponse = response.sources.map {
                SourceDetail(
                    id = it.id,
                    name = it.name,
                    desc = it.description,
                    url = it.url,
                    category = Category.ENTERTAINMENT,
                    language = Language.EN,
                    country = Country.US
                )
            }
            emit(Resource.Success(mappedResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun getTopHeadlinesBySources(sources: String): Flow<Resource<List<News>>> = flow {
        emit(Resource.Loading)
        try {
            val response = api.getTopHeadlinesBySources(sources = sources)
            val mappedResponse = response.articles.map {
                News(
                    source = Source(it.source.id, it.source.name),
                    author = it.author ?: "",
                    title = it.title ?: "",
                    description = it.description ?: "",
                    url = it.url ?: "",
                    urlToImage = it.urlToImage ?: "",
                    timestamp = LocalDate.now(),
                    content = it.content ?: ""
                )
            }
            emit(Resource.Success(mappedResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}