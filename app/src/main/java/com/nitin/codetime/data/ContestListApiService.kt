package com.nitin.codetime.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nitin.codetime.data.response.ContestResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ContestListApiService {
    @GET("contest")
    fun testApiAsync(): Deferred<ContestResponse>

    companion object {
        private lateinit var API_KEY: String
        private lateinit var USER_NAME: String

        operator fun invoke(): ContestListApiService {
            val reqInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization", "ApiKey $USER_NAME:$API_KEY")
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(reqInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://clist.by/api/v1/json/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ContestListApiService::class.java)
        }

        fun initApi(apiKey: String, userName: String) {
            API_KEY = apiKey
            USER_NAME = userName
        }
    }
}