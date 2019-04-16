package com.nitin.codetime.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nitin.codetime.data.network.response.ContestResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ContestListApiService {
    @GET("contest")
    fun getLiveContests(
        @Query(ApiConstants.RESOURCE_ID_IN) resIds: String,
        @Query(ApiConstants.START_LTE) startDate: String,
        @Query(ApiConstants.END_GT) endDate: String
    ): Deferred<ContestResponse>

    @GET("contest")
    fun getPastContests(
        @Query(ApiConstants.RESOURCE_ID_IN) resIds: String,
        @Query(ApiConstants.END_LT) endDate: String,
        @Query(ApiConstants.ORDER_BY) orderBy: String
    ): Deferred<ContestResponse>

    companion object {
        private lateinit var API_KEY: String
        private lateinit var USER_NAME: String

        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ContestListApiService {
            val reqInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization", "ApiKey $USER_NAME:$API_KEY")
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(reqInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.BASE_URL)
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