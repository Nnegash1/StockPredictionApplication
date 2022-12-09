package com.example.stockpredictionapplication.view.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockpredictionapplication.model.Repository
import kotlinx.coroutines.launch

class GraphScreenViewModel : ViewModel() {
    fun getGlobalUpdate() = viewModelScope.launch {
        Repository.getStockHistory()
    }
}