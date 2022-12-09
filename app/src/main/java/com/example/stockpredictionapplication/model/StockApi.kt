package com.example.stockpredictionapplication.model

import com.example.stockpredictionapplication.model.entities.CompanyHistory
import com.example.stockpredictionapplication.model.entities.Global
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface StockApi {
    companion object {
        private const val BASE_URL = "https://alpha-vantage.p.rapidapi.com/"
        private const val CURRENT_HISTORY_END_POINT =
            "query?function=GLOBAL_QUOTE&symbol=MSFT&datatype=json"
        private const val HISTORY_STOCK_END_POINT =
            "query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&datatype=json"

        private val retroFit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(LocalInterceptor())
                    .build()
            )
            .build()
        val getInstance: StockApi = retroFit.create()
    }

    @GET(CURRENT_HISTORY_END_POINT)
    suspend fun getUpdatedStockHistory(): Global

    @GET(HISTORY_STOCK_END_POINT)
    suspend fun getStockHistory(): CompanyHistory
}
