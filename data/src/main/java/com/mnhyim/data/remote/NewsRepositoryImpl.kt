package com.mnhyim.data.remote

import android.util.Log
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import com.mnhyim.data.remote.NewsApi
import com.mnhyim.domain.network.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getNewsSources(
        category: Category,
        lang: Language,
        country: Country
    ): Flow<Resource<List<SourceDetail>>> = flow {
        Log.d("A", "LoadinASDADg")
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
                    desc = it.desc,
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
}