package com.example.pokedex

import android.app.Application
import com.example.pokedex.util.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class AuthInterceptor( ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val response = chain.proceed(request);

        when (response.code) {
            400 -> {
                //Show Bad Request Error Message
            }
            401 -> {
                //Show UnauthorizedError Message
            }

            403 -> {
                //Show Forbidden Message
            }

            404 -> {
                //Show NotFound Message
            }

            // ... and so on

        }
        return response
    }


}
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    private val okHttpClientBuilder = OkHttpClient.Builder()
//        .addInterceptor(provideLoggingInterceptor())
//        .connectTimeout(20, TimeUnit.SECONDS)
//        .writeTimeout(20, TimeUnit.SECONDS)
//        .readTimeout(20, TimeUnit.SECONDS)
//        .addInterceptor(AuthInterceptor())


    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(): Retrofit{
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun providePokeRepository(retrofit: Retrofit): PokeApi{
        return retrofit.create(PokeApi::class.java)
    }
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        //  interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
//    @Singleton
//    @Provides
//    fun provideMainRepository(apiService:PokeApi)= PokeRepository(apiService)
}