package com.example.patient.api

import com.example.patient.BuildConfig
import com.example.patient.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitCore {
    fun retrofitService(apiVer: Int): ApiService {
        val baseUrl = when (apiVer) {
            1 -> BuildConfig.API_ENDPOINT_1
            2 -> BuildConfig.API_ENDPOINT_2
            else -> ""
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient(Config.CONNECTION_TIME_OUT, Config.READ_TIME_OUT, Config.WRITE_TIME_OUT))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    private fun okHttpClient(
        connectionTimeOut: Long,
        readTimeOut: Long,
        writeTimeOut: Long
    ): OkHttpClient {
        val okHttpBuild = OkHttpClient.Builder()

        okHttpBuild.connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
        okHttpBuild.readTimeout(readTimeOut, TimeUnit.SECONDS)
        okHttpBuild.writeTimeout(writeTimeOut, TimeUnit.SECONDS)
        okHttpBuild.cache(null)

        return okHttpBuild.build()
    }
}