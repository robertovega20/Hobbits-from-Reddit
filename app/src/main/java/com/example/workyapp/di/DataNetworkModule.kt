package com.example.workyapp.di

import android.content.Context
import com.example.workyapp.BuildConfig
import com.example.workyapp.R
import com.example.workyapp.WorkyApplication
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {


    /**
     * OkHttpClient
     */
    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptorFlipper =
                (context.applicationContext as WorkyApplication).initializeFlipper()
            interceptorFlipper.let {
                builder.addNetworkInterceptor(it)
            }
        }
        return builder.build()
    }

    /**
     * Retrofit Provider
     */
    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .client(okHttpClient)
            //.addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .addTypeConverter(String.javaClass, HtmlEscapeStringConverter())
                        .build()
                )
            )
            .build()
}