package com.example.stockpredictionapplication.model

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LocalInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Key", "0128648164mshd294b13693db672p12844fjsn50c11bb306d9")
            .build()
        return chain.proceed(request)
    }
}