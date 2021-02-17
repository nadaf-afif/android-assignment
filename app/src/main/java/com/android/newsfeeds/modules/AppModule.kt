package com.android.newsfeeds.modules

import android.content.Context
import androidx.room.Room
import com.android.newsfeeds.apiInterface.NewsFeedApiInterface
import com.android.newsfeeds.database.NewsDatabase
import com.android.newsfeeds.database.NewsFeedDao
import com.android.newsfeeds.datasource.NewsFeedsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Afif Nadaf on 14/02/21.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://newsapi.org/v2/"


    @Singleton
    @Provides
    fun getLogging() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return  httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor)
            = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getNewsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NewsDatabase::class.java,
        NewsDatabase.DATABASE_NAME
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun getApiInterface(retrofit: Retrofit): NewsFeedApiInterface =
        retrofit.create(NewsFeedApiInterface::class.java)

    @Singleton
    @Provides
    fun getDatabaseDao(newsDatabase: NewsDatabase) = newsDatabase.newsFeedDao()


    @Singleton
    @Provides
    fun getFeedsRepository(newsFeedApiInterface: NewsFeedApiInterface, newsFeedDao: NewsFeedDao, @ApplicationContext context: Context)
            = NewsFeedsRepository(newsFeedDao, newsFeedApiInterface, context)


}