package com.example.ktorsample.hiltmodules

import com.example.ktorsample.repositories.MainRepo
import com.example.ktorsample.server.ConnectionBuilder
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingltonScope {
    @Provides
    @Singleton
    fun installHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            engine {
                connectTimeout = 30000
                socketTimeout = 30000
            }
        }
    }

}