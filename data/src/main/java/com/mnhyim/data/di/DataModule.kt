package com.mnhyim.data.di

import android.util.Log
import com.mnhyim.data.remote.NewsApi
import com.mnhyim.data.remote.NewsApiImpl
import com.mnhyim.data.remote.NewsRepositoryImpl
import com.mnhyim.domain.network.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HttpLogging:", message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}

val provideNewsApiModule = module {
    single<NewsApi> { NewsApiImpl(get()) }
}

val provideRepositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}