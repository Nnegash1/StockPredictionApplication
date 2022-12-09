package com.example.stockpredictionapplication.view.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockpredictionapplication.model.Repository
import com.example.stockpredictionapplication.view.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val _homeScreenState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Empty)
    val homeScreenState = _homeScreenState.asStateFlow()

    fun getGlobalUpdate() = viewModelScope.launch {
        _homeScreenState.value = UIState.Success(Repository.getStock())
    }
}