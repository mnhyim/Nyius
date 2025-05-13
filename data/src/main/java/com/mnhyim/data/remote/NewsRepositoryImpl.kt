package com.mnhyim.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mnhyim.data.remote.paging.NewsPagingSource
import com.mnhyim.data.remote.paging.PagedNewsRepository
import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.Source
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import com.mnhyim.domain.network.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository, PagedNewsRepository {

    override fun getNewsSources(
        category: Category,
        lang: Language,
        country: Country
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
                    category = Category.valueOf(it.category.uppercase()),
                    language = Language.valueOf(it.language.uppercase()),
                    country = Country.valueOf(it.country.uppercase())
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
                    timestamp = ZonedDateTime.parse(it.publishedAt).toLocalDate(),
                    content = it.content ?: ""
                )
            }
            emit(Resource.Success(mappedResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun getPagedNews(source: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 5, initialLoadSize = 10),
            pagingSourceFactory = { NewsPagingSource(api, source) }
        ).flow.map { pagingData ->
            pagingData.map {
                News(
                    source = Source(it.source.id, it.source.name),
                    author = it.author ?: "",
                    title = it.title ?: "",
                    description = it.description ?: "",
                    url = it.url ?: "",
                    urlToImage = it.urlToImage ?: "",
                    timestamp = ZonedDateTime.parse(it.publishedAt).toLocalDate(),
                    content = it.content ?: ""
                )
            }
        }
    }
}