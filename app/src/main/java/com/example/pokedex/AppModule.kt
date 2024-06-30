package com.example.pokedex

import android.app.Application
import com.example.pokedex.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi(): PokeApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokeRepository(pokeApi:PokeApi,
                              app:Application,
//                              @Named("hello1") hello1:String
    ):PokeRepository{
        return PokeRepositoryImpl(pokeApi,app)
    }

//    @Provides
//    @Singleton
//    @Named("hello1")
//    fun provideString1() = "안녕1"
//
//    @Provides
//    @Singleton
//    @Named("hello2")
//    fun provideString2() = "안녕2"


}