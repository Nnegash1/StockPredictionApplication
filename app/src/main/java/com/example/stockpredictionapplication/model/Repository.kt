package com.example.stockpredictionapplication.model

import android.util.Log
import com.example.stockpredictionapplication.model.entities.GlobalQuote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Repository {
    suspend fun getStock(): GlobalQuote {
        val scope = CoroutineScope(Dispatchers.IO)
        val result = scope.async {
            val responseBody = StockApi.getInstance.getUpdatedStockHistory()
            val body = responseBody.globalQuote
            Log.d("Repository", "getStock: ${body}")
            body
        }

        return result.await()
    }

    suspend fun getStockHistory() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val responseBody = StockApi.getInstance.getStockHistory()
            Log.d("Repository", "getStock: $responseBody")
        }
    }
}