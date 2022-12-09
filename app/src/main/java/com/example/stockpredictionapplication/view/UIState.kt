package com.example.stockpredictionapplication.view

import com.example.stockpredictionapplication.model.entities.GlobalQuote

sealed class UIState() {
    class Success(val price: GlobalQuote) : UIState()
    object Empty : UIState()
}
