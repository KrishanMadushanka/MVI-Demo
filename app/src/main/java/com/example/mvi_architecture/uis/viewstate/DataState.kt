package com.example.mvi_architecture.uis.viewstate

import com.example.mvi_architecture.data.model.Data

sealed class DataState {

    object Inactive : DataState()
    object Loading : DataState()
    data class ResponseData(val data: Data) : DataState()
    data class Error(val error: String?) : DataState()

}